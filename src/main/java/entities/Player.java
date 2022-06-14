package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Player.deleteAllRows", query = "DELETE from Player")
@Table(name = "player")
public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToMany(mappedBy = "players", cascade = CascadeType.PERSIST)
    private List<Match> matches;

    public Player()
    {
    }

    public Player(String name, String phone, String email, String status)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
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

    public List<Match> getMatches()
    {
        return matches;
    }

    public void setMatches(List<Match> matches)
    {
        this.matches = matches;
    }

    public void addMatch(Match match){
        if (match != null){
            this.matches.add(match);
            match.getPlayers().add(this);
        }

    }


}
