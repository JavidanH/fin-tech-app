package com.javidan.fintech.config.security;

import com.javidan.fintech.dto.response.CommonResponseDTO;
import com.javidan.fintech.dto.response.Status;
import com.javidan.fintech.dto.response.StatusCode;
import com.javidan.fintech.entity.TechUser;
import com.javidan.fintech.exception.NoSuchUserExistException;
import com.javidan.fintech.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Logger logger;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Optional<TechUser> byPin = userRepository.findByPin(pin);
        if (byPin.isPresent()) {
            return new MyUserDetails(byPin.get());
        } else {
           logger.error("There is no user vith this pin: " + pin);
           throw NoSuchUserExistException.builder()
                   .responseDTO(CommonResponseDTO.builder()
                           .status(Status.builder()
                                   .statusCode(StatusCode.USER_NOT_EXIST)
                                   .message("There is no user whith this pin: " + pin)
                                   .build())
                           .build())
                   .build();
        }
    }
}
