package com.example.project.services;

import com.example.project.models.ContactMessage;
import com.example.project.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactMessage saveMessage(ContactMessage message) {
        message.setSubmittedAt(LocalDateTime.now());
        return contactMessageRepository.save(message);
    }
}
