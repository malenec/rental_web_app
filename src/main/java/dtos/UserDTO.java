package dtos;

import entities.User;
import entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;
    private String address;
    private String phone;
    private String email;
    private int birthYear;
    private String gender;

    private List<TripDTO> tripList;

    public UserDTO(User u){
        this.username = u.getUserName();
        this.address = u.getAddress();
        this.phone = u.getPhone();
        this.email = u.getEmail();
        this.birthYear = u.getBirthYear();
        this.gender = u.getGender();
        if(u.getTripList() != null)
            this.tripList = u.getTripList().stream().map(t -> new TripDTO(t)).collect(Collectors.toList());
    }

    public static List<UserDTO> getDtos(List<User> users){
        return users.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<TripDTO> getTripList() {
        return tripList;
    }

    public void setTripList(List<TripDTO> tripList) {
        this.tripList = tripList;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + gender + '\'' +
                ", tripList=" + tripList +
                '}';
    }
}
