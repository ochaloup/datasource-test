package org.jboss.qa.jpa;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Helper SLSB for playing (initiate, update, ...) with the test entity.
 */
@Stateless
@Remote(TestEntityHelperRemote.class)
@Local(TestEntityHelperLocal.class)
public class TestEntityHelper implements TestEntityHelperRemote, TestEntityHelperLocal {

  @PersistenceContext
  EntityManager em;

  /**
   * Initiates the test entity with <code>entityPK</code> key with the value
   * <code>initValue</code>.
   * 
   * @param entityPK
   *          primary key of the test entity
   * @return initiated test entity instance
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public TestEntity initTestEntity(String entityPK, int initValue) {
    TestEntity entity = getTestEntity(entityPK);

    if (entity == null) {
      entity = new TestEntity(entityPK, initValue);
      em.persist(entity);
    } else {
      entity.setA(initValue);
    }

    return entity;
  }

  /**
   * Finds the test entity with <code>entityPK</code> key.
   * 
   * @param entityPK
   *          primary key of the test entity
   * @return test entity instance
   */
  public TestEntity getTestEntity(String entityPK) {
    return em.find(TestEntity.class, entityPK);
  }

  /**
   * Updates the test entity, i.e. increments its value by 1.
   * 
   * @param entityPK
   *          primary key of the test entity
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void updateTestEntity(String entityPK) {
    TestEntity entity = getTestEntity(entityPK);
    entity.setA(entity.getA() + 1);
  }

}
