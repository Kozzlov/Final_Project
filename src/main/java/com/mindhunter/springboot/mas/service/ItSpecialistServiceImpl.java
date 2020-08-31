package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.ItSpecialistRepository;
import com.mindhunter.springboot.mas.entity.IT_Specialist;
import com.mindhunter.springboot.mas.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItSpecialistServiceImpl {

    @Autowired
    private ItSpecialistRepository itSpecialistRepository;

    public List<IT_Specialist> findAll() {
        return itSpecialistRepository.findAll();
    }

    public IT_Specialist find(int theId) {
        Optional<IT_Specialist> result = itSpecialistRepository.findById(theId);
        IT_Specialist specialist;
        if(result.isPresent()){
            specialist = result.get();
        }else{
            throw new RuntimeException(" No IT specialist with id - " + theId);
        }
        return specialist;
    }

    public void save(IT_Specialist specialist) {
        itSpecialistRepository.save(specialist);
    }

    public void delete(int theId) {
        itSpecialistRepository.deleteById(theId);
    }
}
