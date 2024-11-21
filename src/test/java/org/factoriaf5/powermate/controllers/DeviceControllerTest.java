package org.factoriaf5.powermate.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.factoriaf5.powermate.dtos.DeviceDTO;
import org.factoriaf5.powermate.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class DeviceControllerTest {
    private MockMvc mockMvc;
    @Mock
    private DeviceService deviceService;
    @InjectMocks
    private DeviceController deviceController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
    }
    @Test
    public void testAddDevice() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Device 1");
        deviceDTO.setPower(100);
        deviceDTO.setStatus(true);
        DeviceDTO savedDeviceDTO = new DeviceDTO();
        savedDeviceDTO.setId(1L);
        savedDeviceDTO.setUserId(1L);
        savedDeviceDTO.setName("Device 1");
        savedDeviceDTO.setPower(100);
        savedDeviceDTO.setStatus(true);
        when(deviceService.createDevice(any(DeviceDTO.class))).thenReturn(savedDeviceDTO);
        mockMvc.perform(post("/api/admin/devices")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(deviceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Device 1"));
    }
    @Test
    public void testGetDevice() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Device 1");
        deviceDTO.setPower(100);
        deviceDTO.setStatus(true);
        when(deviceService.getAll()).thenReturn(List.of(deviceDTO));
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Device 1"));
    }
    @Test
    public void testUpdateDevice() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Updated Device");
        deviceDTO.setPower(150);
        deviceDTO.setStatus(false);
        DeviceDTO updatedDeviceDTO = new DeviceDTO();
        updatedDeviceDTO.setId(1L);
        updatedDeviceDTO.setUserId(1L);
        updatedDeviceDTO.setName("Updated Device");
        updatedDeviceDTO.setPower(150);
        updatedDeviceDTO.setStatus(false);
        when(deviceService.updateDevice(any(DeviceDTO.class), anyLong())).thenReturn(updatedDeviceDTO);
        mockMvc.perform(put("/api/admin/devices/{deviceId}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(deviceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Device"))
                .andExpect(jsonPath("$.power").value(150));
    }
    @Test
    public void testUpdateStatus() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setUserId(1L);
        deviceDTO.setName("Device 1");
        deviceDTO.setPower(100);
        deviceDTO.setStatus(true);
        DeviceDTO updatedDeviceDTO = new DeviceDTO();
        updatedDeviceDTO.setId(1L);
        updatedDeviceDTO.setUserId(1L);
        updatedDeviceDTO.setName("Device 1");
        updatedDeviceDTO.setPower(100);
        updatedDeviceDTO.setStatus(false);
        when(deviceService.updateStatus(anyLong(), anyBoolean())).thenReturn(updatedDeviceDTO);
        mockMvc.perform(patch("/api/devices/{deviceId}", 1L)
                        .param("status", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }
    @Test
    public void testDeleteDevice() throws Exception {
        doNothing().when(deviceService).deleteDevice(anyLong());
        mockMvc.perform(delete("/api/admin/devices/{deviceId}", 1L))
                .andExpect(status().isOk());
        verify(deviceService, times(1)).deleteDevice(anyLong());
    }
}
