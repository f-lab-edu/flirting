package site.ymango;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void clear() {
    entityManager.createNativeQuery("SHOW TABLES").getResultList().forEach(
        tableName -> entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate());
  }
}
