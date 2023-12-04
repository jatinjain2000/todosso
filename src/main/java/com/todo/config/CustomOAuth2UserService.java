//package com.todo.config;
//
//import com.todo.entities.User;
//import com.todo.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//// CustomOAuth2UserService.java
//@Service
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    @Autowired
//    private UserRepo userRepo;// Assuming you have a UserRepository
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // Extract user details from the OAuth2User object
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//
//        // Check if the user already exists in the database
//        User existingUser = null;
//         existingUser = userRepo.findByEmail(email);
//
//        if (existingUser!=null) {
//            // Update user details if needed
//
//            existingUser.setUserName(name);
//            // Update other fields as needed
//            userRepo.save(existingUser);
//        } else {
//            // Create a new user
//            User newUser = new User();
//            newUser.setUserName(name);
//            newUser.setEmail(email);
//            // Set other fields as needed
//            userRepo.save(newUser);
//        }
//
//        return oAuth2User;
//    }
//}
