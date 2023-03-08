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

    public void saveLog(String description, Date date, UserEntity user) {
        LogUserEntity logUser = new LogUserEntity();
        logUser.setUser(user);
        logUser.setDate(date);
        logUser.setDescription(description);

        this.logUserRepository.save(logUser);
    }
}