package org.factoriaf5.powermate.models;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlertsTest {

    @Test
    void testConstructorAndGetters() {
        //testeamos el constructor, dándole qué valor tendría cada una de las variables siguiendo el orden de id, userid, deviceid y threshold.
        Alerts alert = new Alerts(1L, 2L, 3L, 75.5);
        //Verificamos que los valores fueron asignados correctamente.
        assertThat(alert.getId(), is(1L));
        assertThat(alert.getUserid(), is(2L));
        assertThat(alert.getDeviceid(), is(3L));
        assertThat(alert.getThreshold(), is(75.5));
    }

    @Test
    void testSetters() {
    //Creamos una instancia vacía
        Alerts alert =new Alerts();

        //usamos los setters para asignar valores
        alert.setId(10L);
        alert.setUserid(20L);
        alert.setDeviceid(30L);
        alert.setThreshold(99.9);
        
        //Verificamos si los setters funcionan correctamente
        assertThat(alert.getId(), is(10L));
        assertThat(alert.getUserid(), is (20L));
        assertThat(alert.getDeviceid(), is (30L));
        assertThat(alert.getThreshold(), is (99.9));
    }

    @Test
    void testDefauldConstructor() {
    //Creamos una instancia usando el constructor vacío
    Alerts alert = new Alerts();
    
    //Verificamos que los valores iniciales sean nulos o por defecto
    assertThat(alert.getId(), is (nullValue()));
    assertThat(alert.getUserid(), is (nullValue()));
    assertThat(alert.getDeviceid(), is (0L));
    assertThat(alert.getThreshold(), is (0.0));
    }

}    


    