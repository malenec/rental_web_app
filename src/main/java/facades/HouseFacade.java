package facades;

import dtos.HouseDTO;
import entities.House;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class HouseFacade {

    private static HouseFacade instance;
    private static EntityManagerFactory emf;

    private HouseFacade() {
    }

    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
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
        HouseFacade hf = getHouseFacade(emf);

        hf.getAllHouses().forEach(dto->System.out.println(dto));

    }

}
