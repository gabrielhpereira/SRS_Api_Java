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
//
//    @Transactional
//    public void saveOrUpdateUser(UserDto userDto) {
//        validateUserDto(userDto);
//        if (userDto.getId().equals(0) || userDto.getId() == null)
//            this.saveUser(userDto);
//        else
//            this.updateUser(userDto);
//    }
//
//    private void updateUser(UserDto userDto) {
//        UserEntity user = this.userRepository.getReferenceById(userDto.getId());
//        UserEntity oldUser =
//                new UserEntity
//                        .Builder()
//                        .name(user.getName())
//                        .address(user.getAddress())
//                        .email(user.getEmail())
//                        .build();
//
//        user.setName(userDto.getName().trim());
//        user.setAddress(userDto.getAddress().trim());
//        user.setEmail(userDto.getEmail().trim());
//
//        this.userRepository.saveAndFlush(user);
//
//        this.logUserService.saveLogUpdateUser(user, oldUser);
//    }
//
//    private void saveUser(UserDto userDto) {
//        UserEntity user =
//                new UserEntity
//                        .Builder()
//                        .id(userDto.getId())
//                        .name(userDto.getName().trim())
//                        .email(userDto.getEmail().trim())
//                        .address(userDto.getAddress().trim())
//                        .build();
//
//        this.userRepository.saveAndFlush(user);
//
//        this.logUserService.saveLogNewUser(user);
//    }
//
//    @Transactional
//    public void deleteUserById(Integer id) {
//        UserEntity user = this.userRepository.getReferenceById(id);
//
//        this.userRepository.deleteById(user.getId());
//
//        this.logUserService.saveLogDeleteUser(user);
//    }
//
//    private static void validateUserDto(UserDto userDto) {
//        if (userDto.name() == null || userDto.getName().isBlank())
//            throw new ValidationException("Name is empty or null");
//
//        if (userDto.getAddress() == null || userDto.getAddress().isBlank())
//            throw new ValidationException("Address is empty or null");
//
//        if (userDto.getEmail() == null || userDto.getEmail().isBlank())
//            throw new ValidationException("Price is empty or null");
//    }
}