package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}


