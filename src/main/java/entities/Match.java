package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Matches.deleteAllRows", query = "DELETE from Match ")
@Table(name = "matches")
public class Match implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Column(name = "opponentTeam", nullable = false)
    private String opponentTeam;

    @Column(name = "judge", nullable = false)
    private String judge;

    @Column(name = "matchtype", nullable = false)
    private String type;

    @Column(name = "inDoors", nullable = false)
    private String inDoors;

    @ManyToMany()
    private List<Player> players;

    @ManyToOne()
    private Location location;

    public Match()
    {
    }

    public Match(String opponentTeam, String judge, String type, String inDoors, Location location)
    {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.inDoors = inDoors;
        this.players = new ArrayList<>();
        this.location = location;
    }

    public Match(String opponentTeam, String judge, String type, String inDoors)
    {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.inDoors = inDoors;
        this.players = new ArrayList<>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOpponentTeam()
    {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam)
    {
        this.opponentTeam = opponentTeam;
    }

    public String getJudge()
    {
        return judge;
    }

    public void setJudge(String judge)
    {
        this.judge = judge;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getInDoors()
    {
        return inDoors;
    }

    public void setInDoors(String inDoors)
    {
        this.inDoors = inDoors;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public  void addPlayer(Player player){
        if (player != null){
            this.players.add(player);
            player.getMatches().add(this);
        }
    }

    public void removePlayer(Player player){
        players.remove(player);
    }


}
