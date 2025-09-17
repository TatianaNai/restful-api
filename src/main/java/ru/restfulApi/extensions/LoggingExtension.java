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
            log.info("Request URI: {}\nRequest method: {}\nRequest headers: {}\nRequest body: {}",
                    req.getURI(), req.getMethod(), req.getHeaders(), req.getBody());
            log.info("Response status: {}\nResponse header: {}\nResponse body: {}", response.getStatusCode(),
                    response.getHeaders(), response.getBody().asPrettyString());
            return response;
        });
    }
}
