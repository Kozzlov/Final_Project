package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.TeamRepository;
import com.mindhunter.springboot.mas.entity.Project;
import com.mindhunter.springboot.mas.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl  {
    @Autowired
    private TeamRepository teamRepository;


    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(int theId) {
        Optional<Team> result = teamRepository.findById(theId);
        Team theTeam;
        if(result.isPresent()){
            theTeam = result.get();
        }else{
            throw new RuntimeException("Did not find team id - " + theId);
        }
        return theTeam;
    }


    public void save(Team project) {
        teamRepository.save(project);
    }


    public void delete(int theId) {
        teamRepository.deleteById(theId);
    }
}
