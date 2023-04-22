package com.api.srs.service.user;

import com.api.srs.dto.user.UserDto;
import com.api.srs.entity.user.UserEntity;
import com.api.srs.repository.user.UserRepository;
import com.api.srs.shared.Validator;
import com.api.srs.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogUserService logUserService;

    public List<UserVo> listAllUsers() {
        return this.userRepository.listAllUsers();
    }

    public List<UserVo> listUserByFilters(UserDto userDto) {
        return this.userRepository.listUserByFilters(
                Validator.validateStringNullOrEmpty(userDto.getName()),
                Validator.validateStringNullOrEmpty(userDto.getEmail()),
                Validator.validateStringNullOrEmpty(userDto.getAddress())
        );
    }

    @Transactional
    public void saveOrUpdateUser(UserDto userDto) {
        validateUserDto(userDto);
        if (userDto.getId().equals(0) || userDto.getId() == null)
            this.saveUser(userDto);
        else
            this.updateUser(userDto);
    }

    private void updateUser(UserDto userDto) {
        UserEntity user = this.userRepository.getReferenceById(userDto.getId());
        UserEntity oldUser =
                new UserEntity
                        .Builder()
                        .name(user.getName())
                        .address(user.getAddress())
                        .email(user.getEmail())
                        .build();

        user.setName(userDto.getName().trim());
        user.setAddress(userDto.getAddress().trim());
        user.setEmail(userDto.getEmail().trim());

        this.userRepository.saveAndFlush(user);

        this.logUserService.saveLogUpdateUser(user, oldUser);
    }

    private void saveUser(UserDto userDto) {
        UserEntity user =
                new UserEntity
                        .Builder()
                        .id(userDto.getId())
                        .name(userDto.getName().trim())
                        .email(userDto.getEmail().trim())
                        .address(userDto.getAddress().trim())
                        .build();

        this.userRepository.saveAndFlush(user);

        this.logUserService.saveLogNewUser(user);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        UserEntity user = this.userRepository.getReferenceById(id);

        this.userRepository.deleteById(user.getId());

        this.logUserService.saveLogDeleteUser(user);
    }

    private static void validateUserDto(UserDto userDto) {
        if (userDto.getName() == null || userDto.getName().isBlank())
            throw new ValidationException("Name is empty or null");

        if (userDto.getAddress() == null || userDto.getAddress().isBlank())
            throw new ValidationException("Address is empty or null");

        if (userDto.getEmail() == null || userDto.getEmail().isBlank())
            throw new ValidationException("Price is empty or null");
    }
}