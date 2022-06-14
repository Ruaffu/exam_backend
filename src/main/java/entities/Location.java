package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Location.deleteAllRows", query = "DELETE from Location")
public class Location implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "condition", nullable = false)
    private String condition;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Match> matches;

    public Location()
    {
    }

    public Location(String address, String city, String condition)
    {
        this.address = address;
        this.city = city;
        this.condition = condition;
        this.matches = new ArrayList<>();
    }

    public Long getId()
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

    public List<Match> getMatches()
    {
        return matches;
    }

    public void setMatches(List<Match> matches)
    {
        this.matches = matches;
    }

    public void addMatch(Match match){
        matches.add(match);
    }

    @Override
    public String toString()
    {
        return "Location{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", condition='" + condition + '\'' +
                ", matches=" + matches +
                '}';
    }
}
