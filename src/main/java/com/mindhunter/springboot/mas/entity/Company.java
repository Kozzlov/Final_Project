package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String companyName;

    @NotNull
    private String mainPurpose;

    @OneToMany(mappedBy = "partOf", cascade = CascadeType.ALL)
    private List <Department> branches = new ArrayList<>();

    public Company(){}

    public Company(String companyName, String mainPurpose){
        setCompanyName(companyName);
        setMainPurpose(mainPurpose);
    }


    public void addDepartment(Department department) {
        if (department == null) throw new IllegalArgumentException("department must exist");
        if (department.getCompany() != null) {
            if (branches.contains(department)) System.out.println("department is already assigned to this company");
            else throw new IllegalArgumentException("department is already assigned to another company");
        }
        else {
            branches.add(department);
            department.setCompany(this);
        }
    }
    public void removeDepartment(Department department) {
        if (department != null && branches.contains(department)) {
            branches.remove(department);
            department.unsetCompany(this);
        }
        // if (packages.isEmpty()) deleteDelivery();
    }
    private List<Department> getDepartments() {
        return branches;
    }
    public List<Department> getCopyDepartments() {
        return new ArrayList<>(branches);
    }
    private void setDepartments(List<Department> branches) {
        this.branches = branches;
    }

    public String getCompanyName() {
        if(companyName == null){
            throw new IllegalArgumentException("No company name provided");
        }
        return new String(companyName);
    }

    public void setCompanyName(String companyName) {
        if(companyName == null){
            throw new IllegalArgumentException("Empty string can not be company name");
        }
        this.companyName = companyName;
    }

    public String getMainPurpose() {
        if(mainPurpose == null){
            throw new IllegalArgumentException("No mainPurpose provided");
        }
        return mainPurpose;
    }

    public void setMainPurpose(String mainPurpose) {
        if(mainPurpose == null){
            throw new IllegalArgumentException("Empty string can not be mainPurpose");
        }
        this.mainPurpose = mainPurpose;
    }
}
