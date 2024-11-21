package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.factoriaf5.powermate.models.ConsRecord;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsRecordService {

    private double consumption = 0.0; // consumo periodico calculado (power * startTime)
    private long timeOn = 0; // guarda los nanosegundos que está el dispositivo encendido
    private long durationInNano = 0; // guarda el tiempo encendido en nanosegundos
    private double durationInHours = 0.0; // guarda la conversión de nanosegundos en horas

    private final ConsRecordRepository consRecordRepository;
    private final DeviceRepository deviceRepository;

    private final Map<Long, Double> totalConsumptions = new HashMap<>();

    public ConsRecordService(ConsRecordRepository consRecordRepository, DeviceRepository deviceRepository) {
    public ConsRecordService(ConsRecordRepository consRecordRepository, DeviceRepository deviceRepository) {
        this.consRecordRepository = consRecordRepository;
        this.deviceRepository = deviceRepository;
    }

    // Buscar dispositivo por su ID
    public Device findDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId).orElse(null);
    }

    // calculo y registra el consumo total cada 24h, por id
    public ConsRecord recordConsumption(Long deviceId) {
        Device device = findDeviceById(deviceId);
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
        // acumular el consumo total
        totalConsumptions.put(device.getId(), totalConsumptions.getOrDefault(device.getId(), 0.0) + consumption);

        ConsRecord consRecord = new ConsRecord(device, consumption, LocalDateTime.now());
        consRecordRepository.save(consRecord);
        return consRecord; // atributo que hay que registrar (POST) en la BBDD
    }

    public double getTotalConsumption(Long deviceId) {
        return totalConsumptions.getOrDefault(deviceId,0.0);
    }
}