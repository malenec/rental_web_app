package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RentalDTO;
import dtos.UserDTO;
import errorhandling.NotFoundException;
import facades.RentalFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rental")
public class RentalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final RentalFacade FACADE =  RentalFacade.getRentalFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRentals() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllRentals())).build();
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRental(String rentalInfo){
        System.out.println(rentalInfo);
        RentalDTO rentalDTO = GSON.fromJson(rentalInfo, RentalDTO.class);
        System.out.println(rentalDTO);
        FACADE.createRental(rentalDTO);
        return Response.ok().entity(rentalDTO).build();
    }


    @POST
    @Path("assign/{username}/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response assignUserToRental(@PathParam("username") String userName, @PathParam("id") Long rentalId){
        System.out.println(rentalId);
        UserDTO userDTO = FACADE.assignUserToRental(userName, rentalId);
        return Response.ok().entity(userDTO).build();
    }

    @POST
    @Path("remove/{username}/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response unassignUserFromRental(@PathParam("username") String userName, @PathParam("id") Long rentalId){
        UserDTO userDTO = FACADE.removeUserFromRental(userName, rentalId);
        return Response.ok().entity(userDTO).build();
    }

    @PUT
    @Path("update")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updateRental(String rentalInfo) throws NotFoundException {
        RentalDTO rentalDTO = GSON.fromJson(rentalInfo, RentalDTO.class);
        RentalDTO updatedRentalDTO = FACADE.updateRental(rentalDTO);
        return Response.ok().entity(updatedRentalDTO).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteTrip(@PathParam("id") Long rentalId) throws NotFoundException {
        FACADE.deleteRental(rentalId);
        return Response.ok().build();
    }

}