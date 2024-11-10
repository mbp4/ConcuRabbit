package com.example.demo;


import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMQListener {

    @RabbitListener(queues = "colaBolas")
    public void oyenteColaBolas(String mensaje){
        System.out.println("Se ha recibido una bola: " + mensaje);
    }

    @RabbitListener(queues = "colaNiveles")
    public void oyenteColaNiveles(String mensaje){
        System.out.println("Se ha recibido un nivel: " + mensaje);
    }


}
