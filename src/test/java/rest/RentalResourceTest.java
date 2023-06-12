package rest;

import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RentalResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    User user1;
    House h1;

    Rental r1, r2, r3;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {

        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

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

        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user", "test123");

        h1 = new House();
        h1.setAddress("Testvej 1");
        h1.setCity("Testby");
        h1.setNumberOfRooms(3L);

        r1 = new Rental();
        r1.setStartDate("2023-07-01");
        r1.setEndDate("2023-07-14");
        r1.setPriceAnnual(1000L);
        r1.setDeposit(1000L);
        r1.setContactPerson("Test Testesen");
        r1.setHouse(h1);

        r2 = new Rental();
        r2.setStartDate("2023-07-14");
        r2.setEndDate("2023-07-21");
        r2.setPriceAnnual(1000L);
        r2.setDeposit(1000L);
        r2.setContactPerson("Test Testesen");
        r2.setHouse(h1);

        r3 = new Rental();
        r3.setStartDate("2023-07-21");
        r3.setEndDate("2023-07-28");
        r3.setPriceAnnual(1000L);
        r3.setDeposit(1000L);
        r3.setContactPerson("Test Testesen");
        r3.setHouse(h1);


        try {

            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();

            em.persist(user1);
            em.persist(h1);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    @Test
    public void getAllRentals() {
        given()
                .contentType("application/json")
                .get("/rental/all/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", org.hamcrest.Matchers.is(3));
    }


    @Test
    public void createRental() {
        RentalDTO rentalDTO = new RentalDTO("2023-07-01", "2023-07-08", 1000.0F, 20.0F, "Oda", h1.getId());

        given()
                .contentType("application/json")
                .body(rentalDTO)
                .when()
                .post("/rental/create")
                .then()
                .statusCode(200)
                .body("startDate", equalTo(rentalDTO.getStartDate()))
                .body("endDate", equalTo(rentalDTO.getEndDate()))
                .body("priceAnnual", equalTo(rentalDTO.getPriceAnnual()))
                .body("deposit", equalTo(rentalDTO.getDeposit()))
                .body("contactPerson", equalTo(rentalDTO.getContactPerson()))
                .body("house", equalTo(rentalDTO.getHouse().intValue()));
    }


    @Test
    public void assignUserToRental() {
        given()
                .contentType("application/json")
                .post("/rental/assign/user/" + r1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("username", equalTo("user"))
                .body("rentalDTOListList[0].id", equalTo(r1.getId().intValue()))
                .body("rentalDTOListList[0].users[0]", equalTo("user"));
    }

}
