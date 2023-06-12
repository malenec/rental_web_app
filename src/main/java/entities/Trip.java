package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trip")

@NamedQuery(name = "Trip.deleteAllRows", query = "DELETE from Trip")
@NamedQuery(name = "Trip.getAllTrips", query = "SELECT t FROM Trip t")

public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "trip_name", length = 100, unique = true)
    private String tripName;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "location")
    private String location;

    @Column(name = "duration")
    private String duration;

    @Column(name = "packing_list")
    private String packingList;


    @ManyToOne
    @JoinColumn(name = "guide_name")
    private Guide guide;

    @ManyToMany(mappedBy = "tripList")
    private List<User> userList;

    public Trip() {
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void addUserToList(User user) {
        this.userList.add(user);
    }

    public void removeUserFromList(User user) {
        this.userList.remove(user);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", duration='" + duration + '\'' +
                ", packingList='" + packingList + '\'' +
                ", guide=" + guide +
                ", userList=" + userList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return getTripName() != null && Objects.equals(getTripName(), trip.getTripName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}