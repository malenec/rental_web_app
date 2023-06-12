package facades;

import dtos.GuideDTO;
import dtos.TripDTO;
import entities.Guide;
import entities.Trip;
import entities.User;
import errorhandling.NotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;

public class TripFacade {

    private static TripFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private TripFacade() {}

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static TripFacade getTripFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TripDTO createTrip(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();

        try {
            Trip trip = new Trip();
            trip.setTripName(tripDTO.getTripName());
            trip.setDate(tripDTO.getDate());
            trip.setTime(tripDTO.getTime());
            trip.setLocation(tripDTO.getLocation());
            trip.setDuration(tripDTO.getDuration());
            trip.setPackingList(tripDTO.getPackingList());

            Guide guide = em.find(Guide.class, tripDTO.getGuide());
            trip.setGuide(guide);

            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();

            return new TripDTO(trip);
        } catch (PersistenceException e) {

            throw new IllegalArgumentException("A trip with the same name already exists.");
        } finally {
            em.close();
        }
    }


    public List<TripDTO> getAllTrips() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Trip> trips = em.createNamedQuery("Trip.getAllTrips").getResultList();
            return TripDTO.getDtos(trips);
        } finally {
            em.close();
        }
    }

    public void assignUserToTrip(String userName, TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripDTO.getTripName());
            User user = em.find(User.class, userName);
            user.addTrip(trip);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeUserFromTrip(String userName, TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripDTO.getTripName());
            User user = em.find(User.class, userName);
            user.removeTrip(trip);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public TripDTO updateTrip(TripDTO tripDTO) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        Trip trip = em.find(Trip.class, tripDTO.getTripName());
        if (trip == null) {
            throw new NotFoundException("Trip not found");
        }

        trip.setTripName(tripDTO.getTripName());
        trip.setDate(tripDTO.getDate());
        trip.setTime(tripDTO.getTime());
        trip.setLocation(tripDTO.getLocation());
        trip.setDuration(tripDTO.getDuration());
        trip.setPackingList(tripDTO.getPackingList());

        Guide guide = em.find(Guide.class, tripDTO.getGuide());
        trip.setGuide(guide);

        try {
            em.getTransaction().begin();
            em.merge(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new TripDTO(trip);
    }

    public void deleteTrip(String tripName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, tripName);
            if (trip == null) {
                throw new NotFoundException("Trip not found");
            }


            List<User> users = em.createQuery("SELECT u FROM User u JOIN u.tripList t WHERE t = :trip", User.class)
                    .setParameter("trip", trip)
                    .getResultList();


            for (User user : users) {
                user.removeTrip(trip);
                em.merge(user);
            }


            em.remove(trip);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        TripFacade gf = getTripFacade(emf);

        gf.getAllTrips().forEach(dto->System.out.println(dto));

//        TripDTO tripDTO1 = new TripDTO("Bjergbestigning", "2023-06-24", "12:00", "Machu Picchu", "2 timer", "Vandrestøvler, Sovepose, Lommelygte", "Maria");
//        gf.createTrip(tripDTO1);

//        TripDTO tripDTO2 = new TripDTO("Historisk vandring", "2023-06-27", "14:00", "Pisa", "4 timer", "Solcreme, Solhat, Vand", "Kit");
//        gf.createTrip(tripDTO2);

    }

}
