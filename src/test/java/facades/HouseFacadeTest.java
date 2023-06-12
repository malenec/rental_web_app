package facades;

import dtos.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseFacadeTest {

    private static EntityManagerFactory emf;
    private static HouseFacade facade;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HouseFacade.getHouseFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        try {

            em.getTransaction().begin();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
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
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void createHouse() {

        HouseDTO houseDTO = new HouseDTO("Marievej 27", "Møn", 3L);

        HouseDTO createdHouse = facade.createHouse(houseDTO);

        assertEquals(houseDTO.getAddress(), createdHouse.getAddress());
        assertEquals(houseDTO.getCity(), createdHouse.getCity());
        assertEquals(houseDTO.getNumberOfRooms(), createdHouse.getNumberOfRooms());
    }

}