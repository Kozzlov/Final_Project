package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String teamName;

    //subset
    @ManyToMany(mappedBy = "participantOf")
    private List <Designer> designerParticipants = new ArrayList<>();

    @ManyToMany(mappedBy = "participantOf")
    private List <IT_Specialist> itSpecialistParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "scheduledFor", cascade = CascadeType.PERSIST)
    private List <Meeting> teamMeetingsSchedule = new ArrayList<>();

    @OneToMany(mappedBy = "worksOnTask", cascade = CascadeType.PERSIST)
    private Map<String, Task> taskQualif = new TreeMap<>(); //qualified

    @ManyToOne
    private Manager_Department_Head headOfTeam;

    public Team (){}

    public Team (String teamName,
                 List <Designer> designerParticipants,
                 List <IT_Specialist> itSpecialistParticipants,
                 List <Meeting> teamMeetingsSchedule,
                 Map <String, Task> taskQualif,
                 Manager_Department_Head headOfTeam){
        this.teamName = teamName;
        this.designerParticipants = designerParticipants;
        this.itSpecialistParticipants = itSpecialistParticipants;
        this.teamMeetingsSchedule = teamMeetingsSchedule;
        this.taskQualif = taskQualif;
        this.setHeadOfTeam(headOfTeam);
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        if(teamName == null || teamName.isEmpty()){
            throw new IllegalArgumentException(" data provided in incorrect format");
        }
        this.teamName = teamName;
    }

    public List<Designer> showDesignersParticipants(){
        if(designerParticipants == null || designerParticipants.isEmpty()){
            throw new IllegalArgumentException(" not designers in this team");
        }
        return new ArrayList<>(designerParticipants);
    }
    public List<IT_Specialist> showSpecialistsParticipants(){
        if(itSpecialistParticipants == null || itSpecialistParticipants.isEmpty()){
            throw new IllegalArgumentException(" not IT specialists in this team");
        }
        return new ArrayList<>(itSpecialistParticipants);
    }

    public void addDesigner(Designer designer) {
        if(designer == null){
            throw new IllegalArgumentException(" there is no designer to be added ");
        }
        if(designerParticipants.contains(designer)) {
            throw new IllegalArgumentException(" this designer is already a participant of this team ");
        }
        this.designerParticipants.add(designer);
    }

    public void removeDesigner(Designer designer) {
        if(designer == null){
            throw new IllegalArgumentException(" there is no designer to be removed ");
        }
        if(!designerParticipants.contains(designer)){
            throw new IllegalArgumentException(" this designer is already a participant of this team ");
        }
        this.designerParticipants.remove(designer);
        designer.removeTeam(this);
    }

    public void addDeveloper(IT_Specialist developer) {
        if(developer == null){
            throw new IllegalArgumentException(" there is no developer to be added ");
        }
        if(itSpecialistParticipants.contains(developer)){
            throw new IllegalArgumentException(" this developer is already a participant of this team ");
        }
        this.itSpecialistParticipants.add(developer);
    }

    public void removeDeveloper(IT_Specialist developer) {
        if(developer == null){
            throw new IllegalArgumentException(" there is no designer to be removed ");
        }
        if(!itSpecialistParticipants.contains(developer)){
            throw new IllegalArgumentException(" this designer is already a participant of this team ");
        }
        this.itSpecialistParticipants.remove(developer);
        developer.removeTeamForSpecialist(this);
    }

    public Manager_Department_Head getHeadOfTeam() {
        return headOfTeam;
    }

    public void setHeadOfTeam(Manager_Department_Head headOfTeam){
        if(!headOfTeam.getAccessLevel().contains(Access_Level.MANAGER)){
            throw new IllegalArgumentException(" no manager was provided ");
        }
        this.headOfTeam = headOfTeam;
    }

    public void unset_Manager(Manager_Department_Head headOfTeam){
        if(headOfTeam.getAccessLevel().contains(Access_Level.MANAGER)){
            throw new IllegalArgumentException(" no manager was provided ");
        }
        if(headOfTeam == null){
            throw new IllegalArgumentException(" this team has no supervisor to be removed ");
        }
        this.headOfTeam = null;
        headOfTeam.removeTeamForManager(this);
    }

    public void addMeeting(Meeting meeting){
        if(meeting == null){
            throw new IllegalArgumentException(" there is no meeting to be scheduled ");
        }
        if(teamMeetingsSchedule.contains(meeting) || meeting.getScheduledFor() != null){
            throw new IllegalArgumentException(" this period is already reserved by another team ");
        }
        this.teamMeetingsSchedule.add(meeting);
        meeting.setTeam(this);
    }

    public void removeMeeting(Meeting meeting){
        if(meeting == null){
            throw new IllegalArgumentException(" there is no meeting to be canceled ");
        }
        if(!teamMeetingsSchedule.contains(meeting)){
            throw new IllegalArgumentException(" this period is available anyway ");
        }
        this.teamMeetingsSchedule.remove(meeting);
        meeting.unsetTeam(this);
    }

    public void addTask(Task task){
        if(task == null){
            throw new IllegalArgumentException(" there is no task to be added");
        }
        if(task.get_team() != null && !taskQualif.containsValue(task)) {
            throw new IllegalArgumentException(" another team works on this task ");
        }
        this.taskQualif.put(task.getTaskDescription(), task);
    }

    public void removeTask(Task task){
        if(task == null){
            throw new IllegalArgumentException(" there is no task to be removed");
        }
        if(!taskQualif.containsValue(task)){
            throw new IllegalArgumentException(" this team does not work on this task ");
        }
        this.taskQualif.remove(task.getTaskDescription(),  task);
        task.unsetTeam(this);
    }

    /*
     designerParticipants
    itSpecialistParticipants
    teamMeetingsSchedule
    taskQualif
     */

    private List<Designer> getDesigners() { return designerParticipants; }
    public List<Designer> getCopyDesigners() { return new ArrayList<>(designerParticipants); }
    private void setDesigners(List<Designer> designers) {
        this.designerParticipants = designers;
    }

    private List<IT_Specialist> getItSpecialists() { return itSpecialistParticipants; }
    public List<IT_Specialist> getCopyItSpecialists() { return new ArrayList<>(itSpecialistParticipants); }
    private void getItSpecialists(List<IT_Specialist> specialists) {
        this.itSpecialistParticipants = specialists;
    }

    private List<Meeting> getMeetings() { return teamMeetingsSchedule; }
    public List<Meeting> getCopyMeetings() { return new ArrayList<>(teamMeetingsSchedule); }
    private void setMeetings(List<Meeting> meetings) {
        this.teamMeetingsSchedule = meetings;
    }

    private Map<String, Task> getTasks() { return taskQualif; }
    public Map<String, Task> getCopyTasks() { return new TreeMap<>(taskQualif); }
    private void setTasks(Map<String, Task> tasks) {
        this.taskQualif = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
