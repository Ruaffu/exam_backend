package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import entities.*;
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
import static org.hamcrest.Matchers.hasItem;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class AdminResourceTest
{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static RenameMe r1, r2;
    private static Match m1,m2,m3;
    private static Location l1,l2,l3;
    private static Player p1,p2,p3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");
        l1 = new Location("testAddress1","testCity1","testCondition1");
        l2 = new Location("testAddress2","testCity2","testCondition2");
        l3 = new Location("testAddress3","testCity3","testCondition3");
        m1 = new Match("testTeam1","testjudge1","testType1","false", l1);
        m2 = new Match("testTeam2","testjudge2","testType2","false", l2);
        m3 = new Match("testTeam3","testjudge3","testType3","false", l3);
        p1 = new Player("testName1","testPhone1","testEmail1","testStatus1");
        p2 = new Player("testName2","testPhone2","testEmail2","testStatus2");
        p3 = new Player("testName3","testPhone3","testEmail3","testStatus3");

        m1.addPlayer(p1);
        m1.addPlayer(p2);
        m2.addPlayer(p3);

        l1.addMatch(m1);
        l2.addMatch(m2);
        l3.addMatch(m3);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("user.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Player.deleteAllRows").executeUpdate();
            em.createNamedQuery("Matches.deleteAllRows").executeUpdate();
            em.createNamedQuery("location.deleteAllRows").executeUpdate();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    //This test assumes the database contains two rows
//    @Test
//    public void testCreateLocation() throws Exception {
//        login("admin", "test123");
//        Location l4 = new Location("testAddress4","testCity4","testCondition4");
////        l4.addMatch(m3);
//        LocationDTO locationDTO = new LocationDTO(l4);
//        String data = GSON.toJson(locationDTO);
//        System.out.println(data);
//        given()
//                .contentType("application/json")
//                .header("x-Access-token", securityToken)
//                .body(data)
//                .get("/admin/location").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("address", equalTo(locationDTO.getAddress()));
//
//    }
//
//    @Test
//    public void testCreateMatch() throws Exception {
//        login("admin", "test123");
//        Match m4 = new Match("testTeam4","testjudge4","testType4","true", l1);
//        m4.addPlayer(p2);
//        MatchDTO matchDTO = new MatchDTO(m4);
//        String data = GSON.toJson(matchDTO);
//        System.out.println(data);
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .body(data)
//                .get("/admin/match").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("judge", equalTo(matchDTO.getJudge()));
//
//    }
//
//    @Test
//    public void testCreatePlayer() throws Exception {
//        login("admin", "test123");
//        Player p4 = new Player("testName4","testPhone4","testEmail4","testStatus4");
//        PlayerDTO playerDTO = new PlayerDTO(p4);
//        String data = GSON.toJson(playerDTO);
//        System.out.println(data);
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .body(data)
//                .get("/admin/player").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("name", equalTo(playerDTO.getName()));
//
//    }
//
//    @Test
//    public void testUpdateMatch() throws Exception {
//        login("admin", "test123");
//        m1.setOpponentTeam("Dream Team");
//        m1.setJudge("momo");
//        m1.setType("hello");
//        m1.setInDoors("Yes indoors");
//        m1.setLocation(l2);
//
//        MatchDTO matchDTO = new MatchDTO(m1);
//        String data = GSON.toJson(matchDTO);
//        System.out.println(data);
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .body(data)
//                .get("/admin/{id}/match", m1.getId()).then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("judge", equalTo(matchDTO.getJudge()));
//
//    }
//
//    @Test
//    public void testDeletePlayer() throws Exception {
//        login("admin", "test123");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .get("/admin/{id}/delete", p1.getId()).then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("name", equalTo(p1.getName()));
//
//    }




}
