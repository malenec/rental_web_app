package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "user")

@NamedQueries({
        @NamedQuery(name = "User.deleteAllRows", query = "DELETE from User"),
//        @NamedQuery(name = "User.getAllGroceryListsByUsername", query = "SELECT g FROM GroceryList g WHERE g.user.userName = :username"),
        @NamedQuery(name = "User.getUserByUsername", query = "select u from User u WHERE u.userName = :username")
})

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "birth_year", length = 4)
    private int birthYear;

    @Column(name = "gender", length = 10)
    private String gender;

    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @JoinTable(name = "user_trip", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "trip_name", referencedColumnName = "trip_name")})
    @ManyToMany
    private List<Trip> tripList = new ArrayList<>();


    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User() {
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, userPass);
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        ;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Trip> getTripList() {
        return tripList;
    }

    public void addTrip(Trip trip) {
        tripList.add(trip);
        trip.addUserToList(this);
    }

    public void removeTrip(Trip trip) {
        tripList.remove(trip);
        trip.removeUserFromList(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + gender + '\'' +
                ", roleList=" + roleList +
                ", tripList=" + tripList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserName() != null && Objects.equals(getUserName(), user.getUserName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
