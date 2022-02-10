package service;

import model.VideogameUser;
import repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("users")
public class UserService {
    @Inject
    UserRepository userRepository;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VideogameUser loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        return userRepository.loginUser(username, password);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public VideogameUser createUser(VideogameUser user) {
        return userRepository.createUser(user);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public VideogameUser deleteUser(@QueryParam("userId") int userId) {
        return userRepository.removeUser(userId);
    }
}
