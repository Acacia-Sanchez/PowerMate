# PowerMate
### Proyecto Didáctico: Sistema de Gestión de Energía para Hogares Inteligentes

### EQUIPO 1: 
* PRODUCT OWNER: Andrés Vázquez [Github](https://github.com/andresvaz89)
* SCRUM MASTER: Roberto Lumbreras [Github](https://github.com/roberto-lumbreras)
* DEVELOPER: Maria García [Github](https://github.com/strawmery)
* DEVELOPER: Abel Prieto [Github](https://github.com/abelpriem)
* DEVELOPER: Naudelyn Lucena [Github](https://github.com/NaudelynLucena)
* DEVELOPER: Pilar Pato [Github](https://github.com/Pilar-Pato)
* DEVELOPER: Lara Gutiérrez [Github](https://github.com/lara-gs)
* DEVELOPER: Ana Hernández [Github](https://github.com/AnaBHernandez)
* DEVELOPER: Susana Artime [Github](https://github.com/Susana-Artime)
* DEVELOPER: Rubén Blanco [Github](https://github.com/Ruben-BV)

### EQUIPO 2: 
* PRODUCT OWNER: Acacia Sánchez Pastur [Github](https://github.com/Acacia-Sanchez)
* SCRUM MASTER: Juan Camilo [Github](https://github.com/Juanito2005)
* DEVELOPER: Adrian Caiñas [Github](https://github.com/acr00)
* DEVELOPER: Andrea Martinez [Github](https://github.com/andreamsgi27)
* DEVELOPER: Óscar Menéndez [Github](https://github.com/Morty1904)
* DEVELOPER: Mercy Chancayauri [Github](https://github.com/mercyluz)
* DEVELOPER: Alejandra Sierra [Github](https://github.com/alejandra-sierra)
* DEVELOPER: Estefany Ochoa [Github](https://github.com/EstefanyOchoaRomero)
* DEVELOPER: Kevin Boy [Github](https://github.com/sealkboy)
* DEVELOPER: Guadalupe G.Figeroa [Github](https://github.com/GuadalupeGFigueroa)

## 1. Objetivos del Proyecto
¿Cómo podríamos diseñar un sistema que ayude a las personas a ahorrar energía y controlar sus dispositivos desde cualquier lugar?

El proyecto consiste en crear un backend que permita a los usuarios:
* Monitorear el consumo energético de dispositivos inteligentes en su hogar.
* Configurar alertas y programar horarios para optimizar el uso de energía.
* Visualizar reportes históricos para tomar mejores decisiones sobre su consumo.

### Entregables y flujo de trabajo.
* Definir los roles del equipo (en cada equipo)
* Se debe usar Jira para gestión del proyecto.
* Se debe hacer normalización, ER y UML.
* Repositorio:
    * Se debe trabajar en un único repositorio.
    * Todas las ramas tiene que seguir un flujo de PR y ser aprobadas por una persona de tu mismo grupo que será así mismo el encargo de: mergear y borrar esa rama (una vez aprobada la PR)
    * NO se trabaja en main.
    * TODAS las ramas salen de dev.
    * El naming debe ser coherente (NO spanglish) y seguir un formato acorde al ticket de jira al que se corresponde la tarea. Ejemplo, si el proyecto toma como Key SGE (Sistema de Gestión de Energía) los commit se verían así: feat(SGE-X):Commit-inicial”
        * feat o fix (según lo que vayamos a hacer, desarrollar una feature o bien hacer un “arreglo” de una existente)
        * (SGE-X) donde X es el número del ticket (jira) al que se corresponde esa tarea.
        * descripción de lo que se hará en esa tarea/rama
    * Al ser dos equipos de una misma empresa y estar limitado jira a 10 usuarios en su versión gratuita. La sugerencia es que uséis el mismo KEY, SGE (Sistema de Gestión de Energía) pero el equipo 1 terminado en 1 y el 2, terminado en 2: SGE1, SGE2.
    * Debe usar MySQLWorkbench como bbdd.
    * Debemos usar Postman para testear los endpoints.
    * Cobertura de testing mínima esperada 60%.
    * Se puede investigar LIBREMENTE cualquier otra herramienta que los equipos acuerden probar y/o investigar (si os facilita el trabajo o causa curiosidad)

## Equipo 1: Usuarios y Dispositivos
* Paso 1: Configuración de Autenticación Básica y Roles
    * Entender la necesidad:
        * Crear un sistema donde los usuarios puedan registrarse, iniciar sesión y tener acceso según su rol (usuario o administrador).
        * Investigar cómo usar Spring Security para implementar autenticación básica.
    * Objetivos:
        * Crear una tabla en la base de datos para almacenar usuarios con campos como:
            * id, username, password (encriptado) y role.
        * Configurar una clase de seguridad que permita:
            * Acceso a ciertos endpoints solo para administradores.
            * Acceso general para usuarios autenticados.
    * Tareas:
        * Diseñar el modelo de datos para users y su repositorio.
        * Investigar cómo encriptar contraseñas y verificar autenticación.
        * Configurar roles en la lógica de autorización (pueden investigar el uso de filtros o anotaciones como @PreAuthorize).

* Paso 2: Configuración de CORS
    * Entender la necesidad:
        * Permitir que el sistema backend acepte solicitudes desde dominios específicos (como el frontend o herramientas como Postman).
    * Objetivos:
        * Investigar cómo configurar CORS en Spring Boot para:
            * Permitir métodos HTTP como GET, POST, PUT y DELETE.
            * Asegurar que solo dominios confiables puedan realizar solicitudes.
    * Tareas:
        * Implementar una configuración de CORS global.
        * Probar con herramientas como Postman para asegurarse de que funciona correctamente.

* Paso 3: CRUD de Dispositivos
    * Entender la necesidad:
        * Permitir que los usuarios gestionen sus dispositivos inteligentes (crear, ver, editar y eliminar).
    * Objetivos:
        * Diseñar un modelo de datos para devices relacionado con los usuarios.
        * Asegurarse de que un usuario solo pueda ver y gestionar sus propios dispositivos.
    * Tareas:
        * Diseñar el esquema de la tabla devices.
        * Crear las relaciones necesarias entre users y devices.
        * Implementar endpoints para:
            * Listar dispositivos del usuario autenticado.
            * Crear un nuevo dispositivo con validación de datos.
            * Actualizar la información de un dispositivo.
            * Eliminar un dispositivo.


## Equipo 2: Consumo y Optimización
* Paso 1: Monitoreo de Consumo en Tiempo Real
    * Entender la necesidad:
        * Mostrar a los usuarios cuánto están consumiendo sus dispositivos en tiempo real y registrar este consumo para generar reportes.
    * Objetivos:
        * Diseñar un modelo de datos para almacenar registros de consumo, incluyendo:
            * device_id, timestamp, y consumption.
        * Implementar lógica para simular el consumo energético a intervalos regulares.
    * Tareas:
        * Diseñar el esquema de la tabla consumption_records.
        * Crear un servicio para generar datos de consumo simulados.
        * Implementar endpoints para:
            * Obtener el consumo actual de un dispositivo.
            * Registrar consumos periódicos en la base de datos.

* Paso 2: Alertas de Consumo
    * Entender la necesidad:
        * Notificar a los usuarios cuando el consumo supere un límite establecido por ellos mismos.
    * Objetivos:
        * Diseñar un modelo de datos para almacenar configuraciones de alertas, incluyendo:
            * user_id, threshold, y device_id (opcional, para alertas por dispositivo).
        * Implementar lógica para comparar el consumo con los umbrales configurados.
    * Tareas:
        * Diseñar el esquema de la tabla alerts.
        * Crear endpoints para:
            * Configurar alertas (crear, editar y eliminar).
            * Comprobar si alguna alerta está activa.
        * Proponer un método para notificar (puede ser una simulación en el log o una respuesta API).

* Paso 3: Programación de Dispositivos
    * Entender la necesidad:
        * Automatizar el encendido y apagado de dispositivos en horarios específicos.
    * Objetivos:
        * Diseñar un modelo de datos para almacenar horarios de encendido/apagado.
        * Implementar lógica para ejecutar las acciones programadas.
    * Tareas:
        * Diseñar el esquema de la tabla schedules.
        * Crear un servicio que compruebe si un dispositivo debe estar encendido o apagado.
        * Implementar endpoints para:
            * Configurar horarios (crear, editar y eliminar).
            * Consultar horarios activos para un dispositivo.


## 3. Arquitectura del Sistema (Visual y Práctica)
* Diagrama General
    * Backend: Lógica de negocio y gestión de usuarios/dispositivos.
    * Base de Datos (MySQL): Tablas para almacenar usuarios, dispositivos y consumo.
    * API REST:
        * /api/users: Usuarios y autenticación.
        * /api/devices: Gestión de dispositivos.
        * /api/consumption: Consumo en tiempo real.
        * /api/alerts: Gestión de alertas.
        * /api/schedule: Programación de dispositivos
<<<<<<< HEAD
 
"# PowerMate" 
=======

## 4. DIAGRAMA UML
![image](https://github.com/user-attachments/assets/0e467b9f-7513-4928-8910-c791f062a3c2)
>>>>>>> 3871e79ad60a26e6863a4582e982de9ce723c9bd
