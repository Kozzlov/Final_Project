package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.TaskRepository;
import com.mindhunter.springboot.mas.entity.Project;
import com.mindhunter.springboot.mas.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskRepository taskRepository;


    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(int theId) {
        Optional<Task> result = taskRepository.findById(theId);
        Task task;
        if(result.isPresent()){
            task = result.get();
        }else{
            throw new RuntimeException("Did not find task id - " + theId);
        }
        return task;
    }



    public void save(Task project) {
        taskRepository.save(project);
    }

    public void delete(int theId) {
        taskRepository.deleteById(theId);
    }
}
