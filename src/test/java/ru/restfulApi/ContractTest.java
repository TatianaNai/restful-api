package ru.restfulApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.restfulApi.services.DeviceRestService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Slf4j
public class ContractTest extends BaseTest {
    private final DeviceRestService deviseRestService = new DeviceRestService();

    @Test
    @DisplayName("Verify JSON schema for devices list")
    public void shouldBeCorrectGetDevicesResponseScheme() {
        log.info("Check JSON contract");
        deviseRestService.getAllDevicesResponse(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("deviceResponseSchema.json"));
    }
}
