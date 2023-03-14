package com.api.srs.service.user;

import com.api.srs.entity.user.LogUserEntity;
import com.api.srs.entity.user.UserEntity;
import com.api.srs.repository.user.LogUserRepository;
import com.api.srs.vo.user.LogUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogUserService {

    @Autowired
    private LogUserRepository logUserRepository;

    public List<LogUserVo> listAllLogUser(Integer idUser) {
        return this.logUserRepository.listAllLogUser(idUser);
    }

    public void saveLogUpdateUser(UserEntity newUser, UserEntity oldUser) {
        StringBuilder sb = new StringBuilder();

        if (newUser.getName().toUpperCase().trim().equals(oldUser.getName().toUpperCase().trim()))
            sb.append("Name changed from ")
                    .append(oldUser.getName().toUpperCase().trim())
                    .append(" to ").append(newUser.getName().toUpperCase().trim())
                    .append("\n");

        if (newUser.getAddress().toUpperCase().trim().equals(oldUser.getAddress().toUpperCase().trim()))
            sb.append("Address changed from ")
                    .append(oldUser.getAddress().toUpperCase().trim())
                    .append(" to ").append(newUser.getAddress().toUpperCase().trim())
                    .append("\n");

        if (newUser.getEmail().trim().equals(oldUser.getEmail().trim()))
            sb.append("Email changed from ")
                    .append(oldUser.getEmail().trim())
                    .append(" to ").append(newUser.getEmail().trim())
                    .append("\n");

        if (!sb.toString().isEmpty())
            this.logUserRepository.save(
                    new LogUserEntity
                            .Builder()
                            .userId(newUser.getId())
                            .description("Product " + newUser.getId() + " : \n\n" + sb.toString())
                            .date(now())
                            .build());
    }

    public void saveLogNewUser(UserEntity user) {
        this.logUserRepository.save(
                new LogUserEntity
                        .Builder()
                        .userId(user.getId())
                        .description("User " + user.getId() + " - " + user.getName().trim() + " has been created!")
                        .date(now())
                        .build());
    }

    public void saveLogDeleteUser(UserEntity user) {
        this.logUserRepository.save(
                new LogUserEntity
                        .Builder()
                        .userId(user.getId())
                        .description("User " + user.getId() + " - " + user.getName().trim() + " has been deleted!")
                        .date(now())
                        .build());
    }

    private static Date now() {
        return new Date();
    }
}