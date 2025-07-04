package com.grab.hackathon.publisher;
import com.grab.hackathon.request.TransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
public class SqsPublisher {

    private final SqsClient sqsClient;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    public void sendMessage(TransactionEvent message) {
        String messageBody;

        try {
            messageBody = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize message", e);
        }

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Sent message: " + message);
    }
}
