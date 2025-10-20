package com.stech.mcc_notification_service.service;

import com.stech.mcc_notification_service.event.CreditDisbursementEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotificationService {

    public void sendNotification(CreditDisbursementEvent creditDisbursementEvent) {
        log.info("Send Notification Credit disbursement Event");
    }

}
