package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.GuideDTO;
import dtos.TripDTO;
import errorhandling.NotFoundException;
import facades.GuideFacade;
import facades.TripFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("trip")
public class TripResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TripFacade FACADE =  TripFacade.getTripFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTrips() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllTrips())).build();
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGuide(String tripInfo){
        TripDTO tripDTO = GSON.fromJson(tripInfo, TripDTO.class);
        FACADE.createTrip(tripDTO);
        return Response.ok().entity(tripDTO).build();
    }

    @POST
    @Path("assign/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response assignUserToTrip(@PathParam("username") String userName, String tripName){
        TripDTO tripDTO = GSON.fromJson(tripName, TripDTO.class);
        FACADE.assignUserToTrip(userName, tripDTO);
        return Response.ok().entity(tripDTO).build();
    }

    @POST
    @Path("remove/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response unassignUserFromTrip(@PathParam("username") String userName, String tripName){
        TripDTO tripDTO = GSON.fromJson(tripName, TripDTO.class);
        FACADE.removeUserFromTrip(userName, tripDTO);
        return Response.ok().entity(tripDTO).build();
    }

    @PUT
    @Path("update")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updateTrip(String tripInfo) throws NotFoundException {
        TripDTO tripDTO = GSON.fromJson(tripInfo, TripDTO.class);
        TripDTO updatedTripDTO = FACADE.updateTrip(tripDTO);
        return Response.ok().entity(updatedTripDTO).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteTrip(@PathParam("id") String tripId) throws NotFoundException {
        FACADE.deleteTrip(tripId);
        return Response.ok().build();
    }

}