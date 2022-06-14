package dtos;

import entities.Location;
import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchDTO
{
    private long id;
    private String opponentTeam;
    private String judge;
    private String type;
    private boolean inDoors;
    private Location location;
    private List<String> players;

    public MatchDTO(Match match)
    {
        this.id = match.getId();
        this.opponentTeam = match.getOpponentTeam();
        this.judge = match.getJudge();
        this.type = match.getType();
        this.inDoors = match.isInDoors();
        this.location = match.getLocation();
        this.players = getPlayers(match.getPlayers());
    }

    public List<String> getPlayers(List<Player> players){
        List<String> playerList = new ArrayList<>();
        for (Player p : players)
        {
            playerList.add(p.getName());

        }
        return playerList;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public List<String> getPlayers()
    {
        return players;
    }

    public void setPlayers(List<String> players)
    {
        this.players = players;
    }
}
