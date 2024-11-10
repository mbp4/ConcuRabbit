package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080/galton");
            } else if (os.contains("mac")) {
                // Mac
                Runtime.getRuntime().exec("open http://localhost:8080/galton");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                Runtime.getRuntime().exec("xdg-open http://localhost:8080/galton");
            } else {
                System.err.println("Sistema operativo no soportado para abrir el navegador.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public CommandLineRunner runner(RabbitTemplate rabbitTemplate){
        return args -> {
            new Thread(() -> fabricarBolas(rabbitTemplate, "colaBolas", 100)).start();
            new Thread(() -> fabricarNiveles(rabbitTemplate, "colaNiveles", 80)).start();
        };
    }


    private void fabricarNiveles(RabbitTemplate rabbitTemplate, String colaNiveles, int numNiveles) {
        int i = 1;
        String mensaje;

        while (i <= numNiveles){
            mensaje = "NIVEL " + i;
            rabbitTemplate.convertAndSend(colaNiveles, mensaje);
            System.out.println("Se ha fabricado y enviado el nivel: " + mensaje);
            i++;
            try {
                Thread.sleep((long) (Math.random() * 100) + 500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void fabricarBolas(RabbitTemplate rabbitTemplate, String colaBolas, int numBolas) {
        int i = 1;
        String mensaje;

        while (i <= numBolas){
            mensaje = "BOLA " + i;
            rabbitTemplate.convertAndSend(colaBolas, mensaje);
            System.out.println("Se ha fabricado y enviado la bola: " + mensaje);
            i++;
            try {
                Thread.sleep((long) (Math.random() * 50) + 300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
