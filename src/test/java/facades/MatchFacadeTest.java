package facades;

import entities.Location;
import entities.Match;
import entities.Player;
import utils.EMF_Creator;
import entities.RenameMe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        m1 = new Match("testTeam1","testjudge1","testType1",false, l1);
        m2 = new Match("testTeam2","testjudge2","testType2",false, l2);
        m3 = new Match("testTeam3","testjudge3","testType3",false, l3);
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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetAllMatches() throws Exception {

        int expected = 3;
        int actual = facade.getAllMatches().size();

        assertEquals(expected, actual);
    }
    

}
