package com.example.service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Data
@AllArgsConstructor
public class WikiProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiProducer.class);
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia";
        EventHandler eventHandler = new WikiHandler(kafkaTemplate);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(10);
    }
}
