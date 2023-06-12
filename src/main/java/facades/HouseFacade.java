package facades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;

public class HouseFacade {

    private static HouseFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HouseFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HouseFacade getGuideFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HouseDTO createHouse(HouseDTO houseDTO) {

        House house = new House();
        house.setAddress(houseDTO.getAddress());
        house.setCity(houseDTO.getCity());
        house.setNumberOfRooms(houseDTO.getNumberOfRooms());

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(house);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new HouseDTO(house);
    }

    public List<HouseDTO> getAllHouses() {
        EntityManager em = emf.createEntityManager();
        try {
            List<House> houses = em.createNamedQuery("House.getAllHouses").getResultList();
            return HouseDTO.getDtos(houses);
        } finally {
            em.close();
        }
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        HouseFacade gf = getGuideFacade(emf);

//        HouseDTO guideDTO1 = new HouseDTO("Elva", "Female", 1990, "I am a guide", "www.google.com");
//        gf.createGuide(guideDTO1);

//        HouseDTO houseDTO2 = new HouseDTO("Kit", "Female", 1970, "I am also a guide", "www.facebook.com");
//        gf.createGuide(houseDTO2);

    }


}
