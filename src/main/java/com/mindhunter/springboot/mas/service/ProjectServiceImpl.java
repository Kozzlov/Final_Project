package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.ProjectRepository;
import com.mindhunter.springboot.mas.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(int theId) {
        Optional<Project> result = projectRepository.findById(theId);
        Project project;
        if(result.isPresent()){
            project = result.get();
        }else{
            throw new RuntimeException(" No project with id - " + theId);
        }
        return project;
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public void delete(int theId) {
        projectRepository.deleteById(theId);
    }
}
