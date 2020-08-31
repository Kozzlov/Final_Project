package com.mindhunter.springboot.mas.entity;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private float budget;

    private LocalDateTime dueDate;

    @ManyToOne
    private Manager_Department_Head manager;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "inProject", cascade = CascadeType.ALL)
    private List <Task> tasks = new ArrayList<>();

    public Project(){
    }

    public Project(String title,
                   String description,
                   float budget,
                   LocalDateTime dueDate){
        setTitle(title);
        setDescription(description);
        setBudget(budget);
        setDueDate(dueDate);
    }

    public Manager_Department_Head getManager(){
        return manager;
    }

    public Client getClient() {
        return client;
    }

    public void set_Manager(Manager_Department_Head headOfProject){
        if(!headOfProject.getAccessLevel().contains(Access_Level.MANAGER)){
            throw new IllegalArgumentException(" no manager was provided ");
        }
        if(manager != null){
            throw new IllegalArgumentException(" this project is already supervised");
        }
        this.manager = headOfProject;
    }

    public void unset_manager(Manager_Department_Head managerToRemove){
        if(managerToRemove == null){
            throw new IllegalArgumentException(" no manager was provided ");
        }
        if(managerToRemove.getAccessLevel().contains(Access_Level.MANAGER)){
            this.manager = null;
            managerToRemove.removeProjectForManager(this);
        }
    }

    public void set_client(Client toAdd){
        if(toAdd == null){
            throw new IllegalArgumentException(" no client was provided ");
        }
        if(this.client != null){
            throw new IllegalArgumentException("This project belongs to another client");
        }
        this.client = toAdd;
    }

    public void unset_client(Client toRemove){
        if(toRemove == null){
            throw new IllegalArgumentException(" no client was provided ");
        }
        if(this.client != null){
            throw new IllegalArgumentException(" this project belongs to another client");
        }
        this.client = null;
        toRemove.removeProject(this);
    }

    //derived attribute
    public float get_total_price(){
        float total = 0;
            for(int i = 0; i < tasks.size(); i ++){
                if( tasks.size() == 0){
                    throw new IllegalArgumentException(" This projects has no tasks, price can not be shown");
                }
                total += i;
            }
            return total;
    }

    public void addTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Task should exist");
        if (task.get_project() != null) {
            if (tasks.contains(task)) System.out.println("task already assigned to this project");
            else throw new IllegalArgumentException("task already assigned to another project");
        }
        tasks.add(task);
    }
    public void removeTask(Task task) {
        if (task == null || !tasks.contains(task)) {
            throw new IllegalArgumentException("Task can not be null");
        }
        tasks.remove(task);
        task.unsetProject(this);
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        if(title == null){
            throw new IllegalArgumentException(" Title can not be an empty string ");
        }
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null){
            throw new IllegalArgumentException(" Description can not be an empty string ");
        }
        this.description = description;
    }

    public float getBudget() {
        return budget;
    }

    private void setBudget(float budget) {
        this.budget = budget;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        if(dueDate == null){
            throw new IllegalArgumentException(" Due date can not be an null ");
        }
        this.dueDate = dueDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<Task> getTasks() { return tasks; }
    public List<Task> getCopyTasks() {
        return new ArrayList<>(tasks);
    }
    private void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}


