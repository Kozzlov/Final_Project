package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IT_Specialist extends Worker{

    @NotNull
    private float numberOfWorkingDaysYearly;

    @NotNull
    private String specialization;

    @ManyToMany
    private List<Team> participantOf = new ArrayList<>();

    public IT_Specialist(){}

    public IT_Specialist(
    String firstName,
    String lastName,
    String email,
    List<WorkingDays> workingDays,
    String mainContact,
    boolean ifBusy,
    int salary,
    int numberOfWeekends,
    float numberOfWorkingDaysYearly,
    Department department,
    String specialization){
        super(firstName, lastName, email, workingDays, mainContact, ifBusy, salary, numberOfWeekends,department);
        setNumberOfWorkingDaysYearly(numberOfWorkingDaysYearly);
        setSpecialization(specialization);
    }

    public void addTeamForSpecialist(Team team) {
        if(team == null){
            throw new IllegalArgumentException(" there is no team to be assigned to ");
        }
        if(participantOf.contains(team)){
            throw new IllegalArgumentException(" this developer is already a participant of this team");
        }
        this.participantOf.add(team);
    }

    public void removeTeamForSpecialist(Team team) {
        if(team == null){
            throw new IllegalArgumentException(" there is no team to quit from");
        }
        if(!participantOf.contains(team)){
            throw new IllegalArgumentException(" this developer is not a member of this team");
        }
        this.participantOf.remove(team);
        team.removeDeveloper(this);

    }

    public float getNumberOfWorkingDaysYearly() {
        return numberOfWorkingDaysYearly;
    }

    public void setNumberOfWorkingDaysYearly(float numberOfWorkingDaysYearly) {
        if(this.numberOfWorkingDaysYearly < 0 || numberOfWorkingDaysYearly > 365){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.numberOfWorkingDaysYearly = numberOfWorkingDaysYearly;
    }

    public String observeSpecialization() {
        return new String(specialization);
    }

    public void setSpecialization(String specialization) {
        if(specialization == null){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.specialization = specialization;
    }

    @Override
    public int getYearlySalary(){
        return this.getSalary()+3000;
    }

    @Override
    public void get_info() {
        toString();
    }

    public String getSpecialization() {
        return new String(specialization);
    }

    private List<Team> getParticipantOf() { return participantOf; }
    public List<Team> getCopyParticipantOf() {
        return new ArrayList<>(participantOf);
    }
    private void setParticipantOf(List<Team> participantOf) {
        this.participantOf = participantOf;
    }
}
