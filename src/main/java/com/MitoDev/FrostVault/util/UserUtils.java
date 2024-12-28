package com.MitoDev.FrostVault.util;

import com.MitoDev.FrostVault.model.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    static ObjectMapper mapper = new ObjectMapper();

    public static User getUserFromContext(){
        String userContext = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user;
        try{
              user = mapper.readValue(userContext, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
