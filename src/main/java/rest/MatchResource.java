package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MatchDTO;
import facades.MatchFacade;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("matches")
public class MatchResource
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    @RolesAllowed("user")
    public Response getAllMatches() throws IOException
    {
        List<MatchDTO> response = FACADE.getAllMatches();
        return Response
                .ok()
                .entity(GSON.toJson(response))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/play")
    @RolesAllowed("user")
    public Response getMatchesByPlayerId(@PathParam("id") Long id) throws IOException
    {
        List<MatchDTO> response = FACADE.getMatchesByPlayerId(id);
        return Response
                .ok()
                .entity(GSON.toJson(response))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/location")
    @RolesAllowed("user")
    public Response getMatchesByLocationId(@PathParam("id") Long id) throws IOException
    {
        List<MatchDTO> response = FACADE.getMatchesByLocationId(id);
        return Response
                .ok()
                .entity(GSON.toJson(response))
                .build();
    }
}