package com.javidan.fintech.util;

import com.javidan.fintech.dto.request.AuthenticationRequestDTO;
import com.javidan.fintech.dto.request.UserRequestDTO;
import com.javidan.fintech.dto.response.CommonResponseDTO;
import com.javidan.fintech.dto.response.Status;
import com.javidan.fintech.dto.response.StatusCode;
import com.javidan.fintech.exception.InvalidDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class DTOUtil {
    @Autowired
    private Logger logger;

    public void isValid(UserRequestDTO userRequestDTO) {
        logger.warn(userRequestDTO.toString());
        checkDTOInputInfo(userRequestDTO.getSurname());
        checkDTOInputInfo(userRequestDTO.getName());
        checkDTOInputInfo(userRequestDTO.getPassword());
        checkDTOInputInfo(userRequestDTO.getAccountRequestDTOList());
    }

    public void isValid(AuthenticationRequestDTO authenticationRequestDTO) {
        logger.warn(authenticationRequestDTO.toString());
        checkDTOInputInfo(authenticationRequestDTO.getPassword());
        checkDTOInputInfo(authenticationRequestDTO.getPin());
    }

    private <T> void checkDTOInputInfo(T t) {
        if (Objects.isNull(t) || toString().isBlank()) {
            logger.error("Invalid Input");
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder()
                    .status(Status.builder().
                            statusCode(StatusCode.INVALID_DTO)
                            .message("Invalid data")
                            .build())
                            .build()).build();

        }
    }
}
