package dtos;

import entities.Rental;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RentalDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float priceAnnual;
    private Float deposit;
    private String contactPerson;
    private Long house;
    private List<String> users;

    public RentalDTO(LocalDate startDate, LocalDate endDate, Float priceAnnual, Float deposit, String contactPerson, Long house) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
        this.house = house;
    }

    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.priceAnnual = rental.getPriceAnnual();
        this.deposit = rental.getDeposit();
        this.contactPerson = rental.getContactPerson();
        this.house = rental.getHouse().getId();
        if (rental.getUserList() != null)
            this.users = rental.getUserList().stream().map(u -> u.getUserName()).collect(Collectors.toList());
    }

    public static List<RentalDTO> getDtos(List<Rental> rentals) {
        return rentals.stream().map(t -> new RentalDTO(t)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getPriceAnnual() {
        return priceAnnual;
    }

    public void setPriceAnnual(Float priceAnnual) {
        this.priceAnnual = priceAnnual;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Long getHouse() {
        return house;
    }

    public void setHouse(Long house) {
        this.house = house;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceAnnual=" + priceAnnual +
                ", deposit=" + deposit +
                ", contactPerson='" + contactPerson + '\'' +
                ", house='" + house + '\'' +
                ", users=" + users +
                '}';
    }
}
