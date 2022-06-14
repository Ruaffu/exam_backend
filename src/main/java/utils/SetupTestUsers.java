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


    Location l1 = new Location("4 newport street","Mega City 1","Good");
    Location l2 = new Location("32 Angel way","Sun City","great");
    Location l3 = new Location("3 dusty way","Sad city","poor");
    Player p1 = new Player("Gordon Freeman","32456789","freeman@blackmesa.science","unknown");
    Player p2 = new Player("Severus Snape","90785634","snape@hogwarts.magic","inactive");
    Player p3 = new Player("Alyx Vance","32672389","avance@hl3.valve","active");
    Match m1 = new Match("The Freemans","Tom","Tennis","yes", l1);
    Match m2 = new Match("Testers","jack","badminton","no", l2);
    Match m3 = new Match("The Griffins","Hermione","pool","no", l3);

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
