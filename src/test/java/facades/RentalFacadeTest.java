package facades;

import dtos.*;
import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalFacadeTest {

    private static EntityManagerFactory emf;
    private static RentalFacade facade;

    User user1;
    House h1;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getRentalFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user", "test123");

        h1 = new House();
        h1.setAddress("Testvej 1");
        h1.setCity("Testby");
        h1.setNumberOfRooms(3L);


        try {

            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();

            em.persist(user1);
            em.persist(h1);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    @AfterAll
    public static void tearDownClass() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void createRental() {
        RentalDTO rentalDTO = new RentalDTO("2023-07-01", "2023-07-08", 1000.0F, 20.0F, "Oda", h1.getId());
        RentalDTO createdRental = facade.createRental(rentalDTO);
        assertEquals(rentalDTO.getStartDate(), createdRental.getStartDate());
        assertEquals(rentalDTO.getEndDate(), createdRental.getEndDate());
        assertEquals(rentalDTO.getPriceAnnual(), createdRental.getPriceAnnual());
        assertEquals(rentalDTO.getDeposit(), createdRental.getDeposit());
        assertEquals(rentalDTO.getContactPerson(), createdRental.getContactPerson());
        assertEquals(rentalDTO.getHouse(), createdRental.getHouse());

    }

    @Test
    public void assignUserToRental() {
        RentalDTO rentalDTO = new RentalDTO("2023-07-01", "2023-07-08", 1000.0F, 20.0F, "Oda", h1.getId());
        RentalDTO createdRental = facade.createRental(rentalDTO);

        UserDTO userDTOassignedToRental = facade.assignUserToRental(user1.getUserName(), createdRental.getId());

        assertEquals(user1.getUserName(), userDTOassignedToRental.getUsername());
        assertEquals(createdRental.getId(), userDTOassignedToRental.getRentalDTOListList().get(0).getId());
    }

    @Test
    public void removeUserFromRental() {
        RentalDTO rentalDTO = new RentalDTO("2023-07-01", "2023-07-08", 1000.0F, 20.0F, "Oda", h1.getId());
        RentalDTO createdRental = facade.createRental(rentalDTO);
        facade.assignUserToRental(user1.getUserName(), createdRental.getId());

        UserDTO userDTOremovedFromRental = facade.removeUserFromRental(user1.getUserName(), createdRental.getId());

        assertEquals(0, userDTOremovedFromRental.getRentalDTOListList().size());
    }
}