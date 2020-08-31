package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}