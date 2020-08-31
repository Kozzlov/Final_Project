package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
