package facades;

import dtos.GuideDTO;
import dtos.TripDTO;
import entities.Guide;
import entities.Trip;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

public class GuideFacade {

    private static GuideFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private GuideFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static GuideFacade getGuideFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public GuideDTO createGuide(GuideDTO guideDTO) {

        Guide guide = new Guide();
        guide.setGuideName(guideDTO.getGuideName());
        guide.setGender(guideDTO.getGender());
        guide.setBirthYear(guideDTO.getBirthYear());
        guide.setProfile(guideDTO.getProfile());
        guide.setImageUrl(guideDTO.getImageUrl());

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(guide);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            // Handle the exception caused by duplicate trip name
            throw new IllegalArgumentException("A guide with the same name already exists.");
        } finally {
            em.close();
        }

        return new GuideDTO(guide);
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        GuideFacade gf = getGuideFacade(emf);

//        GuideDTO guideDTO1 = new GuideDTO("Elva", "Female", 1990, "I am a guide", "www.google.com");
//        gf.createGuide(guideDTO1);

        GuideDTO guideDTO2 = new GuideDTO("Kit", "Female", 1970, "I am also a guide", "www.facebook.com");
        gf.createGuide(guideDTO2);

    }


}
