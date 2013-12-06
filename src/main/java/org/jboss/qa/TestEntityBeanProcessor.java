package org.jboss.qa;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.qa.jpa.TestEntityHelperLocal;
import org.jboss.qa.jpa.TestEntityHelperRemote;

@Stateless
@Remote(TestEntityBeanProcessorRemote.class)
public class TestEntityBeanProcessor implements TestEntityBeanProcessorRemote {
  @EJB
  TestEntityHelperRemote entityHelperRemote;
  
  @EJB
  TestEntityHelperLocal entityHelperLocal;
  
  @Override
  public void doStuffRemote(String idKey) {
    entityHelperRemote.initTestEntity(idKey, Integer.MIN_VALUE);
    entityHelperRemote.updateTestEntity(idKey);
  }
  
  @Override
  public void doStuffLocal(String idKey) {
    entityHelperLocal.initTestEntity(idKey, Integer.MIN_VALUE);
    entityHelperLocal.updateTestEntity(idKey);
  }
}
