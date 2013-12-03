package org.jboss.qa.jpa;

import javax.ejb.Remote;

@Remote
public interface TestEntityHelperRemote {
  TestEntity initTestEntity(String entityPK, int initValue);
  TestEntity getTestEntity(String entityPK);
  void updateTestEntity(String entityPK);
}