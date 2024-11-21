package org.factoriaf5.powermate.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.User;
import org.junit.jupiter.api.Test;

public class TestDeviceDTO {

    @Test
    void testDeviceDTO() {
        Long id = 1L;
        Long idUser = 1L;
        String name = "device 1";
        int power = 100;
        boolean status = true;

        User user = User.builder()
                .id(idUser)
                .username("user1")
                .password("password1")
                .role("admin")
                .build();

        Device device = new Device(id, user, name, status, power);

        DeviceDTO devicedto = new DeviceDTO(device);
        assertEquals(id, devicedto.getId());
        assertEquals(idUser, devicedto.getUserId());
        assertEquals(name, devicedto.getName());
        assertEquals(power, devicedto.getPower());
        assertEquals(status, devicedto.isStatus());
    }

    @Test
    void testDeviceId() {
        DeviceDTO devicedto = new DeviceDTO();

        devicedto.setId(1L);
        assertEquals(1L, devicedto.getId());
    }

    @Test
    void testDeviceGetSetUser() {
        DeviceDTO devicedto = new DeviceDTO();

        devicedto.setUserId(1L);
        assertEquals(1L, devicedto.getUserId());
    }

    @Test
    void testDeviceGetSetName() {
        DeviceDTO devicedto = new DeviceDTO();

        devicedto.setName("device 1");
        assertEquals("device 1", devicedto.getName());
    }

    @Test
    void testDeviceGetSetPower() {
        DeviceDTO devicedto = new DeviceDTO();

        devicedto.setPower(100);
        assertEquals(100, devicedto.getPower());
    }

    @Test
    void testDeviceGetSetStatus() {
        DeviceDTO devicedto = new DeviceDTO();

        devicedto.setStatus(true);
        assertEquals(true, devicedto.isStatus());
    }

}
