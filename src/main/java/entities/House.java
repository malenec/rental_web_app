package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "house")

@NamedQuery(name = "House.deleteAllRows", query = "DELETE from House")
@NamedQuery(name = "House.getAllHouses", query = "SELECT h FROM House h")

public class House{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "numberOfRooms", nullable = false)
    private Long numberOfRooms;

    @OneToMany(mappedBy = "house", orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    public House() {
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

    public List<Rental> getRentals() {
        return rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setHouse(this);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
        rental.setHouse(null);
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentals=" + rentals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return getId() != null && Objects.equals(getId(), house.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}


