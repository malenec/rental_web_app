package dtos;

import entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;

    private String name;
    private Long phone;
    private String job;

    private List<RentalDTO> rentalDTOListList;

    public UserDTO(User u){
        this.username = u.getUserName();
        this.name = u.getName();
        this.phone = u.getPhone();
        this.job = u.getJob();
        if(u.getRentalList() != null)
            this.rentalDTOListList = u.getRentalList().stream().map(t -> new RentalDTO(t)).collect(Collectors.toList());
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

    public List<RentalDTO> getRentalDTOListList() {
        return rentalDTOListList;
    }

    public void setRentalDTOListList(List<RentalDTO> rentalDTOListList) {
        this.rentalDTOListList = rentalDTOListList;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", job='" + job + '\'' +
                ", rentalDTOListList=" + rentalDTOListList +
                '}';
    }
}
