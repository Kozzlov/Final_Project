package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
