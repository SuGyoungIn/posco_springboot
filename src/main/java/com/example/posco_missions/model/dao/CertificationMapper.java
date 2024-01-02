package com.example.posco_missions.model.dao;

import com.example.posco_missions.model.dto.CertificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CertificationMapper {

    List<CertificationDto> getAllCertification(int missionId);

    CertificationDto checkCertification(String userId,int missionId);
    int insertCertification(CertificationDto certificationDto);

    int updateCertification(CertificationDto certificationDto);

    int deleteCertification(CertificationDto certificationDto);

}
