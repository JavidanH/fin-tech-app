package com.javidan.fintech.service;


import com.javidan.fintech.dto.request.AuthenticationRequestDTO;
import com.javidan.fintech.dto.request.UserRequestDTO;
import com.javidan.fintech.dto.response.CommonResponseDTO;
import com.javidan.fintech.dto.response.Status;
import com.javidan.fintech.dto.response.StatusCode;
import com.javidan.fintech.dto.response.UserResponseDTO;
import com.javidan.fintech.entity.TechUser;
import com.javidan.fintech.exception.NoSuchUserExistException;
import com.javidan.fintech.exception.UserAlreadyExist;
import com.javidan.fintech.repository.UserRepository;
import com.javidan.fintech.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private DTOUtil dtoUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public CommonResponseDTO<?> saveUser(UserRequestDTO userRequestDTO) {
        dtoUtil.isValid(userRequestDTO);

        if (userRepository.findByPin(userRequestDTO.getPin()).isPresent()) {
            throw new UserAlreadyExist(CommonResponseDTO.builder().status(Status.builder()
                    .statusCode(StatusCode.USER_EXIST)
                    .message("USER ALREADY EXIST")
                    .build()).build());
        }

        TechUser user = TechUser.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .pin(userRequestDTO.getPin())
                .role("Role_User").build();

        user.addAccountToUser((userRequestDTO.getAccountRequestDTOList()));

        return CommonResponseDTO.builder().status(Status.builder()
                        .statusCode(StatusCode.SUCCES)
                        .message("User Created").build())
                .data(UserResponseDTO.entityResponce(userRepository.save(user)))
                .build();
    }

    public CommonResponseDTO<?> loginUser(AuthenticationRequestDTO authenticationRequestDTO) {
        dtoUtil.isValid(authenticationRequestDTO);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.getPin(),
                    authenticationRequestDTO.getPassword()

            ));
        } catch (Exception e) {
            throw NoSuchUserExistException.builder()
                    .responseDTO(CommonResponseDTO.builder()
                            .status(Status.builder()
                                    .statusCode(StatusCode.USER_NOT_EXIST)
                                    .message("There is no user whith this pin: " + authenticationRequestDTO.getPin())
                                    .build())
                            .build())
                    .build();
        }

        return CommonResponseDTO.builder()
                .data(authenticationRequestDTO)
                .status(Status.builder().statusCode(StatusCode.SUCCES).message("Welcome").build()).build();
    }
}
