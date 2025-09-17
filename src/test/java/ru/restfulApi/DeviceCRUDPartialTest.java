package ru.restfulApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.restfulApi.enums.CapacityGB;
import ru.restfulApi.enums.CpuModel;
import ru.restfulApi.enums.DeviceColor;
import ru.restfulApi.enums.HardDiskSize;
import ru.restfulApi.models.DeviceDataModel;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.services.DeviceRestService;
import ru.restfulApi.utils.RandomNumbersGenerator;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class DeviceCRUDPartialTest extends BaseTest {
    private final DeviceRestService deviseRestService = new DeviceRestService();

    static Stream<Arguments> deviceProvider() {
        return Stream.of(
                Arguments.of(DeviceModel.builder()
                        .name("Apple-test")
                        .data(DeviceDataModel.builder()
                                .color(DeviceColor.GREEN.getValue())
                                .price(RandomNumbersGenerator.getRandomNumberInRange(1.0, 2000.0))
                                .capacityGB(CapacityGB.GB128.getValue())
                                .build())
                        .build(),
                        Map.of("name", "Change name")),
                Arguments.of(DeviceModel.builder()
                        .name("Apple-second-test")
                        .build(),
                        Map.of("name", "Apple phone")),
                Arguments.of(DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(RandomNumbersGenerator.getRandomNumberInRange(1990, 2024))
                                        .price(RandomNumbersGenerator.getRandomNumberInRange(1.0, 2000.0))
                                        .cpuModel(CpuModel.INTEL.getValue())
                                        .hardDiskSize(HardDiskSize.TB4.getValue())
                                        .build())
                                .build(),
                        Map.of("name", "Laptop"))
        );
    }

    @ParameterizedTest
    @MethodSource("deviceProvider")
    @DisplayName("CRUD operations for device with PATCH update")
    public void shouldHaveCorrectPartialCRUDDevice(DeviceModel device, Map<String, String> parameter) {
        log.info("Create device");
        String deviceId = deviseRestService.addDeviceResponse(device, HttpStatus.SC_OK)
                .extract()
                .path("id");
        log.info("Device with id \"{}\" is created", deviceId);
        String nameBeforeChanging = deviseRestService.getDeviceById(deviceId).getName();
        log.info("Device's name before changing: \"{}\"", nameBeforeChanging);

        log.info("Change device's parameter by id");
        deviseRestService.patchDeviceById(parameter, deviceId, HttpStatus.SC_OK);
        String nameAfterChanging = deviseRestService.getDeviceById(deviceId).getName();
        log.info("Device's name after changing: \"{}\"", nameAfterChanging);
        assertAll(
                () -> assertNotEquals(nameBeforeChanging, nameAfterChanging, "Device's name before changing: " + nameBeforeChanging + " is equal to device's name after changing: " + nameAfterChanging),
                () -> assertEquals(parameter.values().iterator().next(), nameAfterChanging, "Value of parameter " + parameter.values().iterator().next() + " is not equal to " + nameAfterChanging)
        );

        log.info("Delete device by id");
        deviseRestService.deleteDeviceById(deviceId, HttpStatus.SC_OK);

        log.info("Check if device was deleted by id");
        deviseRestService.getDeviceResponse(deviceId, HttpStatus.SC_NOT_FOUND);
    }
}
