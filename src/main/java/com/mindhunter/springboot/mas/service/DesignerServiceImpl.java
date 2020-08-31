package com.mindhunter.springboot.mas.service;
import com.mindhunter.springboot.mas.dao.DesignerRepository;
import com.mindhunter.springboot.mas.entity.Designer;
import com.mindhunter.springboot.mas.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignerServiceImpl {

    @Autowired
    private DesignerRepository designerRepository;

    public List<Designer> findAll() {
        return designerRepository.findAll();
    }
    public Designer find(int theId) {
        Optional<Designer> result = designerRepository.findById(theId);
        Designer designer;
        if(result.isPresent()){
            designer = result.get();
        } else{
            return null;
        }
        return designer;
    }

    public void save(Designer designer) {
        designerRepository.save(designer);
    }

    public void delete(int theId) {
        designerRepository.deleteById(theId);
    }
}
