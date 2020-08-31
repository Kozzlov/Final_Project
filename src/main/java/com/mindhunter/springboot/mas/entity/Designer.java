package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Designer extends Worker{

    @NotNull
    private float hourlyRate;

    //subset
    @ManyToMany
    private List<Team> participantOf = new ArrayList<>();

    public Designer(){}


    public Designer(
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
        super(firstName, lastName, email, workingDays, mainContact, ifBusy, salary, numberOfWeekends, department);
        setHourlyRate(hourlyRate);
    }

    public float getHourly_rate() {
        return hourlyRate;
    }

    public void addTeam(Team team) {
        if(team == null){
            throw new IllegalArgumentException(" there is no team to be assigned to ");
        }
        if(participantOf.contains(team)){
            throw new IllegalArgumentException(" this designer is already a participant of this team");
        }
        this.participantOf.add(team);
    }

    public void removeTeam(Team team) {
        if(team == null){
            throw new IllegalArgumentException(" there is no team to quit from");
        }
        if(!participantOf.contains(team)){
            throw new IllegalArgumentException(" this designer is not a member of this team");
        }
        this.participantOf.remove(team);
        team.removeDesigner(this);
    }

    public void change_hourly_rate(float hourlyRate) {
        if(hourlyRate < 0 || hourlyRate > 2000){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public int getYearlySalary(){
        return this.getSalary()+1000;
    }

    @Override
    public void get_info() {
        toString();
    }

    public float getHourlyRate() {
        if(hourlyRate == 0){
            throw new IllegalArgumentException(" hourly rate is empty ");
        }
        return hourlyRate;
    }

    private void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    private List<Team> getParticipantOf() { return participantOf; }
    public List<Team> getCopyParticipantOf() {
        return new ArrayList<>(participantOf);
    }
    private void setParticipantOf(List<Team> participantOf) {
        this.participantOf = participantOf;
    }
}
