package com.example.demo.controller;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ColasController {

    private final RabbitTemplate rabbitTemplate;

    private final List<String> mensajes = new CopyOnWriteArrayList<>();

    public ColasController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(value = "/galton", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> recibirMensajes(){
        return Flux.merge(
                recibirMensaje("colaBolas"), recibirMensaje("colaNiveles")
        )
                .doOnNext(this::procesarMensaje);
    }

    private void procesarMensaje(String s) {
        System.out.println("Mensaje procesado " + s);
    }

    private Flux<String> recibirMensaje(String cola){
        return Flux.generate(contenido -> {
            Object mensaje = rabbitTemplate.receiveAndConvert(cola);

            if (mensaje instanceof String){
                System.out.println("He recibido de la cola " + cola + ": " + mensaje);
                contenido.next((String) mensaje);
            }else if (mensaje == null){
                contenido.complete();
            }else {
                System.out.println("ERROR, dato no valido");
            }
        })
                .cast(String.class)
                .delayElements(Duration.ofMillis((long) (Math.random() * 200) + 50));
    }

}
