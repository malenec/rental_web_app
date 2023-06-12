package utils;


import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

//    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
//    // CHANGE the three passwords below, before you uncomment and execute the code below
//    // Also, either delete this file, when users are created or rename and add to .gitignore
//    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

//        STEP 1: CREATES USERS *REMEMBER DROP & CREATE IF YOU'VE MADE CHANGES TO THE USER CLASS IN ENTITIES*
//    User user = new User("user", "test123");
//    User admin = new User("admin", "test123");
//    User both = new User("user_admin", "test123");
//
//    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
//      throw new UnsupportedOperationException("You have not changed the passwords");
//
//    em.getTransaction().begin();
//    Role userRole = new Role("user");
//    Role adminRole = new Role("admin");
//    user.addRole(userRole);
//    admin.addRole(adminRole);
//    both.addRole(userRole);
//    both.addRole(adminRole);
//    em.persist(userRole);
//    em.persist(adminRole);
//    em.persist(user);
//    em.persist(admin);
//    em.persist(both);
//    em.getTransaction().commit();
//    System.out.println("PW: " + user.getUserPass());
//    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
//    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
//    System.out.println("Created TEST Users");
//    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));
//
//    User sofie = new User("sofie", "test123");
//    User caroline = new User("caroline", "test123");
//    User marius = new User("marius", "test123");
//    User hugo = new User("hugo", "test123");
//
//    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
//      throw new UnsupportedOperationException("You have not changed the passwords");
//
//    em.getTransaction().begin();
//
//    Role userRole = em.find(Role.class, "user");

//    sofie.addRole(userRole);
//    caroline.addRole(userRole);
//    marius.addRole(userRole);
//    hugo.addRole(userRole);
//
//    em.persist(sofie);
//    em.persist(caroline);
//    em.persist(marius);
//    em.persist(hugo);
//
//    em.getTransaction().commit();
//    System.out.println("PW: " + sofie.getUserPass());
//    System.out.println("Testing user with OK password: " + sofie.verifyPassword("test"));
//    System.out.println("Testing user with wrong password: " + sofie.verifyPassword("test1"));
//    System.out.println("Created TEST Users");
//    System.out.println("Testing user with OK password: " + sofie.verifyPassword("test123"));
//
//        em.getTransaction().begin();
//
//        User sofie = em.find(User.class, "sofie");
//        User caroline = em.find(User.class, "caroline");
//        User marius = em.find(User.class, "marius");
//        User hugo = em.find(User.class, "hugo");
//
//        sofie.setName("Sofie Marie Magnussen");
//        sofie.setPhone(12345678L);
//        sofie.setJob("Lærer");
//
//        caroline.setName("Caroline Markus");
//        caroline.setPhone(87654321L);
//        caroline.setJob("Studerende");
//
//        marius.setName("Marius Hansen");
//        marius.setPhone(12345678L);
//        marius.setJob("Designer");
//
//        hugo.setName("Hugo Andrew");
//        hugo.setPhone(87654321L);
//        hugo.setJob("Musiker");
//
//
//        em.merge(sofie);
//        em.merge(caroline);
//        em.merge(marius);
//        em.merge(hugo);
//
//        em.getTransaction().commit();

    }
}
