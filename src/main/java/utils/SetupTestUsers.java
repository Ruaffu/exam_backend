package utils;


import entities.*;
import facades.UserFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");


    Location l1 = new Location("testAddress1","testCity1","testCondition1");
    Location l2 = new Location("testAddress2","testCity2","testCondition2");
    Location l3 = new Location("testAddress3","testCity3","testCondition3");
    Player p1 = new Player("testName1","testPhone1","testEmail1","testStatus1");
    Player p2 = new Player("testName2","testPhone2","testEmail2","testStatus2");
    Player p3 = new Player("testName3","testPhone3","testEmail3","testStatus3");
    Match m1 = new Match("testTeam1","testjudge1","testType1","false", l1);
    Match m2 = new Match("testTeam2","testjudge2","testType2","false", l2);
    Match m3 = new Match("testTeam3","testjudge3","testType3","false", l3);

    m1.addPlayer(p1);
    m1.addPlayer(p2);
    m2.addPlayer(p3);

    l1.addMatch(m1);
    l2.addMatch(m2);
    l3.addMatch(m3);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
    {
      throw new UnsupportedOperationException("You have not changed the passwords");
    }

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
//    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
//    em.persist(user);
    em.persist(admin);
    em.persist(both);
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
    UserFacade.getUserFacade(emf).registerNewUser(user);
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
