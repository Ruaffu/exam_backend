package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.LocationDTO;
import dtos.MatchDTO;
import dtos.PlayerDTO;
import facades.MatchFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("admin")
public class AdminResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final MatchFacade FACADE = MatchFacade.getMatchFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, World!";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/location")
    public Response createLocation(String data) throws IOException
    {
        LocationDTO locationDTO = GSON.fromJson(data, LocationDTO.class);
        LocationDTO newLocationDTO = FACADE.createLocation(locationDTO);
        return Response
                .ok()
                .entity(GSON.toJson(newLocationDTO))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/match")
    @RolesAllowed("admin")
    public Response createMatch(String data) throws IOException
    {
        MatchDTO matchDTO = GSON.fromJson(data, MatchDTO.class);
        MatchDTO newLMatchDTO = FACADE.createMatch(matchDTO);
        return Response
                .ok()
                .entity(GSON.toJson(newLMatchDTO))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/player")
    @RolesAllowed("admin")
    public Response createPlayer(String data) throws IOException
    {
        PlayerDTO playerDTO = GSON.fromJson(data, PlayerDTO.class);
        PlayerDTO newPlayerDTO = FACADE.createPlayer(playerDTO);
        return Response
                .ok()
                .entity(GSON.toJson(newPlayerDTO))
                .build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/match")
    @RolesAllowed("admin")
    public Response updateMatch(@PathParam("id") Long id,String data) throws IOException
    {
        MatchDTO matchDTO = GSON.fromJson(data, MatchDTO.class);
        MatchDTO newMatchDTO = FACADE.updateMatch(id, matchDTO);
        return Response
                .ok()
                .entity(GSON.toJson(newMatchDTO))
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/delete")
    @RolesAllowed("admin")
    public Response deletePlayer(@PathParam("id") Long id) throws IOException
    {
        PlayerDTO playerDTO = FACADE.deletePlayer(id);
        return Response
                .ok()
                .entity(GSON.toJson(playerDTO))
                .build();
    }
}