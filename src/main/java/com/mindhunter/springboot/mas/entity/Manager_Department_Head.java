package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.EnumSet;


@Entity
public class Manager_Department_Head extends Worker {

    // separate classes (composition)
    @NotNull
    private float maxTeamSupervision; // for department head
    @NotNull
    private int workShiftDuration; // for manager

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Access_Level> accessLevel = new ArrayList<>(); //for overlapping, manager by default

    @OneToMany(mappedBy = "manager", cascade = CascadeType.PERSIST)
    private List<Project> responsibleForProjects = new ArrayList<Project>();

    @OneToMany(mappedBy = "headOfTeam", cascade = CascadeType.PERSIST)
    private List<Team> responsibleForTeams = new ArrayList<Team>();

    public Manager_Department_Head() {
    }

    public Manager_Department_Head(String firstName, String lastName,
                                   String email, List<WorkingDays> workingDays,
                                   String mainContact, boolean ifBusy,
                                   int salary,
                                   int numberOfWeekends,
                                   Department department) {
        super(firstName, lastName, email, workingDays, mainContact, ifBusy, salary, numberOfWeekends, department);
        this.accessLevel.add(Access_Level.MANAGER);
    }

    public Manager_Department_Head(
            String firstName,
            String lastName,
            String email,
            List<WorkingDays> workingDays,
            String mainContact,
            boolean ifBusy,
            int salary,
            int numberOfWeekends,
            int workShiftDuration,
            Access_Level accessLevel,
            Department department
    ) {
        super(firstName, lastName, email, workingDays, mainContact, ifBusy, salary, numberOfWeekends, department);
        setWorkShiftDuration(workShiftDuration);
        this.accessLevel.add(Access_Level.MANAGER);
        this.accessLevel.add(accessLevel);
    }

    public float getSalaryBonus() {
        if (!this.accessLevel.contains(Access_Level.MANAGER)) {
            throw new IllegalArgumentException(" this worker is now a manager, you can not get maximal team supervision number");
        }
        return maxTeamSupervision;

    }

    public void setSalaryBonus(float newMaximal) {
        if (!this.accessLevel.contains(Access_Level.MANAGER)) {
            throw new IllegalArgumentException(" worker is now a manager, you can not set maximal team supervision number");
        }
        if (newMaximal < 1 || newMaximal > 5) {
            throw new IllegalArgumentException(" manager's team supervision number can not be negative or bigger than 5");
        }
        this.maxTeamSupervision = newMaximal;
    }

    public int getWorkShiftDuration() {
        return workShiftDuration;
    }

    public void setWorkShiftDuration(int workShiftDuration) {
        if (workShiftDuration < 2 || workShiftDuration > 10) {
            throw new IllegalArgumentException(" work shift value can only be between 2 and 10 hours");
        }
        this.workShiftDuration = workShiftDuration;
    }

    public List<Access_Level> getAccessLevel() {
        if (accessLevel.isEmpty()) {
            throw new IllegalArgumentException("There is no data");
        }
        return accessLevel;
    }

    public void addAccessLevel(Access_Level toAdd) {
        if (toAdd == null || this.accessLevel.contains(toAdd)) {
            throw new IllegalArgumentException("this manager already is " + toAdd);
        }
        if (!this.accessLevel.contains(toAdd) && toAdd == Access_Level.DEPARTMENT_HEAD) {
            this.accessLevel.add(toAdd);
        }
        if (!this.accessLevel.contains(toAdd) && toAdd == Access_Level.MANAGER) {
            this.accessLevel.add(toAdd);
        }
    }

    public void removeAccessLevel(Access_Level toRemove) {
        if (toRemove == null || !this.accessLevel.contains(toRemove)) {
            throw new IllegalArgumentException("this manager already is " + toRemove);
        }
        if (this.accessLevel.contains(toRemove) && toRemove == Access_Level.DEPARTMENT_HEAD) {
            if (accessLevel.size() != 1) {
                this.accessLevel.remove(toRemove);
                this.workShiftDuration = 0;
            }
        }
        if (this.accessLevel.contains(toRemove) && toRemove == Access_Level.MANAGER) {
            if (accessLevel.size() != 1) {
                this.accessLevel.remove(toRemove);
                this.maxTeamSupervision = 0;
            }
        }
    }

    public void addTeamForManager(Team team) {
        if (team == null) {
            throw new IllegalArgumentException(" there is no team to be assigned to ");
        }
        if (team.getHeadOfTeam() != null && team.getHeadOfTeam() != this) {
            throw new IllegalArgumentException("Occupied by another manager");
        }
        if (responsibleForTeams.size() > maxTeamSupervision) {
            throw new IllegalArgumentException(" this manager cannot supervise more teams");
        }
        this.responsibleForTeams.add(team);
    }

    public void removeTeamForManager(Team team) {
        if (team == null) {
            throw new IllegalArgumentException(" there is no team to be removed from ");
        }
        if (!responsibleForTeams.contains(team)) {
            throw new IllegalArgumentException(" this manager is not a supervisor of - " + team);
        }
        this.responsibleForTeams.remove(team);
        team.unset_Manager(this);
    }

    public void addProjectForManager(Project project) {
        if (project == null) {
            throw new IllegalArgumentException(" there is no project to be assigned to ");
        }
        if (project.getManager() != null && !responsibleForProjects.contains(project)) {
            throw new IllegalArgumentException("Project is already supervised");
        }
        this.responsibleForProjects.add(project);
    }

    public void removeProjectForManager(Project project) {
        if (project == null) {
            throw new IllegalArgumentException(" there is no project to be removed");
        }
        if (!responsibleForProjects.contains(project)) {
            throw new IllegalArgumentException(" this manager does not supervise this project");
        }
        this.responsibleForProjects.remove(project);
        project.unset_manager(this);
    }



    @Override
    public int getYearlySalary(){
        return this.getSalary() + 2000;
    }

    @Override
    public void get_info() {
        toString();
    }
}
