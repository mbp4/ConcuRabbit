package com.example.demo.controller;

import com.example.demo.elements.Gauss;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.demo.elements.Gauss;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ColasController {

    private final RabbitTemplate rabbitTemplate;

    public ColasController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/galton")
    public String mostrarSimulacion(Model model) {
        model.addAttribute("distribucion", new int[10]);
        return "galton";
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


    @GetMapping(value = "/galton/distribucion", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> simularDistribucion() {
        int niveles = 10;
        Gauss gauss = new Gauss(0, niveles, niveles / 4.0);
        int[] distribucion = new int[niveles + 1];

        return Flux.interval(Duration.ofMillis(300))
                .take(100)  // Simular 100 bolas
                .map(i -> {
                    int nivel = gauss.gaussianRandom();
                    distribucion[nivel]++;
                    return generarMensajeDistribucion(distribucion);
                });
    }

    private String generarMensajeDistribucion(int[] distribucion) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < distribucion.length; i++) {
            builder.append(distribucion[i]);
            if (i < distribucion.length - 1) builder.append(",");
        }
        builder.append("]");
        return builder.toString();
    }

}
