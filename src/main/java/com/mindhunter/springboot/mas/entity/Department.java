package com.mindhunter.springboot.mas.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String departmentName;

    @NotNull
    private String departmentAddress;

    @NotNull
    @ManyToOne
    private Company partOf;

    @OneToMany(mappedBy = "workPlace", cascade = CascadeType.PERSIST)
    private List<Worker> workers = new ArrayList<>();

    @OneToMany(mappedBy = "basedIn", cascade = CascadeType.PERSIST)
    private List<Office_Place> officePlaces = new ArrayList<>();


    public Department() {
    }

    private Department(String departmentName,
                       String departmentAddress) {
        setDepartmentName(departmentName);
        setDepartmentAddress(departmentAddress);
    }

    public static Department createDepartment(
            String departmentName,
            String departmentAddress,
            Company partOf
    ) {
        if (partOf == null) throw new IllegalArgumentException(" Company cannot be undefined ");
        Department newDepartment = new Department(departmentName, departmentAddress);
        partOf.addDepartment(newDepartment);
        return newDepartment;
    }


    public List<Office_Place> showParts() {
        return new ArrayList<>(officePlaces);
    }

    public String getDepartmentName() {
        if(departmentName == null){
            throw new IllegalArgumentException("No department name provided");
        }
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        if(departmentName == null){
            throw new IllegalArgumentException("Department name can not be en empty string");
        }
        this.departmentName = departmentName;
    }

    public String getDepartmentAddress() {
        if(departmentName == null){
            throw new IllegalArgumentException("No department address name provided");
        }
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        if(departmentName == null){
            throw new IllegalArgumentException("Department address can not be en empty string");
        }
        this.departmentAddress = departmentAddress;
    }

    public Company getWorksInDepartment() {
        if(partOf == null){
            throw new IllegalArgumentException(" no company was provided ");

        }
        return partOf;
    }

    public void setCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException(" no department was provided ");
        }
        if (this.partOf != null) {
            throw new IllegalArgumentException(" company already has such department");
        }
        this.partOf = company;
        company.addDepartment(this);
    }

    public void unsetCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException(" no department was provided ");
        }
        if (this.partOf == null) {
            throw new IllegalArgumentException(" company does not has such department");
        }
        this.partOf = null;
        company.removeDepartment(this);

    }

    public void addWorker(Worker worker) {
        if (worker == null) {
            throw new IllegalArgumentException(" no worker was provided ");
        }
        if (workers.contains(worker)) {
            throw new IllegalArgumentException(" department already has such worker");
        }
        workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        if (worker == null) {
            throw new IllegalArgumentException(" no worker was provided ");
        }
        if (workers.contains(worker)) {
            throw new IllegalArgumentException(" department has no such worker");
        }
        this.partOf = null;
        worker.unsetDepartment(this);
    }

    public void addOfficePlace(Office_Place placeToAdd) {
        if (officePlaces.contains(placeToAdd) || placeToAdd.getDepartment() != null) {
            throw new IllegalArgumentException("This office place is a part of another department");
        }
        officePlaces.add(placeToAdd);
        placeToAdd.setDepartment(this);
    }

    public void removeOfficePlace(Office_Place placeToRemove) {
        if (!officePlaces.contains(placeToRemove) || placeToRemove.getDepartment() == null) {
            throw new IllegalArgumentException("This office place is not a part of this department");
        }
        officePlaces.remove(placeToRemove);
        placeToRemove.unsetDepartment(null);

    }

    private List<Worker> getWorkers() {
        return workers;
    }
    public List<Worker> getCopyWorkers() {
        return new ArrayList<>(workers);
    }
    private void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    private List<Office_Place> getOfficePlaces() { return officePlaces; }
    public List<Office_Place> getCopyOfficePlaces() {
        return new ArrayList<>(officePlaces);
    }
    private void SetOfficePlaces(List<Office_Place> officePlaces) {
        this.officePlaces = officePlaces;
    }

    public Company getCompany() { return partOf; }
    private void SetOfficePlaces(Company partOf) {
        this.partOf = partOf;
    }
}
