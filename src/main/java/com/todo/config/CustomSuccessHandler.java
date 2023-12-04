//package com.todo.config;
//
//import com.todo.entities.User;
//import com.todo.repository.UserRepo;
//import com.todo.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Component
//public class CustomSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    UserRepo userRepo;
//
//    @Autowired
//    UserService userService;
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException{
//        String redirectUrl = null;
//        if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
//            DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
//            String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
//            if(userRepo.findByEmail(username) == null) {
////                UserRegisteredDTO user = new UserRegisteredDTO();
//                User user = new User();
//                user.setUserName(username);
//                user.setEmail(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
//                user.setPassword(("Dummy"));
//                Integer id =user.getUserId();
//                userRepo.save(user);
//            }
//        }
//        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
//    }
//}
