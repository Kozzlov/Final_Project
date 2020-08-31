package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Date meetingDay;

    @NotNull
    private Time meetingTime;

    @NotNull
    private String meetingRoom;

    @ManyToOne
    private Team scheduledFor;

    public Meeting(){}

    public Meeting(Date meetingDay,
                   Time meetingTime,
                   String meetingRoom) {
        setMeetingDay(meetingDay);
        setMeetingTime(meetingTime);
        setMeetingRoom(meetingRoom);
    }

    public void setTeam(Team team){
        if(team == null){
            throw new IllegalArgumentException(" there was no team provided ");
        }
        if(scheduledFor != null){
            throw new IllegalArgumentException(" this meeting is already scheduled for another team ");
        }
        this.scheduledFor = team;
        team.addMeeting(this);
    }

    public void unsetTeam(Team team){
        if(team == null){
            throw new IllegalArgumentException(" there was no team provided ");
        }
        if(scheduledFor == null){
            throw new IllegalArgumentException(" this meeting is not scheduled for this team ");
        }
        this.scheduledFor = null;
        team.removeMeeting(this);
    }

    public Date getMeetingDay() {
        if(meetingDay == null){
            throw new IllegalArgumentException("No meeting date  provided");
        }
        return meetingDay;
    }

    public void setMeetingDay(Date meetingDay) {
        if(meetingDay == null){
            throw new IllegalArgumentException("meeting date can not be set as null");
        }
        this.meetingDay = meetingDay;
    }

    public Time getMeetingTime() {
        if(meetingTime == null){
            throw new IllegalArgumentException("No time provided");
        }
        return meetingTime;
    }

    public void setMeetingTime(Time meetingTime) {
        if(meetingTime == null){
            throw new IllegalArgumentException("meeting time can not be set as null");
        }
        this.meetingTime = meetingTime;
    }

    public String getMeetingRoom() {
        if(meetingRoom == null){
            throw new IllegalArgumentException("No room provided");
        }
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        if(meetingRoom == null){
            throw new IllegalArgumentException("meeting room can not be set as null");
        }
        this.meetingRoom = meetingRoom;
    }

    public Team getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(Team scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    private Team getTeam() { return scheduledFor; }
}