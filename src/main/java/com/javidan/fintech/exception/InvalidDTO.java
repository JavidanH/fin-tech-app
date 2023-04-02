package com.javidan.fintech.exception;

import com.javidan.fintech.dto.response.CommonResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class InvalidDTO extends RuntimeException{
    private CommonResponseDTO<?> responseDTO;

}
