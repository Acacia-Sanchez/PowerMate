package org.factoriaf5.powermate.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DeviceTest {

    @Test
    void testDeviceId() {
        Device device = new Device();

        device.setId(1L);
        assertEquals(1L, device.getId());
    }

    @Test
    void testDeviceGetUserId() {
        User user = new User(1L, "Prueba", "1234", "admin");
        Device device = new Device();

        device.setUserId(user.getId());

        assertEquals(device.getUserId(), user.getId());
    }

    @Test
    void testDeviceName() {
        Device device = new Device();

        device.setName("Televisión");
        assertEquals("Televisión", device.getName());
    }

    @Test
    void testDevicePower() {
        Device device = new Device();

        device.setPower(45);
        assertEquals(45, device.getPower());
    }

    @Test
    void testDeviceStatus() {
        Device device = new Device();

        device.setStatus(true);
        assertEquals(true, device.isStatus());
    }
}
