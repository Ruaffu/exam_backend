package rest;

import com.google.gson.Gson;
import dtos.CatDTO;
import dtos.CatImageDTO;
import dtos.FactDTO;
import utils.HttpUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("cat")
public class CatResource
{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cat")
    public String getCat() throws IOException
    {
        Gson GSON = new Gson();
        String fact = HttpUtils.fetchData("https://catfact.ninja/fact");
        FactDTO factDTO = GSON.fromJson(fact, FactDTO.class);
        String catImage = HttpUtils.fetchData("https://cataas.com/cat?json=true");
        CatImageDTO catImageDTO = GSON.fromJson(catImage, CatImageDTO.class);
        catImageDTO.setUrl("https://cataas.com"+catImageDTO.getUrl());
        CatDTO catDTO = new CatDTO(factDTO, catImageDTO);
        return GSON.toJson(catDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("facts")
    public String getCatFact() throws IOException
    {
        return HttpUtils.fetchData("https://catfact.ninja/fact");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cats")
    public String getCatPictures() throws IOException
    {
        return HttpUtils.fetchData("https://api.thecatapi.com/v1/images/search");
    }
}