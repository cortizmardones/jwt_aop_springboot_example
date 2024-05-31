package com.example.demo.utils;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

import java.nio.charset.StandardCharsets;

import org.owasp.html.Sanitizers;

import com.example.demo.dto.User;

public class SanitizationUtil {
	
    public static String sanitizeInput(String input) {
    	return htmlEscape(input, String.valueOf(StandardCharsets.UTF_8));
    }
    
    public static User sanitizeUser(User user) {
    	
    	return User.builder()
    			.rut(htmlEscape(user.rut(), String.valueOf(StandardCharsets.UTF_8)))
    			.name(htmlEscape(user.name(), String.valueOf(StandardCharsets.UTF_8)))
    			.lastName(htmlEscape(user.lastName(), String.valueOf(StandardCharsets.UTF_8)))
    			.age(htmlEscape(user.age(), String.valueOf(StandardCharsets.UTF_8)))
    			.build();
    }
    
    public static String sanitizeInputOwasp(String input) {    	
        return Sanitizers.FORMATTING.and(Sanitizers.LINKS).sanitize(input);
    }

}
