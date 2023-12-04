package com.todo.controllers;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    private String storedCaptchaText;
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCaptcha() throws IOException {
        String captchaText = generateRandomText();
        storedCaptchaText = captchaText;
        BufferedImage captchaImage = generateCaptchaImage(captchaText);


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "jpg", byteArrayOutputStream);
        String base64Image = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());


        String imageUrl = "data:image/jpeg;base64," + base64Image;


        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validateCaptcha(@RequestParam String enteredText) {
        // Validate the entered text against the stored captcha text
        if (enteredText.equals(storedCaptchaText)) {
            return ResponseEntity.ok("Captcha is valid");
        } else {
            return ResponseEntity.badRequest().body("Captcha is invalid");
        }
    }

    private String generateRandomText() {

        int length = 4;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomText = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            randomText.append(characters.charAt(index));
        }

        return randomText.toString();
    }

    private BufferedImage generateCaptchaImage(String text) {
        int width = 200;
        int height = 80;




        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);


        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.setColor(Color.BLACK);

        // Draw the text on the image
        int x = 40;
        int y = height / 2 + 10;
        g2d.drawString(text, x, y);


        addNoise(g2d, width, height);

        g2d.dispose();

        return image;
    }

    private void addNoise(Graphics2D g2d, int width, int height) {
        // You can customize this method to add noise to the image
        // For simplicity, we'll add random dots
        int numDots = 8000;
        g2d.setColor(Color.GRAY);

        for (int i = 0; i < numDots; i++) {
            int x = new Random().nextInt(width);
            int y = new Random().nextInt(height);
            g2d.fillOval(x, y, 2, 2);
        }
    }
}

