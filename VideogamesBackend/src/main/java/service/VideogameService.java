package service;

import model.Videogame;
import repository.UserRepository;
import repository.VideogameRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("videogames")
public class VideogameService {
    @Inject
    VideogameRepository videogameRepository;

    @GET
    @ActivateRequestContext
    @Produces(MediaType.APPLICATION_JSON)
    public List<Videogame> getGamesForUser(@QueryParam("userId") int userId) {
        return videogameRepository.getGamesForUser(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Videogame createVideogame(@QueryParam("userId") int userId, Videogame videogame) {
        return videogameRepository.createVideogame(userId, videogame);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Videogame deleteVideogame(@QueryParam("userId") int userId, @QueryParam("gameId") int videogameId) {
        return videogameRepository.removeGameForUser(userId, videogameId);
    }
}
