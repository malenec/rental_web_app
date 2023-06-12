package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import facades.HouseFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("house")
public class HouseResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final HouseFacade FACADE =  HouseFacade.getHouseFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRentals() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllHouses())).build();
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createHouse(String houseInfo){
        HouseDTO houseDTO = GSON.fromJson(houseInfo, HouseDTO.class);
        FACADE.createHouse(houseDTO);
        return Response.ok().entity(houseDTO).build();
    }
}