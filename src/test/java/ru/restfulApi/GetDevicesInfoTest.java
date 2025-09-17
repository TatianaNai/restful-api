package ru.restfulApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.restfulApi.services.DeviceRestService;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GetDevicesInfoTest extends BaseTest{
    private final DeviceRestService deviseRestService = new DeviceRestService();

    @Test
    @DisplayName("Get list of devices")
    public void shouldHaveCorrectGetListDevices() {
        deviseRestService.getAllDevicesResponse(HttpStatus.SC_OK)
                .body("data[2].'capacity GB'", greaterThan(500));
    }

    @ValueSource(strings = {"5", "4"})
    @ParameterizedTest
    @DisplayName("Get device by id")
    public void shouldHaveCorrectGetDeviceById(String id) {
        log.info("Device's id: {}", id);
        deviseRestService.getDeviceResponse(id, HttpStatus.SC_OK)
                .body("$", hasKey("name"));
    }

    @ParameterizedTest
    @CsvSource({
            "7, Apple MacBook Pro 16",
            "2, 'Apple iPhone 12 Mini, 256GB, Blue'"
    })
    @DisplayName("Get name of device by id")
    public void shouldHaveCorrectGetDeviceNameById(String id, String expectedName) {
        String deviceName = deviseRestService.getDeviceById(id).getName();
        log.info("Device's name: {}", deviceName);
        assertEquals(expectedName, deviceName, "Device's name " + deviceName + " is not equal to " + expectedName);
    }
}
