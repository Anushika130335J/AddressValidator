package org.example;


import com.amazonaws.serverless.proxy.internal.LambdaContainerHandler;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamLambdaHandlerTest {

    private static StreamLambdaHandler handler;
    private static Context lambdaContext;
    private static String validAddressJson;
    private static String invalidAddressJson;
    private static String autoCompleteAddressJson;

    @BeforeAll
    public static void setUp() {
        handler = new StreamLambdaHandler();
        lambdaContext = new MockLambdaContext();
        validAddressJson = "{" +
                "\"q\": \"10 Brooks Place, Feilding 4702\"" +
                "}";
        invalidAddressJson = "{" +
                "\"q\": \"aadsfjpivg\"" +
                "}";
        autoCompleteAddressJson = "{" +
                "\"q\": \"Br\"" +
                "}";
    }

    @Test
    public void ping_streamRequest_respondsWithHello() {
        InputStream requestStream = new AwsProxyRequestBuilder("/ping", HttpMethod.GET)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

        assertFalse(response.isBase64Encoded());

        assertFalse(response.getBody().contains("pong"));
        assertTrue(response.getBody().contains("Hello, World!"));

        assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void validateAddress_streamRequest_respondsWithValidation() {
        InputStream inputStream = new AwsProxyRequestBuilder("/verification", HttpMethod.POST)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(validAddressJson)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        handle(inputStream, responseStream);
        AwsProxyResponse response = readResponse(responseStream);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

        assertTrue(response.getBody().contains("\"matched\":true"));
        assertTrue(response.getBody().contains("\"success\":true"));

        assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
                .startsWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void inValidatAddress_streamRequest_respondsWithValidation() {
        InputStream inputStream = new AwsProxyRequestBuilder("/verification", HttpMethod.POST)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(invalidAddressJson)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(inputStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

        assertTrue(response.getBody().contains("\"matched\":false"));
        assertTrue(response.getBody().contains("\"success\":true"));

        assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
                .startsWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void autocomplete_streamRequest_respondsWithValidation() {
        InputStream inputStream = new AwsProxyRequestBuilder("/autocomplete", HttpMethod.POST)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(autoCompleteAddressJson)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(inputStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

        assertTrue(response.getBody().contains("\"a\":\"Braemar Hospital, 24 Ohaupo Road, Hamilton Lake, Hamilton 3204\""));
        assertTrue(response.getBody().contains("\"a\":\"116A Gallie Branch Road, Matakohe 0594\""));
        assertTrue(response.getBody().contains("\"a\":\"157 Branch Drain Road, Leeston 7682\""));
        assertFalse(response.getBody().contains("\"a\":\"10 Brooks Place, Feilding 4702\""));
        assertTrue(response.getBody().contains("\"success\":true"));

        assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
                .startsWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void invalidResource_streamRequest_responds404() {
        InputStream requestStream = new AwsProxyRequestBuilder("/pong", HttpMethod.GET)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    private void handle(InputStream is, ByteArrayOutputStream os) {
        try {
            handler.handleRequest(is, os, lambdaContext);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    private AwsProxyResponse readResponse(ByteArrayOutputStream responseStream) {
        try {
            return LambdaContainerHandler.getObjectMapper().readValue(responseStream.toByteArray(), AwsProxyResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error while parsing response: " + e.getMessage());
        }
        return null;
    }

}
