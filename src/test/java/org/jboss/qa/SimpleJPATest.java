package org.jboss.qa;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.qa.jpa.TestEntity;
import org.jboss.qa.jpa.TestEntityHelper;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleJPATest {
  private static final String ENTITY_NAME = "jpatest";
  
  @Inject
  private TestEntityHelper entityHelper;

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "test-jpa.jar")
        .addPackage(TestEntity.class.getPackage())
        .addAsManifestResource("xa-ds.xml")
        // .addAsManifestResource("normal-ds.xml")
        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        .addAsManifestResource("persistence.xml");
  }

  @Test
  public void testIsDeployed() {
    Assert.assertNotNull(entityHelper);
    entityHelper.initTestEntity(ENTITY_NAME, 1);
    entityHelper.updateTestEntity(ENTITY_NAME);
  }
}
