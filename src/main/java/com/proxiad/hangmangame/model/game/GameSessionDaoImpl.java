package com.proxiad.hangmangame.model.game;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class GameSessionDaoImpl implements GameSessionDao {

  @PersistenceContext private EntityManager entityManager;

  private CriteriaBuilder cb;

  @PostConstruct
  public void init() {
    cb = entityManager.getCriteriaBuilder();
  }

  @Override
  public Optional<GameSession> get(String gameId) {
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
  public List<GameSession> getOngoingGames() {

    CriteriaQuery<GameSession> cq = cb.createQuery(GameSession.class);
    Root<GameSession> gameSession = cq.from(GameSession.class);

    Predicate triesLeftPredicate = cb.greaterThan(gameSession.get(GameSession_.TRIES_LEFT), 0);
    Predicate lettersToGuessLeftPredicate =
        cb.greaterThan(gameSession.get(GameSession_.LETTERS_TO_GUESS_LEFT), 0);

    cq.select(gameSession);
    cq.where(cb.and(triesLeftPredicate, lettersToGuessLeftPredicate));

    TypedQuery<GameSession> typedQuery = entityManager.createQuery(cq);
    return typedQuery.getResultList();
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

    CriteriaDelete<GameSession> criteriaDelete = cb.createCriteriaDelete(GameSession.class);
    Root<GameSession> root = criteriaDelete.from(GameSession.class);
    criteriaDelete.where(cb.equal(root.get(GameSession_.GAME_ID), gameId));
    entityManager.createQuery(criteriaDelete).executeUpdate();
  }
}
