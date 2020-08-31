package com.mindhunter.springboot.mas.dao;
import com.mindhunter.springboot.mas.entity.Client;
import com.mindhunter.springboot.mas.entity.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}