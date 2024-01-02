package com.example.posco_missions.controller;

import com.example.posco_missions.model.dto.CertificationDto;
import com.example.posco_missions.model.dto.MissionDto;
import com.example.posco_missions.model.dto.UserDto;
import com.example.posco_missions.service.CertificationService;
import com.example.posco_missions.service.MissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@SessionAttributes("user")
@RequestMapping("/mission")
public class CertificationController {

    private final CertificationService certificationService;
    private final MissionService missionService;

    @Autowired
    public CertificationController (CertificationService certificationService,MissionService missionService) {
        this.certificationService = certificationService;
        this.missionService = missionService;
    }

    @ModelAttribute("user")
    public UserDto getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        return (UserDto) session.getAttribute("user");
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadCertification(@RequestParam("file") MultipartFile file,@RequestParam("missionId") int missionId, @ModelAttribute("user") UserDto userDto) {

        MissionDto missionDto = missionService.getOneMission(missionId);
        if(certificationService.uploadCertification(userDto, missionDto, file) > 0){
            return ResponseEntity.ok("success");
        } return null;
    }

    @GetMapping("/certification")
    @ResponseBody
    public List<CertificationDto> getCertificationList(@RequestParam int missionId) {
        return certificationService.getCertificationList(missionId);
    }


    @GetMapping("/check-certification")
    @ResponseBody
    public CertificationDto checkCertification(@RequestParam int missionId, @ModelAttribute("user") UserDto userDto) {
        String userId = userDto.getId();
        return certificationService.checkCertification(userId,missionId);
    }

    @PatchMapping("/reupload")
    @ResponseBody
    public ResponseEntity<?> updateCertification(@RequestParam("file") MultipartFile file, @RequestParam("missionId") int missionId, @ModelAttribute("user") UserDto userDto) {
        if(certificationService.updateCertification(userDto.getId(), missionId, file)){
            return ResponseEntity.ok("success");
        }
        return null;
    }

    @DeleteMapping("/certification")
    @ResponseBody
    public ResponseEntity<?> deleteCertification(@RequestParam("missionId") int missionId, @ModelAttribute("user") UserDto userDto){
        MissionDto missionDto = missionService.getOneMission(missionId);
        if(certificationService.deleteCertification(userDto,missionDto)>0){
            return ResponseEntity.ok("success");
        }
        return null;
    }

}
