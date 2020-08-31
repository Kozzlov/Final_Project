package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String taskDescription;

    @NotNull
    private float productionPrice;

    @ManyToOne
    private Project inProject;

    @ManyToOne
    private Team worksOnTask;

    public Task (){}

    private Task (String taskDescription,
                 float productionPrice){
        setTaskDescription(taskDescription);
        setProductionPrice(productionPrice);
    }

    public static Task createTask(
            Project myProject,
            Team myTeam,
            String taskDescription,
            float productionPrice)
    {
        if (myProject == null) throw new IllegalArgumentException("Project cannot be undefined");
        if (myTeam == null) throw new IllegalArgumentException("Team cannot be undefined");
        Task task = new Task(taskDescription, productionPrice);
        myProject.addTask(task);
        myTeam.addTask(task);
        return task;
    }

    public float getProductionPrice() {
        return productionPrice;
    }

    public void setProject(Project project){
        if(project == null){
            throw new IllegalArgumentException(" there is no project provided ");
        }
        if(inProject != null) {
            throw new IllegalArgumentException(" this task is already a part of another project ");
        }
        this.inProject = project;
    }
    public void unsetProject(Project project){
        if(project == null){
            throw new IllegalArgumentException(" there was no project provided ");
        }
        if(inProject == null){
            throw new IllegalArgumentException(" this task is not a part of this project ");
        }
        this.inProject = null;
        project.removeTask(this);
    }

    public void setTeam(Team team){
        if(team == null){
            throw new IllegalArgumentException(" there is no team provided ");
        }
        if(worksOnTask != null){
            throw new IllegalArgumentException(" another team works on this task ");
        }
        this.worksOnTask = team;
    }

    public void unsetTeam(Team team){
        if(team == null){
            throw new IllegalArgumentException(" there is no team provided ");
        }
        if(worksOnTask == null){
            throw new IllegalArgumentException(" no team works on this task ");
        }
        this.worksOnTask = null;
        team.removeTask(this);
    }

    public Team get_team(){
        if(worksOnTask == null){
            throw new IllegalArgumentException(" there is no team provided ");
        }
        return this.worksOnTask;
    }
    public Project get_project(){
        return this.inProject;
    }


    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setProductionPrice(float productionPrice) {
        this.productionPrice = productionPrice;
    }

    public void setInProject(Project inProject) {
        this.inProject = inProject;
    }

    public void setWorksOnTask(Team worksOnTask) {
        this.worksOnTask = worksOnTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
