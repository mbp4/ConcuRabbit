package com.example.demo.component;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQPurgeListener {  //implements ApplicationListener<ApplicationReadyEvent> {
/*
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private Queue myQueue; // Asegúrate de que esta sea la cola que quieres purgar

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Purga la cola al iniciar la aplicación
        rabbitAdmin.purgeQueue(myQueue.getName(), true);
    }

    @EventListener
    public void onApplicationClose(ContextClosedEvent event) {
        // Purga la cola al cerrar la aplicación
        rabbitAdmin.purgeQueue(myQueue.getName(), true);
    }
 */
}


