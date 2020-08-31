package com.mindhunter.springboot.mas.entity;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique=true)
    private String email;

    @NotNull
    @ElementCollection
    private List<WorkingDays> workingDays = new ArrayList<>();

    @NotNull
    private String mainContact;

    private String additionalContact = "";

    @NotNull
    private boolean ifBusy;

    @NotNull
    private int salary;

    @NotNull
    private int numberOfWeekends; // class attribute

    @ManyToOne
    private Department workPlace;

    @OneToOne(mappedBy = "placeOwner")
    private Office_Place assignedToPlace;

    public Worker() {
    }

    public Worker(
            String firstName,
            String lastName,
            String email,
            List<WorkingDays> workingDays,
            String mainContact,
            boolean ifBusy,
            int salary,
            int numberOfWeekends,
            Department department
    ) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setWorkingDays(workingDays);
        setMainContact(mainContact);
        setIfBusy(ifBusy);
        setSalary(salary);
        setNumberOfWeekends(numberOfWeekends);
        setWorkingPlace(department);
    }
    // overloads
    public Worker(String firstName, String lastName,
                  String email, List<WorkingDays> workingDays,
                  String mainContact,
                  String additionalContact,
                  boolean ifBusy,
                  int salary,
                  int numberOfWeekends) {
        setFirstName(firstName);
        setLastName(firstName);
        setEmail(email);
        setWorkingDays(workingDays);
        setMainContact(mainContact);
        this.additionalContact = additionalContact; // optional
        setIfBusy(ifBusy);
        setSalary(salary);
        setNumberOfWeekends(numberOfWeekends);
        setWorkingPlace(workPlace);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return new String(firstName);
    }

    public void setFirstName(String firstName) {
        if(firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return new String(email);
    }

    public void setEmail(String email) {
        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.email = email;
    }

    public List<WorkingDays> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WorkingDays> workingDays) {
        if(workingDays != null && workingDays.size() < 1){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.workingDays = workingDays;
    }

    public String getMainContact() {
        return new String(mainContact);
    }

    public void setMainContact(String mainContact) {
        if(mainContact == null || mainContact.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.mainContact = mainContact;
    }

    public String getAdditionalContact() {
        return new String(additionalContact);
    }

    public void setAdditionalContact(String additionalContact) {
        if(additionalContact == null || additionalContact.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.additionalContact = additionalContact;
    }

    public boolean getIfBusy() {
        return ifBusy;
    }

    //attribute constraint
    public void setIfBusy(boolean status) {
        this.ifBusy = status;
    }

    public int getSalary() {
        return salary;
    }

    //custom constraint
    public void setSalary(int new_salary) {
        if(Math.abs(new_salary - salary) > 10000){
            throw new IllegalArgumentException("New salary cannot differ from the previous one by more or less than 1000");
        }
        if( new_salary < 0){
            throw new IllegalArgumentException("New salary cannot be less than minimal possible allowed");

        }
        this.salary = salary;
    }

    public int getNumberOfWeekends() {
        return numberOfWeekends;
    }

    public void setNumberOfWeekends(int numberOfWeekends) {
        if(numberOfWeekends < 0 || numberOfWeekends > 10){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.numberOfWeekends = numberOfWeekends;
    }

    public Department getWorkingPlace(){
        if(workPlace == null){
            throw new IllegalArgumentException(" there is no department to be presented");
        }
        return workPlace;
    }

    public void setWorkingPlace(Department department){
        this.workPlace = department;
    }

    public Department getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(Department workPlace) {
        this.workPlace = workPlace;
    }

    public Office_Place getAssignedToPlace() {
        return assignedToPlace;
    }

    public void setAssignedToPlace(Office_Place assignedToPlace) {
        this.assignedToPlace = assignedToPlace;
    }

    // override
    @Override
    public String toString() {
        return "Worker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mainContact='" + mainContact + '\'' +
                ", ifBusy='" + ifBusy + '\'' +
                ", salary=" + salary +
                ", numberOfWeekends=" + numberOfWeekends +
                '}';
    }
    public abstract void get_info();
    public abstract int getYearlySalary();

    public void setDepartment(Department department){
        if(department == null){
            throw new IllegalArgumentException(" no department was provided ");
        }
        this.workPlace = department;
        department.addWorker(this);
    }

    public void unsetDepartment(Department department){
        if(department == null){
            throw new IllegalArgumentException(" no department was provided ");
        }
        if(this.workPlace == null){
            throw new IllegalArgumentException(" this worker has no department");
        }
        this.workPlace = null;
        department.removeWorker(this);
    }

    public void setOfficePlace(Office_Place place){
        if(place == null){
            throw new IllegalArgumentException(" no place was provided ");
        }
        this.assignedToPlace = place;
        place.setWorker(this);
    }

    public void unsetOfficePlace(Office_Place place){
        if(place == null){
            throw new IllegalArgumentException(" no place was provided ");
        }
        if(this.assignedToPlace != place){
            throw new IllegalArgumentException(" This worker is not assigned to this place ");
        }
        this.assignedToPlace = null;
        place.unsetWorker(this);
    }
}
