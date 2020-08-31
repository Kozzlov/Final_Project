package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DesignerRepository extends JpaRepository<Designer, Integer> {
}

