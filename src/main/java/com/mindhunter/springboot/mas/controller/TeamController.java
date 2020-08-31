package com.mindhunter.springboot.mas.controller;

import com.mindhunter.springboot.mas.dao.CompanyRepository;
import com.mindhunter.springboot.mas.entity.*;
import com.mindhunter.springboot.mas.service.CompanyServiceImpl;
import com.mindhunter.springboot.mas.service.ManagerDepartmentHeadServiceImpl;
import com.mindhunter.springboot.mas.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {
    @Autowired
    TeamServiceImpl teamService;
    @Autowired
    ManagerDepartmentHeadServiceImpl managerService;
    @Autowired
    CompanyServiceImpl companyService;

    @GetMapping("/team")
    public String getTeams(Model thModel){
        List<Team> teams = teamService.findAll();
        thModel.addAttribute("teams", teams);
        return "team/team";
    }

    @GetMapping("/team_management/{id}")
    public String getProject(Model thModel, @PathVariable String id){
        Team team = teamService.findById(Integer.parseInt(id));
        thModel.addAttribute("team", team);
        return "team/team_management";
    }
    List<Department> departments = new ArrayList<>();



    @GetMapping("/create_team")
    public String createTeam(Model thModel){
        /*Company c1 = new Company(
               "c1",
               "software"
        );
        Department.createDepartment("test7", "centrum", c1);
        Department.createDepartment("test8", "centrum", c1);
        companyService.save(c1);
        List<Company> companies = companyService.findAll();
        List<WorkingDays> w = new ArrayList<>();
        w.add(WorkingDays.WEDNESDAY);
        Manager_Department_Head manager_department_head = new Manager_Department_Head(
                "Vasya",
                "Ducker",
                "mail112&gmail.com",
                 w,
                "111-222-564",
                true,
                1200,
                10,
                8,
                Access_Level.DEPARTMENT_HEAD,
                companies.get(0).getCopyDepartments().get(1)
        );
        managerService.save(manager_department_head);*/
        Team team = new Team();
        List<Manager_Department_Head> managers = managerService.findAll();
        thModel.addAttribute("team", team);
        thModel.addAttribute("managers", managers);
        return "team/team-creation";
    }

    @PostMapping("/create_team")
    public String saveTeam(
            @ModelAttribute("team") Team team,
            @RequestParam("manager") String managerId
    ) {
        Manager_Department_Head manager = managerService.find(Integer.parseInt(managerId));
        team.setHeadOfTeam(manager);
        teamService.save(team);
        return "redirect:/";
    }

}
