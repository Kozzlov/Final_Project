package com.mindhunter.springboot.mas.controller;

import com.mindhunter.springboot.mas.entity.*;
import com.mindhunter.springboot.mas.service.ClientServiceImpl;
import com.mindhunter.springboot.mas.service.ManagerDepartmentHeadServiceImpl;
import com.mindhunter.springboot.mas.service.ProjectServiceImpl;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectServiceImpl projectService;
    @Autowired
    ManagerDepartmentHeadServiceImpl managerService;
    @Autowired
    ClientServiceImpl clientService;

    @GetMapping("/project")
    public String getProject(Model thModel){
        List<Project> projects = projectService.findAll();
        thModel.addAttribute("projects", projects);
        return "project/project";
    }

    @GetMapping("/project_management/{id}")
    public String getProject(Model thModel, @PathVariable String id){
        Project project = projectService.findById(Integer.parseInt(id));
        thModel.addAttribute("project", project);
        return "project/project_management";
    }

    @GetMapping("/create_project")
    public String createProject(Model thModel){
        /*Client client1 = new Client("111-333-432", 200);
        Client client2 = new Client("111-313-432", 210);
        clientService.save(client1);
        clientService.save(client2);*/

        List<Manager_Department_Head> managers = managerService.findAll();
        List<Client> clients = clientService.findAll();
        Project project = new Project();
        thModel.addAttribute("project", project);
        thModel.addAttribute("managers", managers);
        thModel.addAttribute("clients", clients);
        return "project/project-creation";
    }

    @PostMapping("/create_project")
    public String saveProject(
            @ModelAttribute("project") Project project,
            @RequestParam("dateTime") String dateTime,
            @RequestParam("manager") String managerId,
            @RequestParam("client") String clientId
    ) {
        String[] split = dateTime.split(" ");
        String[] date = split[0].split("-");
        String[] time = split[1].split(":");
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                Integer.parseInt(date[2]),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1])
        );
        project.setDueDate(localDateTime);
        Manager_Department_Head manager = managerService.find(Integer.parseInt(managerId));
        Client client = clientService.find(Integer.parseInt(clientId));
        project.set_Manager(manager);
        project.set_client(client);
        projectService.save(project);
        return "redirect:/";
    }
}
