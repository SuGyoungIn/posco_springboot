package com.example.posco_missions.service;

import com.example.posco_missions.model.dao.MissionMapper;
import com.example.posco_missions.model.dto.MissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MissionService {
    private final MissionMapper missionMapper;

    @Autowired
    public MissionService(MissionMapper missionMapper) {
        this.missionMapper = missionMapper;
    }

    public List<MissionDto> getAllMission(){
        List<MissionDto> missionList =  missionMapper.getAllMission();
        // 정렬 (1순위: endDate, 2순위: startDate)
        missionList.sort(Comparator
                .comparing(MissionDto::getEndDate)
                .thenComparing(MissionDto::getStartDate)
                .reversed());
        return missionList;
    }

    public MissionDto getOneMission(int id){
        return missionMapper.getMissionById(id);
    }

    public int insetMission(MissionDto missionDto){
        return missionMapper.insertMission(missionDto);
    }

    public List<MissionDto> recommendationMission(){
        List<MissionDto> missionList =  missionMapper.getAllMission();
        missionList.sort(Comparator
                .comparing(MissionDto::getParticipants)
                .reversed());
        return missionList.subList(0, Math.min(5, missionList.size()));
    }
}
