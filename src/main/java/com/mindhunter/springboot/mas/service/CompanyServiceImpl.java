package com.mindhunter.springboot.mas.service;
import com.mindhunter.springboot.mas.dao.CompanyRepository;
import com.mindhunter.springboot.mas.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company find(int theId) {
        Optional<Company> result = companyRepository.findById(theId);
        Company company;
        if(result.isPresent()){
            company = result.get();
        }else{
            throw new RuntimeException(" No company with id - " + theId);
        }
        return company;
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public void delete(int theId) {
        companyRepository.deleteById(theId);
    }
}

