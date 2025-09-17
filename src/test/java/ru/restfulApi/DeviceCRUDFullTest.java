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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
public class DeviceCRUDFullTest extends BaseTest {
    private final DeviceRestService deviseRestService = new DeviceRestService();

    static Stream<Arguments> deviceProvider() {
        return Stream.of(
                Arguments.of(DeviceModel.builder()
                                .name("Apple-test")
                                .data(DeviceDataModel.builder()
                                        .color(DeviceColor.BLUE.getValue())
                                        .price(RandomNumbersGenerator.getRandomNumberInRange(1.0, 2000.0))
                                        .capacityGB(CapacityGB.GB16.getValue())
                                        .build())
                                .build(),
                        DeviceModel.builder()
                                .name("Apple-test2")
                                .data(DeviceDataModel.builder()
                                        .price(RandomNumbersGenerator.getRandomNumberInRange(1.0, 2000.0))
                                        .capacityGB(CapacityGB.GB32.getValue())
                                        .build())
                                .build()),
                Arguments.of(DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(RandomNumbersGenerator.getRandomNumberInRange(1990, 2024))
                                        .price(RandomNumbersGenerator.getRandomNumberInRange(1.0, 2000.0))
                                        .cpuModel(CpuModel.APPLE_MAX.getValue())
                                        .hardDiskSize(HardDiskSize.TB2.getValue())
                                        .build())
                                .build(),
                        DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(RandomNumbersGenerator.getRandomNumberInRange(1990, 2024))
                                        .cpuModel(CpuModel.APPLE_PRO.getValue())
                                        .hardDiskSize(HardDiskSize.TB8.getValue())
                                        .build())
                                .build())
        );
    }

    @ParameterizedTest
    @MethodSource("deviceProvider")
    @DisplayName("CRUD operations for device with PUT update")
    public void shouldHaveCorrectFullCRUDDevice(DeviceModel deviceToAdd, DeviceModel deviceToChange) {
        log.info("Create device");
        String deviceId = deviseRestService.addDeviceResponse(deviceToAdd, HttpStatus.SC_OK)
                .extract()
                .path("id");
        log.info("Device with id \"{}\" is created", deviceId);
        DeviceModel deviceBeforeChanging = deviseRestService.getDeviceById(deviceId);
        log.info("Device before changing info: {}", deviceBeforeChanging);

        log.info("Change device's info by id");
        deviseRestService.putDeviceById(deviceToChange, deviceId, HttpStatus.SC_OK);
        DeviceModel deviceAfterChanging = deviseRestService.getDeviceById(deviceId);
        log.info("Device after changing info: {}", deviceAfterChanging);
        assertNotEquals(deviceBeforeChanging, deviceAfterChanging, "Device before changing: " + deviceBeforeChanging + " is equal to device after changing: " + deviceAfterChanging);

        log.info("Delete device by id");
        deviseRestService.deleteDeviceById(deviceId, HttpStatus.SC_OK);

        log.info("Check if device was deleted by id");
        deviseRestService.getDeviceResponse(deviceId, HttpStatus.SC_NOT_FOUND);
    }
}
