package com.was.sentiment.configuration;

import com.was.sentiment.model.KeywordRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaJsonTemplateConfiguration {

//    @Bean
//    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> producerFactory,
//                                                                               ConcurrentMessageListenerContainer<String,String> repliesContainer){
//        return new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
//    }
//
//    @Bean
//    public ConcurrentMessageListenerContainer<String,String> repliesContainer(ConcurrentKafkaListenerContainerFactory<String,String> containerFactory) {
//        ConcurrentMessageListenerContainer<String,String>  container = containerFactory.createContainer("clip3-replies");
//        container.getContainerProperties().setGroupId("clip3-replies-container-id");
//
//        return container;
//    }
    @Bean
    public KafkaTemplate<String, KeywordRequest> kafkaJsonTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    private ProducerFactory<String, KeywordRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    private Map<String, Object> producerProps() {
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,10*1000);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,10*1000);
        return props;

    }
}
