# ExamenConcurrente

link al repositorio: https://github.com/mbp4/ExamenConcurrente.git

Participantes: Adrián Thierry Puyo Olias, Miriam Blanco Ponce y Daniel Sousa Escudero

## Descripción del Proyecto

Este proyecto es una aplicación Java que simula la medición de datos de sensores utilizando concurrencia. La aplicación utiliza una interfaz gráfica para mostrar los datos medidos por los sensores en una tabla.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes y clases:

### Paquete `com.example.demo`

#### Clase `Demo2Application`

Esta clase contiene el método `main` que inicia la aplicación. Crea dos hilos (Therads) de `RabbitTemplate` con sus propias fábricas de bolas y niveles, y la hace visible.

### Paquete `com.example.demo.rabbit`

#### Clase `RabbitMQConfig`

Esta clase crea instancias de las colas para la posterior creación de las mismas.

#### Clase `RabbitMQListener`

Esta clase es un listener que escucha los mensajes de la cola y los imprime en consola para tener un seguimiento de los hilos.

### Paquete `com.example.demo.webFlux`

#### Clase `WebFluxConfig`

Esta clase configura el servidor webFlux para que pueda recibir mensajes de los Threads.

### Paquete `com.example.demo.controller`

#### Clase `ColasController`

Esta clase tiene implementada tóda la lógica para el tratamiento de los mensajes que se reciben de los Threads gracias a las funciones de `procesarMensaje()` y `recibirMensajes()`. También simula la distribución con la función `simularDistribucion()`.

### Paquete `com.example.demo.elements`

#### Clase `Gauss`

Esta clase simula la distribución gaussiana.

### Directorio `resources`

#### Archivo `application.properties`

Este archivo contiene la configuración de la aplicación, incluyendo la configuración de RabbitMQ.

## Instrucciones de Uso

Para ejecutar la aplicación, es necesario tener instalado RabbitMQ en el equipo. Se puede descargar desde la página oficial de RabbitMQ: https://www.rabbitmq.com/docs/download
También hace falta tener instalado Erlang, que se puede descargar desde la página oficial de Erlang: https://www.erlang.org/
Por último es necesario abrir el servicio de RabbitMQ en el equipo desde la powershell con el comando `rabbitmq-server start` o desde el bash en IOS o Linux con el comando `sudo rabbitmq-server start`.
Ya puedes ejecutar la aplicación, la cual esta programada de tal manera que hasta que no se reciban todos los mensajes no se mostrará la distribución.

