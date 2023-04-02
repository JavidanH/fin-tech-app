package com.javidan.fintech.exception;


import com.javidan.fintech.dto.response.CommonResponseDTO;
import com.javidan.fintech.dto.response.Status;
import com.javidan.fintech.dto.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> internalError(){
        return new ResponseEntity<>(CommonResponseDTO.builder().status(Status.builder()
                          .statusCode(StatusCode.INTERNAL_ERROR)
                          .message("Internal Error").build()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidDTO.class)
    public ResponseEntity<?> invalidDto(InvalidDTO invalidDTO){
        return new ResponseEntity<>(invalidDTO.getResponseDTO(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    public ResponseEntity<?> userExist(UserAlreadyExist userAlreadyExist){
        return new ResponseEntity<>(userAlreadyExist.getResponseDTO(),HttpStatus.CONFLICT);
    }
}
