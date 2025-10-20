package com.stech.mcc_notification_service.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ServiceBusClientConfiguration {

    @Value("${azure.servicebus.connection-string}")
    private String CONNECTION_STRING;
    @Value("${azure.servicebus.queue-name}")
    private String QUEUE_NAME;

    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder() {
        return new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING);
    }

    @Bean
    ServiceBusProcessorClient serviceBusProcessorClient(
            ServiceBusClientBuilder serviceBusClientBuilder,
            SubsMessageService subsMessageService
    ) {
        ServiceBusProcessorClient serviceBusProcessorClient =
                serviceBusClientBuilder.processor()
                        .queueName(QUEUE_NAME)
                        .processMessage(subsMessageService::processMessage)
                        .processError(subsMessageService::processError)
                        .buildProcessorClient();
        serviceBusProcessorClient.start();
        return serviceBusProcessorClient;
    }
}
