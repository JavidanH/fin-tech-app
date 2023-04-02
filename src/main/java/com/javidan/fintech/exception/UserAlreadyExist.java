package com.javidan.fintech.exception;


import com.javidan.fintech.dto.response.CommonResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserAlreadyExist extends RuntimeException {
    private final CommonResponseDTO<?> responseDTO;

}
