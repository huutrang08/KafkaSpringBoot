package com.example.service;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class WikiHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiHandler.class);


    private KafkaTemplate<String,String> kafkaTemplate;

    public WikiHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("Event data -> %s",messageEvent.getData()));
        kafkaTemplate.send("wikimedia", messageEvent.getData()).addCallback(new KafkaSendCallback<String,String>(){

            @Override
            public void onSuccess(SendResult<String, String> result) {
                  // do something if success
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                // do something if fail
            }
        });
     }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
