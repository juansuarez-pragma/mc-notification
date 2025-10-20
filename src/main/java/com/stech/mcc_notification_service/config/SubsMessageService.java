package com.stech.mcc_notification_service.config;

import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stech.mcc_notification_service.event.CreditDisbursementEvent;
import com.stech.mcc_notification_service.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class SubsMessageService {

    private ObjectMapper objectMapper;
    private NotificationService notificationService;

    public void processMessage(ServiceBusReceivedMessageContext messageContext) {
        ServiceBusReceivedMessage message = messageContext.getMessage();
        try {
            String payload = message.getBody().toString();
            CreditDisbursementEvent event = objectMapper.readValue(payload, CreditDisbursementEvent.class);
            log.info("Precessing event: {}", event);
            this.notificationService.sendNotification(event);
        } catch (Exception e) {
            log.error("Error processing event");
        }
    }

    public void processError(ServiceBusErrorContext context) {
        log.error("Error procesing event");
    }

}
