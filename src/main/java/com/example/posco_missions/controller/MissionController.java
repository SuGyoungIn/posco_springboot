package com.example.posco_missions.controller;


import com.example.posco_missions.model.dto.CertificationDto;
import com.example.posco_missions.model.dto.MissionDto;
import com.example.posco_missions.model.dto.UserDto;
import com.example.posco_missions.service.CertificationService;
import com.example.posco_missions.service.MissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@SessionAttributes("user")
public class MissionController {

    private final MissionService missionService;
    private final CertificationService certificationService;

    @Autowired
    public MissionController (MissionService missionService,CertificationService certificationService ){
        this.missionService = missionService;
        this.certificationService = certificationService;
    }

    @ModelAttribute("user")
    public UserDto getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return (UserDto) session.getAttribute("user");
    }

    @GetMapping("/mission")
    public String createMission(Model model,@ModelAttribute("user") UserDto userDto){
        model.addAttribute("user",userDto);
        List<MissionDto> missionList = missionService.recommendationMission();
        System.out.println(missionList);
        model.addAttribute("recommendationList", missionList);
        return "createMission";
    }

    @PostMapping("/mission")
    public String insertMission(@RequestBody MissionDto missionDto,@ModelAttribute("user") UserDto userDto){

        missionService.insetMission(missionDto);
        return "redirect:/mission/" + missionDto.getId();
    }

    @GetMapping("/mission/{id}")
    public String getMission(@PathVariable int id, Model model,HttpServletRequest request){
        MissionDto missionDto = missionService.getOneMission(id);
        model.addAttribute("mission", missionDto);

        HttpSession session = request.getSession(false);
        UserDto userDto = (UserDto) session.getAttribute("user");
        model.addAttribute("user",userDto);

        // certificationList보내기
        List<CertificationDto> certificationList = certificationService.getCertificationList(id);
        for(int i=0; i < certificationList.size();i++){
            certificationService.generateBase64Image(certificationList.get(i));
        }
        model.addAttribute("certificationList", certificationList);

        return "mission";
    }


}
