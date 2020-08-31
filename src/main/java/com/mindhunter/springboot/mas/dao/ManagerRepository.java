package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.IT_Specialist;
import com.mindhunter.springboot.mas.entity.Manager_Department_Head;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager_Department_Head, Integer>  {
}
