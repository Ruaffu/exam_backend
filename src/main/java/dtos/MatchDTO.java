package dtos;

import entities.Location;
import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchDTO
{
    private Long id;
     String opponentTeam;
     String judge;
     String type;
     String inDoors;
     LocationDTO location;
     List<String> players;

    public MatchDTO()
    {
    }

    public MatchDTO(Match match)
    {
        if(match.getId() != null)
            this.id = match.getId();
        this.opponentTeam = match.getOpponentTeam();
        this.judge = match.getJudge();
        this.type = match.getType();
        this.inDoors = match.getInDoors();
        this.players = getPlayers(match.getPlayers());
        if(match.getLocation() != null)
        this.location = new LocationDTO(match.getLocation());
    }



    public List<String> getPlayers(List<Player> players){
        List<String> playerList = new ArrayList<>();
        for (Player p : players)
        {
            playerList.add(p.getName());

        }
        return playerList;
    }

    public static List<MatchDTO> getDtos(List<Match> matches) {
        List<MatchDTO> matchDTOS = new ArrayList();
        matches.forEach(match -> matchDTOS.add(new MatchDTO(match)));
        return matchDTOS;
    }

    public String getInDoors()
    {
        return inDoors;
    }

    public void setInDoors(String inDoors)
    {
        this.inDoors = inDoors;
    }

    public LocationDTO getLocation()
    {
        return location;
    }

    public void setLocation(LocationDTO location)
    {
        this.location = location;
    }

    public long getId()
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


    public List<String> getPlayers()
    {
        return players;
    }

    public void setPlayers(List<String> players)
    {
        this.players = players;
    }
}
