package com.akshay.notification.repository;

import com.akshay.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepo extends MongoRepository<Notification, String> {
}
