package com.ev.momcalcboot.service.internal;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;


import java.util.Arrays;

@Service
@AllArgsConstructor
public class CookService {

    public Cookie findCookByName(HttpServletRequest request, String nameCook){

        Cookie[] cookie = request.getCookies();

        return Arrays.stream(cookie)
                .filter(cook -> cook.getName().equals(nameCook)).findAny().orElse(null);
    }

    public void setCookiesForUserResponse(HttpServletResponse response, String userId, String userName, String userRole){

        response.addCookie(new Cookie("userId", userId));

        response.addCookie(new Cookie("userName", userName));

        response.addCookie(new Cookie("userRole", userRole));
    }

}
