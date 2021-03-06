package com.softserve.academy.controller;

import com.softserve.academy.model.User;
import com.softserve.academy.service.AuthenticationTokenService;
import com.softserve.academy.security.UserCredential;
import com.softserve.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Value("${security.headerName}")
    private String HEADER_NAME;

    @Autowired private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void getAuthenticationToken(@RequestBody UserCredential credential, HttpServletResponse response) {

        User user = userService.loadUserByUsername(credential.getUsername());
        String fullToken;

        if (user.getPassword().equals(credential.getPassword())) {
            fullToken = AuthenticationTokenService.createToken(user);
            response.addHeader(HEADER_NAME, fullToken);
        } else {
            System.err.println("Password is incorrect");
        }
    }
}