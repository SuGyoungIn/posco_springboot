package com.example.posco_missions.service;

import com.example.posco_missions.model.dao.UserMapper;
import com.example.posco_missions.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUser(){
        List<UserDto> userList = userMapper.getAllUser();
        userList.sort(Comparator.comparing(UserDto::getPoint).reversed());
        return userList;
    }

    public UserDto getOneUser(String id){
        return userMapper.getUserById(id);
    }

}
