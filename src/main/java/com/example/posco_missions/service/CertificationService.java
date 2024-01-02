package com.example.posco_missions.service;

import com.example.posco_missions.model.dao.CertificationMapper;
import com.example.posco_missions.model.dao.MissionMapper;
import com.example.posco_missions.model.dao.UserMapper;
import com.example.posco_missions.model.dto.CertificationDto;
import com.example.posco_missions.model.dto.MissionDto;
import com.example.posco_missions.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CertificationService {

    private final CertificationMapper certificationMapper;
    private final UserMapper userMapper;
    private final MissionMapper missionMapper;

    @Autowired
    public  CertificationService(CertificationMapper certificationMapper, UserMapper userMapper, MissionMapper missionMapper){
        this.certificationMapper = certificationMapper;
        this.userMapper = userMapper;
        this.missionMapper = missionMapper;
    }


    public int uploadCertification(UserDto userDto, MissionDto missionDto, MultipartFile file){
        int flag1 = 0;
        int flag2;
        int flag3;
        // 인증샷 올리기
        try {
            CertificationDto certificationDto = new CertificationDto();
            certificationDto.setUserId(userDto.getId());
            certificationDto.setMissionId(missionDto.getId());
            certificationDto.setImage(file.getBytes());

            Date currentDate = new Date();

            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            certificationDto.setDate(currentTimestamp);
            flag1 = certificationMapper.insertCertification(certificationDto);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        // 유저의 포인트 올리기
        int newPoint = userDto.getPoint() + missionDto.getPoint();
        userDto.setPoint(newPoint);
        flag2 = userMapper.updateUserPoint(userDto);

        // 미션의 참여자수 올리기
        int cnt = missionDto.getParticipants();
        missionDto.setParticipants(cnt+1);
        flag3 = missionMapper.updateParticipantsMission(missionDto);

        if(flag1>0 && flag2>0 && flag3>0)return 1;

        return 0;
    }

    public List<CertificationDto> getCertificationList(int missionId){
        List<CertificationDto> certificationDtoList = certificationMapper.getAllCertification(missionId);
        certificationDtoList.sort(Comparator.comparing(CertificationDto::getDate).reversed());
        return certificationDtoList;
    }

    public CertificationDto checkCertification(String userId,int missionId){
        CertificationDto result = certificationMapper.checkCertification(userId, missionId);

        if (result != null) return result;
        return new CertificationDto();
    }

    public boolean updateCertification(String userId,int missionId, MultipartFile file){

        try {
            CertificationDto certificationDto = checkCertification(userId,missionId);
            certificationDto.setImage(file.getBytes());
            Date currentDate = new Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            certificationDto.setDate(currentTimestamp);
            int cnt = certificationMapper.updateCertification(certificationDto);
            return cnt > 0;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    public int deleteCertification(UserDto userDto, MissionDto missionDto){
        // 인증샷 삭제
        int flag1;
        int flag2;
        int flag3;
        CertificationDto certificationDto = certificationMapper.checkCertification(userDto.getId(),missionDto.getId());
        flag1 = certificationMapper.deleteCertification(certificationDto);

        // 포인트 삭감
        int newPoint = userDto.getPoint() - missionDto.getPoint();
        userDto.setPoint(newPoint);
        flag2 = userMapper.updateUserPoint(userDto);

        // 참여자 감소
        int cnt = missionDto.getParticipants();
        missionDto.setParticipants(cnt-1);
        flag3 = missionMapper.updateParticipantsMission(missionDto);

        if(flag1>0 && flag2>0 && flag3>0)return 1;
        return 0;
    }

    public CertificationDto generateBase64Image(CertificationDto certificationDto){
        String url = Base64.getEncoder().encodeToString(certificationDto.getImage());
        certificationDto.setImageUrl(url);
        return  certificationDto;
    }
}
