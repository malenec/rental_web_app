package rest;

import dtos.HouseDTO;
import dtos.UserDTO;
import entities.House;
import entities.Rental;
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

public class HouseResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    House h1, h2, h3;

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
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
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

        h1 = new House();
        h1.setAddress("Testvej 1");
        h1.setCity("Testby");
        h1.setNumberOfRooms(3L);

        h2 = new House();
        h2.setAddress("Testvej 2");
        h2.setCity("Testby");
        h2.setNumberOfRooms(3L);

        h3 = new House();
        h3.setAddress("Testvej 3");
        h3.setCity("Testby");
        h3.setNumberOfRooms(3L);


        try {

            em.getTransaction().begin();

            em.createNamedQuery("House.deleteAllRows").executeUpdate();

            em.persist(h1);
            em.persist(h2);
            em.persist(h3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    @Test
    public void getAllHouses() {
        given()
                .contentType("application/json")
                .get("/house/all/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", org.hamcrest.Matchers.is(3));
    }


    @Test
    public void createHouse() {

        HouseDTO houseDTO = new HouseDTO("Testvej 4", "Testby", 3L);

        given()
                .contentType("application/json")
                .body(houseDTO)
                .when()
                .post("/house/create")
                .then()
                .statusCode(200)
                .body("address", equalTo(houseDTO.getAddress()))
                .body("city", equalTo(houseDTO.getCity()))
                .body("numberOfRooms", equalTo(houseDTO.getNumberOfRooms().intValue()));
    }

}
