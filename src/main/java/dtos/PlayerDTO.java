package dtos;

import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO
{
    private long id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private List<String> matches;

    public PlayerDTO(Player player)
    {
        this.id = player.getId();
        this.name = player.getName();
        this.phone = player.getPhone();
        this.email = player.getEmail();
        this.status = player.getStatus();
        this.matches = getMatches(player.getMatches());
    }

    public List<String> getMatches(List<Match> matches){
        List<String> matchList = new ArrayList<>();
        for (Match m : matches)
        {
            matchList.add("Oppenent Team: "+m.getOpponentTeam()+" judge: "+ m.getJudge()+" type: "+m.getType()+" inDoors: "+m.getInDoors());
        }
        return matchList;
    }

    public static List<PlayerDTO> getDtos(List<Player> players) {
        List<PlayerDTO> playerDTOS = new ArrayList();
        players.forEach(player -> playerDTOS.add(new PlayerDTO(player)));
        return playerDTOS;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<String> getMatches()
    {
        return matches;
    }

    public void setMatches(List<String> matches)
    {
        this.matches = matches;
    }
}
