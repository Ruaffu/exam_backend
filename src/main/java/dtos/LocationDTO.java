package dtos;

import entities.Location;
import entities.Match;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO
{
    private Long id;
    private String address;
    private String city;
    private String condition;
    private List<String> matches;

    public LocationDTO(Location location)
    {
        if (location.getId() != null)
            this.id = location.getId();
        this.address = location.getAddress();
        this.city = location.getCity();
        this.condition = location.getCondition();
        this.matches = getMatches(location.getMatches());
    }

    public List<String> getMatches(List<Match> matches){
        List<String> matchList = new ArrayList<>();
        for (Match m : matches)
        {
            matchList.add("Oppenent Team: "+m.getOpponentTeam()+" judge: "+ m.getJudge()+" type: "+m.getType()+" inDoors: "+m.getInDoors());

        }
        return matchList;
    }

    public static List<LocationDTO> getDtos(List<Location> locations) {
        List<LocationDTO> locationDTOS = new ArrayList();
        locations.forEach(location -> locationDTOS.add(new LocationDTO(location)));
        return locationDTOS;
    }

    public long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
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
