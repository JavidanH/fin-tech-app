package com.javidan.fintech.service;


import com.javidan.fintech.dto.request.UserRequestDTO;
import com.javidan.fintech.dto.response.CommonResponseDTO;
import com.javidan.fintech.dto.response.Status;
import com.javidan.fintech.dto.response.StatusCode;
import com.javidan.fintech.dto.response.UserResponseDTO;
import com.javidan.fintech.entity.TechUser;
import com.javidan.fintech.exception.UserAlreadyExist;
import com.javidan.fintech.repository.UserRepository;
import com.javidan.fintech.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private DTOUtil dtoUtil;

    @Autowired
    private UserRepository userRepository;
    public CommonResponseDTO<?> saveUser(UserRequestDTO userRequestDTO){
        dtoUtil.isValid(userRequestDTO);

        if (userRepository.findByPin(userRequestDTO.getPin()).isPresent()){
            throw new UserAlreadyExist(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.USER_EXIST)
                    .message("USER ALREADY EXIST")
                    .build()).build());
        }

        TechUser user = TechUser.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .password(userRequestDTO.getPassword())
                .pin(userRequestDTO.getPin())
                .role("Role_User").build();

        user.addAccountToUser((userRequestDTO.getAccountRequestDTOList()));

        return CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.SUCCES)
                        .message("User Created").build())
                        .data(UserResponseDTO.entityResponce(userRepository.save(user)))
                        .build();
    }
}
