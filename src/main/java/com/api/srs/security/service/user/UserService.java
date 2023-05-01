package com.api.srs.security.service.user;

import com.api.srs.security.dto.user.UserDto;
import com.api.srs.security.entity.user.UserEntity;
import com.api.srs.security.repository.user.UserRepository;
import com.api.srs.shared.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserEntity> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<UserDto> listAllUsers() {
        return this.userRepository.listAllUsers();
    }

    public List<UserDto> listUserByFilters(UserDto userDto) {
        return this.userRepository.listUserByFilters(
                Validator.validateStringNullOrEmpty(userDto.firstname() + " " + userDto.lastname()),
                Validator.validateStringNullOrEmpty(userDto.email())
        );
    }

    @Transactional
    public UserEntity saveUser(UserEntity userEntity) {
        return this.userRepository.saveAndFlush(userEntity);
    }
}