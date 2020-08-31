package com.mindhunter.springboot.mas.controller;

import com.mindhunter.springboot.mas.entity.*;
import com.mindhunter.springboot.mas.entity.Project;
import com.mindhunter.springboot.mas.service.*;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PeopleController {
    @Autowired
    DesignerServiceImpl designerService;
    @Autowired
    ItSpecialistServiceImpl itSpecialistService;
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("/people")
    public String getProject(Model thModel){

        /*Company c2 = new Company(
       "c2",
       "design"
        );
        Department.createDepartment("c2-design1", "centrum", c2);
        Department.createDepartment("c2-design2", "centrum", c2);
        companyService.save(c2);
        List<Company> companies = companyService.findAll();
        List<WorkingDays> wd = new ArrayList<>();
        wd.add(WorkingDays.WEDNESDAY);*/

//        Designer designer1 = new Designer(
//                "designerTest1",
//                "test1",
//                "test111@gmail.com",
//                 wd,
//                "111-222-564",
//                false,
//                1200,
//                9,
//                companies.get(0).getCopyDepartments().get(0));
//        Designer designer2 = new Designer(
//                "designerTest2",
//                "test2",
//                "test222@gmail.com",
//                wd,
//                "111-222-564",
//                false,
//                1400,
//                9,
//                companies.get(0).getCopyDepartments().get(1));

//        designerService.save(designer1);
//        designerService.save(designer2);

        /*IT_Specialist specialist1 = new IT_Specialist(
                "specialistTest3",
                "test3",
                "test322@gmail.com",
                wd,
                "131-221-561",
                false,
                1500,
                5,
                100,
                companies.get(0).getCopyDepartments().get(0),
                "frontend developer");

        IT_Specialist specialist2 = new IT_Specialist(
                "specialistTest4",
                "test4",
                "test114@gmail.com",
                wd,
                "111-562-564",
                false,
                1550,
                3,
                150,
                companies.get(0).getCopyDepartments().get(1),
                "backend developer");

        itSpecialistService.save(specialist1);
        itSpecialistService.save(specialist2);*/

        List<Designer> designers = designerService.findAll();
        for (Designer designer : designers) {
            System.out.println(designer.getClass());
        }
        List<IT_Specialist> it_specialists = itSpecialistService.findAll();
        for (IT_Specialist it : it_specialists) {
            System.out.println(it.getClass());
        }
        thModel.addAttribute("designers", designers);
        thModel.addAttribute("its", it_specialists);
        return "people/people-list";
    }

    @GetMapping("/add_to_team")
    public String addToTeam(Model thModel){
        List<IT_Specialist> its = itSpecialistService.findAll();
        List<Designer> designers = designerService.findAll();
        List<Team> teams = teamService.findAll();
        thModel.addAttribute("teams", teams);
        thModel.addAttribute("its", its);
        thModel.addAttribute("designers", designers);
        return "people/add_to_team";
    }

    @PostMapping("/add_to_team")
    public String saveTeamState(
            @RequestParam("worker") String workerId,
            @RequestParam("team") String teamId
    ) {
        Team team = teamService.findById(Integer.parseInt(teamId));
        Designer designer = designerService.find(Integer.parseInt(workerId));
        if (designer == null) {
            IT_Specialist specialist = itSpecialistService.find(Integer.parseInt(workerId));
            if (specialist != null) {
                specialist.addTeamForSpecialist(team);
                itSpecialistService.save(specialist);
            }
        }
        else {
            designer.addTeam(team);
            designerService.save(designer);
        }
        return "redirect:/";
    }
}
