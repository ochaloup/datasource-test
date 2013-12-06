package org.jboss.qa.jpa;


public interface TestEntityHelperLocal {
  TestEntity initTestEntity(String entityPK, int initValue);
  TestEntity getTestEntity(String entityPK);
  void updateTestEntity(String entityPK);
}