package dtos;

import entities.House;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

public class HouseDTO {

    private Long id;
    private String address;
    private String city;
    private Long numberOfRooms;

    private List<Long> rentals;

    public HouseDTO(String address, String city, Long numberOfRooms) {
        this.address = address;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
    }

    public HouseDTO(House house){
        this.id = house.getId();
        this.address = house.getAddress();
        this.city = house.getCity();
        this.numberOfRooms = house.getNumberOfRooms();
        if(house.getRentals() != null)
            this.rentals = house.getRentals().stream().map(r -> r.getId()).collect(Collectors.toList());
    }

    public static List<HouseDTO> getDtos(List<House> houses){
        return houses.stream().map(g -> new HouseDTO(g)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Long> getRentals() {
        return rentals;
    }

    public void setRentals(List<Long> rentals) {
        this.rentals = rentals;
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentals=" + rentals +
                '}';
    }
}
