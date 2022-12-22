package com.was.sentiment.producer;

import com.was.sentiment.model.KeywordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChartProducer {
    private final KafkaTemplate<String, KeywordRequest> kafkaJsonTemplate;
    public void sync(String topic, KeywordRequest keywordRequest) {
        try {
            SendResult<String, KeywordRequest> result = kafkaJsonTemplate.send(topic, keywordRequest).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }

    }
}
