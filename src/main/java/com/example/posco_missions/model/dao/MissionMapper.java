package com.example.posco_missions.model.dao;

import com.example.posco_missions.model.dto.MissionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MissionMapper {
    List<MissionDto> getAllMission();

    MissionDto getMissionById(@Param("id") int id);

    int insertMission(MissionDto mission);

    int updateParticipantsMission(MissionDto missionDto);
}
