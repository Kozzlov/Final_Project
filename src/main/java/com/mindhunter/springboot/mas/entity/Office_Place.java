package com.mindhunter.springboot.mas.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Office_Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tableIndex;

    @NotNull
    private String officePart;

    @OneToOne
    private Worker placeOwner;

    @ManyToOne
    private Department basedIn;


    public Office_Place(){}

    public Office_Place(String tableIndex,
                        String officePart){
        setTableIndex(tableIndex);
        setOfficePart(officePart);
    }

    public static Office_Place createOfficePlace(
            Department basedIn,
            String tableIndex,
            String officePart)
    {
        if(basedIn == null){
            throw new IllegalArgumentException(" There is no such department");
        }
        Office_Place newOfficePlace = new Office_Place(tableIndex, officePart);
        basedIn.addOfficePlace(newOfficePlace);
        return newOfficePlace;
    }

    public Worker getPlaceOwner() {
        if(placeOwner == null){
            throw new IllegalArgumentException("no worker was provided");
        }
        return placeOwner;
    }

    public Department getDepartment(){
        if(basedIn == null){
            throw new IllegalArgumentException("no department was provided");
        }
        return basedIn;
    }

    public void setDepartment(Department department){
        if(department == null){
            throw new IllegalArgumentException(" there was no team provided ");
        }
        if(basedIn != null){
            throw new IllegalArgumentException(" this office place in not in this department ");
        }
        this.basedIn = department;
        department.addOfficePlace(this);
    }

    public void unsetDepartment(Department department){
        if(department == null){
            throw new IllegalArgumentException(" there was no department provided ");
        }
        if(basedIn == null){
            throw new IllegalArgumentException(" this office place in not in this department ");
        }
        this.basedIn = null;
        department.removeOfficePlace(this);
    }

    public void setWorker(Worker worker){
        if(worker == null){
            throw new IllegalArgumentException(" no place was provided ");
        }
        this.placeOwner = worker;
        worker.setOfficePlace(this);
    }

    public void unsetWorker(Worker worker){
        if(worker == null){
            throw new IllegalArgumentException(" no place was provided ");
        }
        if(this.placeOwner != worker){
            throw new IllegalArgumentException(" This place is not assigned to this worker ");
        }
        this.placeOwner = null;
        worker.unsetOfficePlace(this);
    }

    public String getTableIndex() {
        if(tableIndex == null){
            throw new IllegalArgumentException(" there was no table index provided ");
        }
        return tableIndex;
    }

    private void setTableIndex(String tableIndex) {
        if(tableIndex == null){
            throw new IllegalArgumentException(" Table index can not be an empty string ");
        }
        this.tableIndex = tableIndex;
    }

    public String getOfficePart() {
        if(officePart == null){
            throw new IllegalArgumentException(" there was no office part provided ");
        }
        return officePart;
    }

    private void setOfficePart(String officePart) {
        if(officePart == null){
            throw new IllegalArgumentException(" Office part can not be an empty string ");
        }
        this.officePart = officePart;
    }
}
