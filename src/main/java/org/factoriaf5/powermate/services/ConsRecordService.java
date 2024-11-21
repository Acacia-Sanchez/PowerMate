package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;


import org.factoriaf5.powermate.dtos.ConsRecordDTO;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsRecordService {

    private double consumption = 0.0; // consumo periodico calculado (power * startTime)
    private long timeOn = 0; // guarda los nanosegundos que está el dispositivo encendido
    private long durationInNano = 0; // guarda el tiempo encendido en nanosegundos
    private double durationInHours = 0.0; // guarda la conversión de nanosegundos en horas
    private double totalConsumption = 0.0; // guarda el consumo total del dispositivo desde el comienzo y nunca lo borra

    private final ConsRecordRepository consRecordRepository;
    private final DeviceRepository deviceRepository;

    public ConsRecordService(ConsRecordRepository consRecordRepository, DeviceRepository deviceRepository) {
        this.consRecordRepository = consRecordRepository;
        this.deviceRepository = deviceRepository;
    }


    // metodo para que el test funcione correctamente
    public Device findDeviceById(Long deviceId) {
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);
        return deviceOpt.orElse(null);
    }

    // calculo el consumo total cada 24h, por id

    public ConsRecordDTO recordConsumption(Long deviceId) { // Usar deviceId en lugar de deviceID
        Device device = findDeviceById(deviceId);
        if (device == null) {
            throw new IllegalArgumentException("Dispositivo no encontrado con ID: " + deviceId);
        }


        durationInNano = 0; // reseteo para el nuevo registro periódico

        while (LocalTime.now().isBefore(LocalTime.of(23, 59, 59))) {
            try {
                if (device.isStatus()) { // si está encendido
                    if (timeOn == 0) {
                        timeOn = System.nanoTime(); // Registra el tiempo de inicio en nanosegundos
                    }
                } else { // Si está apagado
                    if (timeOn != 0) { // Y tiene guardados datos
                        durationInNano += System.nanoTime() - timeOn; // Guarda el tiempo actual - el tiempo de comienzo
                                                                      // (timeOn)
                        timeOn = 0;
                    }
                }
                // Simula un retardo para evitar una ejecución infinita muy rápida

                // (pausa de 1 segundo entre cada iteración del bucle)

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Calcula la duración total si el WHILE termina mientras el atributo está en
        // true
        if (device.isStatus() && timeOn != 0) {
            durationInNano += System.nanoTime() - timeOn;
        }

        durationInHours = durationInNano / 3_600_000_000_000.0; // convierte nanosegundos en horas
        consumption = device.getPower() * durationInHours; // guarda el consumo en kW/h
        totalConsumption += consumption; // acumular el consumo total

        // return consumption; // atributo que hay que registrar (POST) en la BBDD

        // Crea una instancia de ConsRecordDTO con los datos calculados
        ConsRecordDTO consRecordDTO = new ConsRecordDTO();
        consRecordDTO.setId(device.getId());
        consRecordDTO.setConsumption(consumption);
        consRecordDTO.setTimestamp(LocalDateTime.now());
        consRecordDTO.setDeviceId(device.getId());

        return consRecordDTO; // Retorna el DTO con los datos

    }

}
