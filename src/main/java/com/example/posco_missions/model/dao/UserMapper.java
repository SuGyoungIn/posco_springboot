package com.example.posco_missions.model.dao;

import com.example.posco_missions.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<UserDto> getAllUser();
    UserDto getUserById(@Param("id") String id);

    int updateUserPoint(UserDto userDto);

}
