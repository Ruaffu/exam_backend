package facades;

import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import entities.Location;
import entities.Match;
import entities.Player;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MatchFacadeTest
{

    private static EntityManagerFactory emf;
    private static MatchFacade facade;
    private static Match m1,m2,m3;
    private static Location l1,l2,l3;
    private static Player p1,p2,p3;

    public MatchFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = MatchFacade.getMatchFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
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
            em.createNamedQuery("Player.deleteAllRows").executeUpdate();
            em.createNamedQuery("Matches.deleteAllRows").executeUpdate();
            em.createNamedQuery("location.deleteAllRows").executeUpdate();
            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // US-1
    @Test
    public void testGetAllMatches() throws Exception {

        int expected = 3;
        int actual = facade.getAllMatches().size();

        assertEquals(expected, actual);
    }

    @Test
    public void tesGetMatchByPlayerID(){
        int expected = 1;
        int actual = facade.getMatchesByPlayerId(p1.getId()).size();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMatchesByLocation(){
        int expected = 1;
        int actual = facade.getMatchesByLocationId(l1.getId()).size();

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateLocation(){
        Location l4 = new Location("testAddress4","testCity4","testCondition4");
        l4.addMatch(m3);
        LocationDTO locationDTO = new LocationDTO(l4);
        facade.createLocation(locationDTO);

        int expected = 4;
        int actual = facade.getAllLocations().size();

        assertEquals(expected, actual);

    }

    @Test
    public void testCreateMatch(){
        Match m4 = new Match("testTeam4","testjudge4","testType4","true", l1);
        m4.addPlayer(p2);
        MatchDTO matchDTO = new MatchDTO(m4);
        facade.createMatch(matchDTO);

        int expected = 4;
        int actual = facade.getAllMatches().size();

        assertEquals(expected, actual);
    }

    @Test
    public void testCreatePlayer(){
        Player p4 = new Player("testName4","testPhone4","testEmail4","testStatus4");
        PlayerDTO playerDTO = new PlayerDTO(p4);
        facade.createPlayer(playerDTO);

        int expected = 4;
        int actual = facade.getAllPlayers().size();

        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateMatch(){
        m1.setOpponentTeam("Dream Team");
        m1.setJudge("momo");
        m1.setType("hello");
        m1.setInDoors("Yes indoors");
        m1.setLocation(l2);

        MatchDTO matchDTO = new MatchDTO(m1);
        facade.updateMatch(m1.getId(), matchDTO);

        assertEquals("momo", matchDTO.getJudge());
        assertEquals(l2.getId(), matchDTO.getLocation().getId());


    }



    @Test
    public void testUpdateAllInformation(){
        Player p4 = new Player("testName4","testPhone4","testEmail4","testStatus4");
        Player p5 = new Player("testName5","testPhone5","testEmail5","testStatus5");
        m1.setOpponentTeam("Dream Team");
        m1.setJudge("momo");
        m1.setType("hello");
        m1.setInDoors("Yes indoors");
        m1.setLocation(l2);
        List<Player> players = new ArrayList<>();
        players.add(p4);
        players.add(p5);
        m1.setPlayers(players);

        MatchDTO matchDTO = new MatchDTO(m1);
        facade.updateAllMatchInformation(m1.getId(), matchDTO);

        assertEquals("momo", matchDTO.getJudge());
        assertEquals(l2.getId(), matchDTO.getLocation().getId());

    }

    @Test
    public void testDeletePlayer(){
        facade.deletePlayer(p3.getId());
        int expected = 2;
        int actual = facade.getAllPlayers().size();

        assertEquals(expected, actual);

    }



}
