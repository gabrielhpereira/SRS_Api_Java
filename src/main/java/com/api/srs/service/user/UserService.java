package com.api.srs.service.user;

import com.api.srs.dto.user.UserDto;
import com.api.srs.entity.user.UserEntity;
import com.api.srs.repository.user.UserRepository;
import com.api.srs.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<UserVo> listAllUsers() {
        return this.userRepository.listAllUsers();
    }

    public List<UserVo> listUserByFilters(UserDto dto) {
        return this.userRepository.listUserByFilters(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    @Transactional
    public void saveOrUpdateUser(UserDto dto) {
        UserEntity user =
                new UserEntity
                        .Builder()
                        .id(dto.getId() == null ? null : dto.getId())
                        .name(dto.getName() == null || dto.getName().equals("") ? null : dto.getName())
                        .email(dto.getEmail() == null || dto.getEmail().equals("") ? null : dto.getEmail())
                        .address(dto.getAddress() == null || dto.getAddress().equals("") ? null : dto.getAddress())
                        .build();

        this.userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        this.userRepository.deleteById(id);
    }
}