package repository;

import io.vertx.ext.auth.User;
import model.VideogameUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepository {
    @Inject
    EntityManager em;

    @Transactional
    public VideogameUser createUser(VideogameUser user) {
        this.em.persist(user);
        return user;
    }

    public VideogameUser removeUser(int userId) {
        var user = this.em.find(VideogameUser.class, userId);
        em.remove(user);
        return user;
    }

    public VideogameUser loginUser(String username, String password) {
        var query = em.createNamedQuery("User.login", VideogameUser.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
