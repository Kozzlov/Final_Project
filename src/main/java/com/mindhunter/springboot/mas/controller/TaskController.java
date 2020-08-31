package com.mindhunter.springboot.mas.controller;
import com.mindhunter.springboot.mas.entity.*;
import com.mindhunter.springboot.mas.service.ProjectServiceImpl;
import com.mindhunter.springboot.mas.service.TaskServiceImpl;
import com.mindhunter.springboot.mas.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class TaskController {
    @Autowired
    TaskServiceImpl taskService;
    @Autowired
    TeamServiceImpl teamService;
    @Autowired
    ProjectServiceImpl projectService;

    @GetMapping("/task")
    public String getTask(Model thModel){
        List<Task> tasks = taskService.findAll();
        thModel.addAttribute("tasks", tasks);
        return "task/task";
    }

    @GetMapping("/create_task")
    public String createTask(Model thModel){
        Task task = new Task();
        List<Team> teams = teamService.findAll();
        List<Project> projects = projectService.findAll();
        thModel.addAttribute("task", task);
        thModel.addAttribute("teams", teams);
        thModel.addAttribute("projects", projects);
        return "task/task-creation";
    }

    @PostMapping("/create_task")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.save(task);
        return "redirect:/";
    }
}
