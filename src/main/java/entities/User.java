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
        @NamedQuery(name = "User.getAllUsers", query = "SELECT u FROM User u"),
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

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "phone", length = 15)
    private Long phone;

    @Column(name = "job", length = 255)
    private String job;

    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @JoinTable(name = "user_rental", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")})
    @ManyToMany
    private List<Rental> rentalList = new ArrayList<>();


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Rental> getRentalList() {
        return rentalList;
    }

    public void addRental(Rental rental) {
        rentalList.add(rental);
        rental.addUserToList(this);
    }

    public void removeRental(Rental rental) {
        rentalList.remove(rental);
        rental.removeUserFromList(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", job='" + job + '\'' +
                ", roleList=" + roleList +
                ", rentalList=" + rentalList +
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
