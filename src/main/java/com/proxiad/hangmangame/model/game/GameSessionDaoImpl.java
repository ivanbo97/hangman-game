package com.proxiad.hangmangame.model.game;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class GameSessionDaoImpl implements GameSessionDao {

  @PersistenceContext EntityManager entityManager;

  @Override
  public Optional<GameSession> get(String gameId) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<GameSession> cq = cb.createQuery(GameSession.class);
    Root<GameSession> gameSession = cq.from(GameSession.class);
    cq.select(gameSession);
    cq.where(cb.equal(gameSession.get(GameSession_.GAME_ID), gameId));

    TypedQuery<GameSession> typedQuery = entityManager.createQuery(cq);
    GameSession retrievedSession = null;
    try {
      retrievedSession = typedQuery.getSingleResult();
    } catch (NoResultException exc) {
      return Optional.ofNullable(null);
    }
    return Optional.of(retrievedSession);
  }

  @Override
  public void save(GameSession newSession) {
    entityManager.persist(newSession);
  }

  @Override
  public void delete(GameSession gameSession) {
    entityManager.remove(gameSession);
  }

  @Override
  public void deleteById(String gameId) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaDelete<GameSession> criteriaDelete = cb.createCriteriaDelete(GameSession.class);
    Root<GameSession> root = criteriaDelete.from(GameSession.class);
    criteriaDelete.where(cb.equal(root.get(GameSession_.GAME_ID), gameId));
    entityManager.createQuery(criteriaDelete).executeUpdate();
  }
}