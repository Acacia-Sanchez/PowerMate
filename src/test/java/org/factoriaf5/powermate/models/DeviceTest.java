package org.factoriaf5.powermate.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DeviceTest {

    @Test
    void testDeviceConstructor() {
        User user = User.builder()
                        .id(1L)
                        .username("user1")
                        .password("password1")
                        .role("admin")
                        .build();
        Long id = 1L;
        String name = "device1";
        boolean status = true;
        int power = 100;
        Device device = new Device(id, user, name, status, power);

        assertEquals(id, device.getId());
        assertEquals(user, device.getUser());
        assertEquals(name, device.getName());
        assertEquals(status, device.isStatus());
        assertEquals(power, device.getPower());
        
    }

    @Test
    void testDeviceId() {
        Device device = new Device();

        device.setId(1L);
        assertEquals(1L, device.getId());
    }

    @Test
    void testDeviceGetSetUser() {
        User user = User.builder()
                        .id(1L)
                        .username("user1")
                        .password("password1")
                        .role("admin")
                        .build();
        Device device = new Device();
        device.setUser(user);
        assertEquals(user, device.getUser());
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
