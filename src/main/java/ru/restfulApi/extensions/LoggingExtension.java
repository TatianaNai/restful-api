package ru.restfulApi.extensions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class LoggingExtension implements BeforeTestExecutionCallback {
    @Override
    public void beforeTestExecution(ExtensionContext context) {
        RestAssured.filters((req, res, ctx) -> {
            Response response = ctx.next(req, res);
            log.info("Request URI: {}", req.getURI());
            log.info("Request method: {}", req.getMethod());
            log.info("Request header: {}", req.getHeaders());
            log.info("Request body: {}", (Object) req.getBody());
            log.info("Response status: {}", response.getStatusCode());
            log.info("Response header: {}", response.getHeaders());
            log.info("Response body: {}", response.getBody().asPrettyString());
            return response;
        });
    }
}
