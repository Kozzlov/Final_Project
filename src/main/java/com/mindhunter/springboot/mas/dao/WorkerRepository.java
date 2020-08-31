package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}