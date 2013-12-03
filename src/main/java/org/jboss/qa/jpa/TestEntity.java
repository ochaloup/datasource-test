package org.jboss.qa.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Simple TestEntity for crash recovery tests.
 * 
 * CREATE TABLE testentity ( id VARCHAR, a INTEGER )
 */
@Entity
@Table(name = "datasourcetestjpa")
public class TestEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private int a;

  public TestEntity() {
  }

  public TestEntity(String id, int a) {
    this.id = id;
    this.a = a;
  }

  @Id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TestEntity other = (TestEntity) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "TestEntity [id=" + id + ", a=" + a + "]";
  }

}
