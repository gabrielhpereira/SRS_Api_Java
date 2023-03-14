package com.api.srs.service.user;

import com.api.srs.dto.user.UserDto;
import com.api.srs.entity.user.UserEntity;
import com.api.srs.repository.user.UserRepository;
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
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAddress()
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

        user.setName(userDto.getName().toUpperCase().trim());
        user.setAddress(userDto.getAddress().toUpperCase().trim());
        user.setEmail(userDto.getEmail().trim());

        this.userRepository.saveAndFlush(user);

        this.logUserService.saveLogUpdateUser(user, oldUser);
    }

    private void saveUser(UserDto userDto) {
        UserEntity user =
                new UserEntity
                        .Builder()
                        .id(userDto.getId())
                        .name(userDto.getName().toUpperCase().trim())
                        .email(userDto.getEmail().trim())
                        .address(userDto.getAddress().toUpperCase().trim())
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
        if (userDto.getName().isBlank() || userDto.getName() == null)
            throw new ValidationException("Name is empty or null");

        if (userDto.getAddress().isBlank() || userDto.getAddress() == null)
            throw new ValidationException("Address is empty or null");

        if (userDto.getEmail().isBlank() || userDto.getEmail() == null)
            throw new ValidationException("Price is empty or null");
    }
}