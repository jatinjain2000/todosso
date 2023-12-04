package com.todo.controllers;

import java.util.List;
import java.util.Optional;

import com.todo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import com.todo.entities.User;
import com.todo.services.UserService;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@CrossOrigin("*")
public class UserController {
    private Map<String, String> userCaptchaMap = new HashMap<>();

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public Optional<User> getUserById(@PathVariable("userId") Integer userId) {
        return this.userService.getUserById(userId);
    }

    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/signin/{email}/{password}")
    public User getUserByEmailAndPassword(@PathVariable("email") String email,
            @PathVariable("password") String password) {
        return this.userService.getUserByEmailAndPassword(email, password);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
    }

    private String storedCaptchaText;
    @GetMapping("/generate/{userId}")
    public ResponseEntity<Map<String, String>> generateCaptcha(@PathVariable("userId")String userId ) throws IOException {
        String captchaText = generateRandomText();
        userCaptchaMap.put(userId,captchaText);
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
    @PostMapping("/validate/{userId}")
    public ResponseEntity<String> validateCaptcha(@PathVariable("userId") String userId,@RequestParam String enteredText) {

        String storedCaptchaText = userCaptchaMap.get(userId);
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
        int numDots = 10000;
        g2d.setColor(Color.GRAY);

        for (int i = 0; i < numDots; i++) {
            int x = new Random().nextInt(width);
            int y = new Random().nextInt(height);
            g2d.fillOval(x, y, 2, 2);
        }
    }
    @GetMapping("/user")
    public User getUserFullName(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            int flag=0;
            // Accessing full name from OAuth2User
            String fullName = principal.getAttribute("name");
            String email = principal.getAttribute("email");
            User user=new User();
            // Check if the full name exists
            if (fullName != null && !fullName.isEmpty()) {
                user.setEmail(email);
                user.setUserName(fullName);
                try {
                    this.userService.saveUser(user);
                }catch (Exception e){
                    e.printStackTrace();
                }
                User newuser;
                newuser = userRepo.findByEmail(email);
//                return user;
                return newuser;
            }
        }
        return null;
    }

//    @GetMapping("/me")
//    public ResponseEntity<User> getAuthenticatedUser(OAuth2AuthenticationToken token) {
//        OAuth2User user = token.getPrincipal();
//        String userName = user.getAttribute("name");
//        // Check if the user already exists
//        User existingUser = userRepo.findByUserName(userName);
//        if (existingUser == null) {
//            // Initialize the users object
//            User newUser = new User();
//            newUser.setUserName(userName);
//            // Save the new user
//            userRepo.save(newUser);
//            return ResponseEntity.ok(newUser);
//        } else {
//            // User already exists, return the user
//            return ResponseEntity.ok(existingUser);
//        }
//    }
}








