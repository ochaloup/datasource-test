package org.jboss.qa;

import javax.ejb.EJB;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.qa.jpa.TestEntity;
import org.jboss.qa.jpa.TestEntityHelper;
import org.jboss.qa.jpa.TestEntityHelperRemote;
import org.jboss.qa.util.TxUtil;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleJPATest {
  private static final String DEPLOYMENT_NAME = "jpatest";
  
  @EJB
  private TestEntityHelperRemote entityHelper;

  @Deployment(name = DEPLOYMENT_NAME)
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, DEPLOYMENT_NAME + ".jar")
        .addPackage(TestEntity.class.getPackage())
        .addAsManifestResource("xa-ds.xml")
        // .addAsManifestResource("normal-ds.xml")
        // .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        .addAsManifestResource("persistence.xml")
        .addAsManifestResource(new StringAsset("Dependencies: org.jboss.jts\n"), "MANIFEST.MF");
  }

  @Test
  public void testUpdate() {
    Assert.assertNotNull(entityHelper);
    entityHelper.initTestEntity(DEPLOYMENT_NAME, 1);
    entityHelper.updateTestEntity(DEPLOYMENT_NAME);
  }
  
  @Test
  @RunAsClient
  public void testUpdateRemote() throws NamingException {
    TestEntityHelperRemote bean = TxUtil.lookup(TestEntityHelperRemote.class, TestEntityHelper.class, DEPLOYMENT_NAME);
    Assert.assertNotNull(bean);
    bean.initTestEntity(DEPLOYMENT_NAME, 1);
    bean.updateTestEntity(DEPLOYMENT_NAME);
  }
}
