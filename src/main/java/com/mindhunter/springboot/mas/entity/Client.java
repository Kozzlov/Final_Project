package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String clientContact;

    @NotNull
    private int individualDiscount;

    @NotNull
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Project> orders = new ArrayList<Project>();

    public Client(){}

    public Client(String clientContact,
                  int individualDiscount) {
        setClientContact(clientContact);
        setIndividualDiscount(individualDiscount);
    }

    public void addProject(Project project){
        if(project == null){
            throw new IllegalArgumentException(" There was no project provided ");
        }
        if(orders.contains(project)) {
            throw new IllegalArgumentException(" This project was already ordered by this client ");
        }
        orders.add(project);
    }

    public void removeProject(Project project){
        if(project == null){
            throw new IllegalArgumentException(" There was no project provided ");
        }
        if(!orders.contains(project)){
            throw new IllegalArgumentException(" This project does not belong to this client ");
        }
        orders.remove(project);
        project.unset_client(this);
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        if(clientContact == null){
            throw new IllegalArgumentException(" you can now set contact as null");
        }
        this.clientContact = clientContact;
    }

    public int getIndividualDiscount() {
        return individualDiscount;
    }

    public void setIndividualDiscount(int individualDiscount) {
        if(individualDiscount < 0){
            throw new IllegalArgumentException(" discount cannot be less than 0");
        }
        this.individualDiscount = individualDiscount;
    }

    private List<Project> getOrders() {
        return orders;
    }
    public List<Project> getCopyOrders() {
        return new ArrayList<>(orders);
    }

    private void setOrders(List<Project> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
