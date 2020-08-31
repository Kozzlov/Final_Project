package com.mindhunter.springboot.mas.service;

import com.mindhunter.springboot.mas.dao.ClientRepository;
import com.mindhunter.springboot.mas.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client find(int theId) {
        Optional<Client> result = clientRepository.findById(theId);
        Client client;
        if(result.isPresent()){
            client = result.get();
        }else{
            throw new RuntimeException(" No client with id - " + theId);
        }
        return client;
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void delete(int theId) {
        clientRepository.deleteById(theId);
    }
}

