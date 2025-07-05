package com.grab.hackathon.publisher;
import com.grab.hackathon.request.TransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

@Service
@RequiredArgsConstructor
public class SqsPublisher {

    private final SqsClient sqsClient;

    @Value("${sqs.queue.url}")
    private String defaultQueueUrl;

    public void publishMessage(String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(defaultQueueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Sent message: " + message);
    }

    public void publishMessageToQueue(String queueName, String message) {
        // Get queue URL from queue name
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();
        GetQueueUrlResponse getQueueResponse = sqsClient.getQueueUrl(getQueueRequest);
        String queueUrl = getQueueResponse.queueUrl();

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Sent message to queue " + queueName + ": " + message);
    }

    public void sendMessage(TransactionEvent message) {
        String messageBody;

        try {
            messageBody = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize message", e);
        }

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(defaultQueueUrl)
                .messageBody(messageBody)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Sent message: " + message);
    }
}
