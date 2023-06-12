package dtos;

import entities.Trip;
import entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class TripDTO {

    private String tripName;
    private String date;
    private String time;
    private String location;
    private String duration;
    private String packingList;

    private String guide;

    private List<String> users;

    public TripDTO(String tripName, String date, String time, String location, String duration, String packingList, String guide) {
        this.tripName = tripName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.packingList = packingList;
        this.guide = guide;
    }

    public TripDTO(Trip trip) {
        this.tripName = trip.getTripName();
        this.date = trip.getDate();
        this.time = trip.getTime();
        this.location = trip.getLocation();
        this.duration = trip.getDuration();
        this.packingList = trip.getPackingList();
        this.guide = trip.getGuide().getGuideName();
        if(trip.getUserList() != null)
            this.users = trip.getUserList().stream().map(u -> u.getUserName()).collect(Collectors.toList());
    }

    public static List<TripDTO> getDtos(List<Trip> trips){
        return trips.stream().map(t -> new TripDTO(t)).collect(Collectors.toList());
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

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "tripName='" + tripName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", duration='" + duration + '\'' +
                ", packingList='" + packingList + '\'' +
                ", guide='" + guide + '\'' +
                ", users=" + users +
                '}';
    }
}
