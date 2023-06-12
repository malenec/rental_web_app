package facades;

import dtos.RentalDTO;
import dtos.UserDTO;
import entities.House;
import entities.Rental;
import entities.User;
import errorhandling.NotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class RentalFacade {

    private static RentalFacade instance;
    private static EntityManagerFactory emf;


    private RentalFacade() {}


    public static RentalFacade getRentalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RentalDTO createRental(RentalDTO rentalDTO) {
        EntityManager em = emf.createEntityManager();

        Rental rental = new Rental();
        rental.setStartDate(rentalDTO.getStartDate());
        rental.setEndDate(rentalDTO.getEndDate());
        rental.setPriceAnnual(rentalDTO.getPriceAnnual());
        rental.setDeposit(rentalDTO.getDeposit());
        rental.setContactPerson(rentalDTO.getContactPerson());

        House house = em.find(House.class, rentalDTO.getHouse());
        rental.setHouse(house);

        try {

            em.getTransaction().begin();
            em.persist(rental);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RentalDTO(rental);
    }


    public List<RentalDTO> getAllRentals() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Rental> rentals = em.createNamedQuery("Rental.getAllRentals").getResultList();
            return RentalDTO.getDtos(rentals);
        } finally {
            em.close();
        }
    }

    public UserDTO assignUserToRental(String userName, Long rentalId) {
        EntityManager em = emf.createEntityManager();
        Rental rental = em.find(Rental.class, rentalId);
        User user = em.find(User.class, userName);
        user.addRental(rental);
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO removeUserFromRental(String userName, Long rentalId) {
        EntityManager em = emf.createEntityManager();
        Rental rental = em.find(Rental.class, rentalId);
        User user = em.find(User.class, userName);
        user.removeRental(rental);
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public RentalDTO updateRental(RentalDTO rentalDTO) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Rental rental = em.find(Rental.class, rentalDTO.getId());
        if (rental == null) {
            throw new NotFoundException("Rental not found");
        }

        rental.setStartDate(rentalDTO.getStartDate());
        rental.setEndDate(rentalDTO.getEndDate());
        rental.setPriceAnnual(rentalDTO.getPriceAnnual());
        rental.setDeposit(rentalDTO.getDeposit());
        rental.setContactPerson(rentalDTO.getContactPerson());

        House house = em.find(House.class, rentalDTO.getHouse());
        rental.setHouse(house);

        try {
            em.getTransaction().begin();
            em.merge(rental);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new RentalDTO(rental);
    }

    public void deleteRental(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Rental rental = em.find(Rental.class, id);
            if (rental == null) {
                throw new NotFoundException("Rental not found");
            }


            List<User> users = em.createQuery("SELECT u FROM User u JOIN u.rentalList r WHERE r = :rental", User.class)
                    .setParameter("rental", rental)
                    .getResultList();


            for (User user : users) {
                user.removeRental(rental);
                em.merge(user);
            }


            em.remove(rental);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        RentalFacade rf = getRentalFacade(emf);

        rf.getAllRentals().forEach(dto->System.out.println(dto));

    }

}
