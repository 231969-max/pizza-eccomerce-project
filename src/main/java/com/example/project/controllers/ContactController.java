package com.example.project.controllers;

import com.example.project.models.ContactMessage;
import com.example.project.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactMessage> sendMessage(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(contactService.saveMessage(message));
    }
}
