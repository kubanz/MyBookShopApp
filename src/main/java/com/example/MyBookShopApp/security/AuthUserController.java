package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.errs.UserNotFoundException;
import com.example.MyBookShopApp.security.service.BookstoreUserRegister;
import com.example.MyBookShopApp.security.service.JWTBlacklistService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api(value = "User Authentication Controller")
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final JWTBlacklistService jwtBlacklistService;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, JWTBlacklistService jwtBlacklistService) {
        this.userRegister = userRegister;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @GetMapping("/signin")
    public String handleSignIn(){
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) throws UserNotFoundException {
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse httpServletResponse)throws UserNotFoundException {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @GetMapping("/my")
    public String handleMyBooksPage(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse){
        for(Cookie cookie : httpServletRequest.getCookies()){
            if(cookie.getName().equals("token") && jwtBlacklistService.isBlacklisted(cookie.getValue())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);

                httpServletResponse.addCookie(cookie);
                return "redirect:/signin";
            }
        }
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        return "profile";
    }

//    @GetMapping("/logout")
//    public String handleLogout(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        SecurityContextHolder.getContext();
//        if(session != null){
//            session.invalidate();
//        }
//
//        for(Cookie cookie : request.getCookies()){
//            cookie.setMaxAge(0);
//        }
//        return "redirect:/";
//    }
}
