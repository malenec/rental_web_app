package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rental")

@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental")
@NamedQuery(name = "Rental.getAllRentals", query = "SELECT r FROM Rental r")

public class Rental{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long id;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "priceAnnual")
    private Float priceAnnual;

    @Column(name = "deposit")
    private Float deposit;

    @Column(name = "contactPerson")
    private String contactPerson;


    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToMany(mappedBy = "rentalList")
    private List<User> userList;

    public Rental() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(float priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return getId() != null && Objects.equals(getId(), rental.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}