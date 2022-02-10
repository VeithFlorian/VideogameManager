package repository;

import model.VideogameUser;
import model.Videogame;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VideogameRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Videogame createVideogame(int userId, Videogame videogame) {
        var user = this.em.find(VideogameUser.class, userId);
        videogame.getUsers().add(user);
        user.getVideogames().add(videogame);
        for (var platform : videogame.getPlatforms()) {
            platform.videogames.add(videogame);
            em.persist(platform);
        }
        for (var genre : videogame.getGenres()) {
            genre.videogames.add(videogame);
            em.persist(genre);
        }
        this.em.persist(videogame);
        return videogame;
    }

    @ActivateRequestContext
    public List<Videogame> getGamesForUser(int userId) {
        var query = em.createNamedQuery("Videogame.forUser", Videogame.class);
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public Videogame removeGameForUser(int userId, int gameId) {
        var user = this.em.find(VideogameUser.class, userId);
        var game = this.em.find(Videogame.class, gameId);
        user.getVideogames().remove(game);
        for (var genre : game.getGenres()) {
            this.em.remove(genre);
        }
        for(var platform: game.getPlatforms()) {
            this.em.remove(platform);
        }
        this.em.remove(game);
        return game;
    }
}
