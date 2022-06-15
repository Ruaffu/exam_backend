package facades;

import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import dtos.RenameMeDTO;
import entities.Location;
import entities.Match;
import entities.Player;
import entities.RenameMe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
//import jdk.internal.org.objectweb.asm.tree.analysis.SourceValue;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MatchFacade
{

    private static MatchFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MatchFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MatchFacade getMatchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MatchFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PlayerDTO> getAllPlayers(){
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p", Player.class);
            List<Player> players = query.getResultList();
            return PlayerDTO.getDtos(players);
        }finally
        {
            em.close();
        }

    }

    //US-1
    public List<MatchDTO> getAllMatches(){
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
            List<Match> matches = query.getResultList();
            return MatchDTO.getDtos(matches);
        }finally
        {
            em.close();
        }

    }

    //US-2
    public List<MatchDTO> getMatchesByPlayerId(Long pId)
    {
        EntityManager em = emf.createEntityManager();
        Player player = em.find(Player.class, pId);
        List<Match> matches = player.getMatches();
       return MatchDTO.getDtos(matches);

    }

    //US-3
    public List<MatchDTO> getMatchesByLocationId(Long lId)
    {
        EntityManager em = emf.createEntityManager();
        Location  location = em.find(Location.class, lId);
        List<Match> matches = location.getMatches();
        return MatchDTO.getDtos(matches);
    }

    //US-7
    public PlayerDTO deletePlayer(Long id)
    {
        EntityManager em = emf.createEntityManager();
        Player player = em.find(Player.class, id);
        try
        {
            em.getTransaction().begin();
            for (Match m : player.getMatches() )
            {
                m.setPlayers(null);
                em.merge(m);

            }

            em.remove(player);
            em.getTransaction().commit();
            return new PlayerDTO(player);
        }finally
        {
            em.close();
        }
    }

    public MatchDTO deleteMatch(Long id)
    {
        EntityManager em = emf.createEntityManager();
        Match match = em.find(Match.class, id);
        try
        {
            em.getTransaction().begin();
            for (Player p : match.getPlayers() )
            {
                p.setMatches(null);
                em.merge(p);

            }

            em.remove(match);
            em.getTransaction().commit();
            return new MatchDTO(match);
        }finally
        {
            em.close();
        }
    }

    public LocationDTO deleteLocation(Long id)
    {
        EntityManager em = emf.createEntityManager();
        Location location = em.find(Location.class, id);
        try
        {
            em.getTransaction().begin();
            for (Match m : location.getMatches() )
            {
                m.setPlayers(null);
                em.merge(m);

            }

            em.remove(location);
            em.getTransaction().commit();
            return new LocationDTO(location);
        }finally
        {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        MatchFacade fe = getMatchFacade(emf);
        fe.getAllMatches().forEach(dto->System.out.println(dto));
    }

    //US-4
    public LocationDTO createLocation(LocationDTO locationDTO)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            Location location = new Location(locationDTO.getAddress(), locationDTO.getCity(), locationDTO.getCondition());
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return new LocationDTO(location);
        }finally
        {
            em.close();
        }
    }

    public List<LocationDTO> getAllLocations()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
            List<Location> locations = query.getResultList();
            return LocationDTO.getDtos(locations);
        }finally
        {
            em.close();
        }
    }

    //US-4
    public MatchDTO createMatch(MatchDTO matchDTO)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
//            Location location = em.find(Location.class, matchDTO.getLocation().getId());
            Match match = new Match(matchDTO.getOpponentTeam(), matchDTO.getJudge(), matchDTO.getType(), matchDTO.getInDoors());
            em.getTransaction().begin();
            em.persist(match);
            em.getTransaction().commit();
            return new MatchDTO(match);
        }finally
        {
            em.close();
        }
    }

    //US-4
    public PlayerDTO createPlayer(PlayerDTO playerDTO)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            Player player = new Player(playerDTO.getName(), playerDTO.getPhone(), playerDTO.getEmail(), playerDTO.getStatus());
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            return new PlayerDTO(player);
        }finally
        {
            em.close();
        }
    }

    //US-5
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Match match = em.find(Match.class, id);
            match.setOpponentTeam(matchDTO.getOpponentTeam());
            match.setJudge(matchDTO.getJudge());
            match.setType(matchDTO.getType());
            match.setInDoors(matchDTO.getInDoors());
            if (matchDTO.getLocation() != null)
            {
                Location location = em.find(Location.class, matchDTO.getLocation().getId());
                match.setLocation(location);
            }

            em.getTransaction().begin();
            em.merge(match);
            em.getTransaction().commit();
            return new MatchDTO(match);
        }finally
        {
            em.close();
        }
    }


    public MatchDTO updateAllMatchInformation(Long id, MatchDTO matchDTO)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Match match = em.find(Match.class, id);
            match.setOpponentTeam(matchDTO.getOpponentTeam());
            match.setJudge(matchDTO.getJudge());
            match.setType(matchDTO.getType());
            match.setInDoors(matchDTO.getInDoors());
            Location location = em.find(Location.class, matchDTO.getLocation().getId());
            match.setLocation(location);

            for (String player : matchDTO.getPlayers())
            {


            }

            em.getTransaction().begin();
            em.persist(match);
            em.getTransaction().commit();
            return new MatchDTO(match);
        }finally
        {
            em.close();
        }
    }

    public MatchDTO connectMatchToLocation(Long matchID, Long locationId)
    {
        EntityManager em = emf.createEntityManager();
        Match match = em.find(Match.class, matchID);
        Location location = em.find(Location.class, locationId);
        match.setLocation(location);
        try
        {
            em.getTransaction().begin();
            em.merge(match);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return new MatchDTO(match);
    }
}
