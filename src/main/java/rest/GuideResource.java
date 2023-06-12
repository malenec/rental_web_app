package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.GuideDTO;
import facades.GuideFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("guide")
public class GuideResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final GuideFacade FACADE =  GuideFacade.getGuideFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGuide(String guideInfo){
        GuideDTO guideDTO = GSON.fromJson(guideInfo, GuideDTO.class);
        FACADE.createGuide(guideDTO);
        return Response.ok().entity(guideDTO).build();
    }
}