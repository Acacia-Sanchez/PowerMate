package org.factoriaf5.powermate.services;

import org.factoriaf5.powermate.dtos.DeviceDTO;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class DeviceServiceTest {
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeviceService deviceService;
    public DeviceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAll() {
        User user1 = new User(1L, "User1", "password1", "role1");
        User user2 = new User(2L, "User2", "password2", "role2");
        Device device1 = new Device(1L, user1, "Device1", true, 100);
        Device device2 = new Device(2L, user2, "Device2", false, 200);
        List<Device> devices = List.of(device1, device2);
        when(deviceRepository.findAll()).thenReturn(devices);
        List<DeviceDTO> result = deviceService.getAll();
        assertEquals(2, result.size());
        assertEquals("Device1", result.get(0).getName());
        assertEquals(1L, result.get(0).getUserId());
        assertEquals("Device2", result.get(1).getName());
        assertEquals(2L, result.get(1).getUserId());
        verify(deviceRepository, times(1)).findAll();
    }
    @Test
    void testGetDevicesById() {
        User user = new User(1L, "User1", "password", "role");
        Device device = new Device(1L, user, "Device1", true, 100);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        Optional<DeviceDTO> result = deviceService.getDevicesById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Device1", result.get().getName());
        assertEquals(1L, result.get().getUserId());
        verify(deviceRepository, times(1)).findById(1L);
    }
    @Test
    void testCreateDevice() {
        User user = new User(1L, "User1", "password", "role");
        Device device = new Device(1L, user, "Device1", true, 100);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Device1");
        deviceDTO.setPower(100);
        deviceDTO.setStatus(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        DeviceDTO result = deviceService.createDevice(deviceDTO);
        assertEquals("Device1", result.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }
    @Test
    void testCreateDevice_UserNotFound() {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Device1");
        deviceDTO.setPower(100);
        deviceDTO.setStatus(true);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceService.createDevice(deviceDTO);
        });
        assertEquals("User not found with deviceId: 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(deviceRepository, times(0)).save(any(Device.class));
    }
    @Test
    void testUpdateDevice() {
        User user = new User(1L, "User1", "password", "role");
        Device existingDevice = new Device(1L, user, "OldDevice", false, 50);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("UpdatedDevice");
        deviceDTO.setPower(200);
        deviceDTO.setStatus(true);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deviceRepository.save(any(Device.class))).thenReturn(existingDevice);
        DeviceDTO result = deviceService.updateDevice(deviceDTO, 1L);
        assertEquals("UpdatedDevice", result.getName());
        assertTrue(result.isStatus());
        assertEquals(200, result.getPower());
        verify(deviceRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }
    @Test
    void testUpdateDevice_DeviceNotFound() {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setUserId(1L);
        deviceDTO.setName("UpdatedDevice");
        deviceDTO.setPower(200);
        deviceDTO.setStatus(true);
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceService.updateDevice(deviceDTO, 1L);
        });
        assertEquals("User not found with deviceId: 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(deviceRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).findById(1L);
        verify(deviceRepository, times(0)).save(any(Device.class));
    }
    @Test
    void testUpdateStatus() {
        User user = new User(1L, "User1", "password", "role");
        Device existingDevice = new Device(1L, user, "Device1", false, 100);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(any(Device.class))).thenReturn(existingDevice);
        DeviceDTO result = deviceService.updateStatus(1L, true);
        assertTrue(result.isStatus());
        assertEquals(1L, result.getUserId());
        verify(deviceRepository, times(1)).findById(1L);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }
    @Test
    void testUpdateStatus_DeviceNotFound() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceService.updateStatus(1L, true);
        });
        assertEquals("Device not found with deviceId: 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(deviceRepository, times(1)).findById(1L);
        verify(deviceRepository, times(0)).save(any(Device.class));
    }
    @Test
    void testDeleteDevice() {
        Device existingDevice = new Device(1L, null, "Device1", true, 100);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
        doNothing().when(deviceRepository).deleteById(1L);
        deviceService.deleteDevice(1L);
        verify(deviceRepository, times(1)).findById(1L);
        verify(deviceRepository, times(1)).deleteById(1L);
    }
    @Test
    void testDeleteDevice_DeviceNotFound() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceService.deleteDevice(1L);
        });
        assertEquals("Device not found with deviceId: 1. Status: 404 NOT_FOUND", exception.getMessage());
        verify(deviceRepository, times(1)).findById(1L);
        verify(deviceRepository, times(0)).deleteById(1L);
    }
    @Test
    void testGetAll_EmptyList() {
        when(deviceRepository.findAll()).thenReturn(List.of());  // Simulamos que no hay dispositivos
        List<DeviceDTO> result = deviceService.getAll();
        assertTrue(result.isEmpty(), "El listado de dispositivos debe estar vacío");
        verify(deviceRepository, times(1)).findAll();
    }
}
