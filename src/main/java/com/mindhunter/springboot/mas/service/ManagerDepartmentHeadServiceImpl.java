package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.ManagerRepository;
import com.mindhunter.springboot.mas.entity.IT_Specialist;
import com.mindhunter.springboot.mas.entity.Manager_Department_Head;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ManagerDepartmentHeadServiceImpl {

@Autowired
private ManagerRepository managerRepository;

public List<Manager_Department_Head> findAll() {
    return managerRepository.findAll();
}

public Manager_Department_Head find(int theId) {
    Optional<Manager_Department_Head> result = managerRepository.findById(theId);
    Manager_Department_Head manager;
    if(result.isPresent()){
        manager = result.get();
    }else{
        throw new RuntimeException(" No manager with id - " + theId);
    }
    return manager;
}

public void save(Manager_Department_Head manager) {
    managerRepository.save(manager);
}

public void delete(int theId) {
    managerRepository.deleteById(theId);
}
}
