package main.java.org.factoriaf5.powermate.services;

import java.time.LocalTime;

import org.factoriaf5.PowerMate.powermate.repositories.ConsRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsRecordService {

    private double consumption = 0.0;  // consumo periodico calculado (power * startTime)
    private long timeOn = 0;  // guarda los nanosegundos que está el dispositivo encendido
    private long durationInNano = 0;  // guarda el tiempo encendido en nanosegundos
    private double durationInHours = 0.0;  // guarda la conversión de nanosegundos en horas
    private double totalConsumption = 0.0;  // guarda el consumo total del dispositivo desde el comienzo y nunca lo borra

    private final ConsRecordRepository consRecordRepository;

    public ConsRecordService(ConsRecordRepository consRecordRepository) {
        this.consRecordRepository = consRecordRepository;
    }

/*     public final Device device; // creo atributo device, de la clase Device

    public Device(Device device) {   // instancio device para traerme sus datos
        this.device = device;
    }
    // según la IA no es necesario instanciar device, las 5 líneas anteriores sobran.... 
*/

    // calculo el consumo total cada 24h, por id
    public double recordConsumption(Device device) {
        durationInNano = 0;  // reseteo para el nuevo registro periódico

        while (LocalTime.now().isBefore(LocalTime.of(23, 59, 59))) {
            try {
                if (device.isStatus()) {  // si está encendido
                    if (timeOn == 0) {
                        timeOn = System.nanoTime();  // Registra el tiempo de inicio en nanosegundos
                    }
                } else {  // Si está apagado
                    if (timeOn != 0) {  //  Y tiene guardados datos
                        durationInNano += System.nanoTime() - timeOn;  // Guarda el tiempo actual - el tiempo de comienzo (timeOn)
                        timeOn = 0;
                    }
                }
                // Simula un retardo para evitar una ejecución infinita muy rápida
                //(pausa de 1 segundo entre cada iteracción del bucle)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }     

        // Calcula la duración total si el WHILE termina mientras el atributo está en true
        if (device.isStatus() && timeOn != 0) {
            durationInNano += System.nanoTime() - timeOn;
        }

        durationInHours = durationInNano / 3_600_000_000_000.0;  //  convierte nanosegundos en horas
        consumption = device.getPower() * durationInHours; // guarda el consumo en kW/h
        totalConsumption += consumption;  // acumular el consumo total
        return consumption; // atributo que hay que registrar (POST) en la BBDD
    }
}