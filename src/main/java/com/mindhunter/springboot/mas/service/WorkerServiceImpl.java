package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.WorkerRepository;
import com.mindhunter.springboot.mas.entity.Project;
import com.mindhunter.springboot.mas.entity.Team;
import com.mindhunter.springboot.mas.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl  {
    @Autowired
    private WorkerRepository workerRepository;


    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public Worker findById(int theId) {
        Optional<Worker> result = workerRepository.findById(theId);
        Worker theWorker;
        if(result.isPresent()){
            theWorker = result.get();
        }else{
            throw new RuntimeException("Did not find worker - " + theId);
        }
        return theWorker;
    }


    public void save(Worker worker) {
        workerRepository.save(worker);
    }


    public void delete(int theId) {
        workerRepository.deleteById(theId);
    }
}
