//package rest;
//
//import io.restassured.RestAssured;
//import io.restassured.parsing.Parser;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.http.util.HttpStatus;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.core.UriBuilder;
//import java.net.URI;
//
//import static io.restassured.RestAssured.given;
//
//public class GroceryResourceTest {
//
//    private static final int SERVER_PORT = 7777;
//    private static final String SERVER_URL = "http://localhost/api";
//
//    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
//    private static HttpServer httpServer;
//    private static EntityManagerFactory emf;
//
//    private static Grocery g1, g2;
//
//    static HttpServer startServer() {
//        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
//        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
//    }
//
//    @BeforeAll
//    public static void setUpClass() {
//        //This method must be called before you request the EntityManagerFactory
//        EMF_Creator.startREST_TestWithDB();
//        emf = EMF_Creator.createEntityManagerFactoryForTest();
//
//        httpServer = startServer();
//        //Setup RestAssured
//        RestAssured.baseURI = SERVER_URL;
//        RestAssured.port = SERVER_PORT;
//        RestAssured.defaultParser = Parser.JSON;
//    }
//
//    @AfterAll
//    public static void closeTestServer() {
//        //Don't forget this, if you called its counterpart in @BeforeAll
//        EMF_Creator.endREST_TestWithDB();
//        httpServer.shutdownNow();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//        g1 = new Grocery("id1","Ost", "Mælkeprodukter", "kg",  0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 1.2);
//        g2 = new Grocery("id2","OstHaps", "Mælkeprodukter", "kg",  0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.8);
//
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
//            em.persist(g1);
//            em.persist(g2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    @Test
//    public void getAllGroceriesTest() throws Exception {
//        given()
//                .contentType("application/json").
//                when().
//                get("/produce").
//                then().
//                statusCode(HttpStatus.OK_200.getStatusCode());
//    }
//}
