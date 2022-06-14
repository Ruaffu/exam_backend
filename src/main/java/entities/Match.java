package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Match.deleteAllRows", query = "DELETE from Match")
public class Match implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "opponentTeam", nullable = false)
    private String opponentTeam;

    @Column(name = "judge", nullable = false)
    private String judge;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "inDoors", nullable = false)
    private boolean inDoors;

    @ManyToMany
    private List<Player> players;

    @ManyToOne
    private Location location;

    public Match()
    {
    }

    public Match(String opponentTeam, String judge, String type, boolean inDoors, Location location)
    {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.inDoors = inDoors;
        this.players = new ArrayList<>();
        this.location = location;
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

    public boolean isInDoors()
    {
        return inDoors;
    }

    public void setInDoors(boolean inDoors)
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

    @Override
    public String toString()
    {
        return "Match{" +
                "id=" + id +
                ", opponentTeam='" + opponentTeam + '\'' +
                ", judge='" + judge + '\'' +
                ", type='" + type + '\'' +
                ", inDoors=" + inDoors +
                ", players=" + players +
                ", location=" + location +
                '}';
    }
}
