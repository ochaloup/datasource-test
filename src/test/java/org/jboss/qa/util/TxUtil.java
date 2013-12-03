package org.jboss.qa.util;

import org.jboss.as.network.NetworkUtils;
import org.jboss.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;

/**
 * @author <a href="istudens@redhat.com">Ivo Studensky</a>
 */
public abstract class TxUtil {
  protected static final Logger log = Logger.getLogger(TxUtil.class);

  public static <T> T lookup(final Class<T> remoteClass, final Class<?> beanClass, final String archiveName)  throws NamingException {
    return lookup(remoteClass, beanClass, "", archiveName);
  }

  public static <T> T lookup(final Class<T> remoteClass, final Class<?> beanClass, final String appName, final String archiveName) throws NamingException {
    String myContext = createRemoteEjbJndiContext(
        appName, 
        archiveName, 
        "", 
        beanClass.getSimpleName(),
        remoteClass.getName(), 
        false);

    Context ctx = createNamingContext();
    return remoteClass.cast(ctx.lookup(myContext));
  }

  private static String createRemoteEjbJndiContext(String appName, String moduleName, 
      String distinctName, String beanName, String viewClassName, boolean isStateful) {

      return "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + 
          beanName + "!" + viewClassName + (isStateful ? "?stateful" : "");
  }

  private static Context createNamingContext() throws NamingException {
    final Properties jndiProps = new Properties();
    jndiProps.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming"); 
    return new InitialContext(jndiProps);

  }

  /**
   * Possible lookup ways check
   * org.jboss.as.test.integration.ejb.iiop.naming.IIOPNamingTestCase in WildFly
   * testsuite.
   */
  public static <T> T lookupIIOP(final Class<T> homeClass, final Class<?> beanClass) throws NamingException {
    String serverName = NetworkUtils.formatPossibleIpv6Address(System.getProperty("node0", "localhost"));

    // This is needed for the PortableRemoteObject.narrow method does not return 'null'
    // WARN: IBM JDK does not know to dynamically generate IIOP stubs - they
    // have to be generated manually before test with rmic tool - check ibmjdk.profile in pom.xml
    System.setProperty("com.sun.CORBA.ORBUseDynamicStub", "true");

    final Properties prope = new Properties();
    prope.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
    prope.put(Context.PROVIDER_URL, "corbaloc::" + serverName + ":3528/JBoss/Naming/root");
    final InitialContext context = new InitialContext(prope);
    final Object ejbHome = context.lookup(beanClass.getSimpleName());

    return homeClass.cast(PortableRemoteObject.narrow(ejbHome, homeClass));
  }

  /**
   * This method just wait for a specified time and hopes that the recovery will
   * be triggered by periodic recovery on server on its own.
   */
  public static void waitForRecovery() throws InterruptedException {
    int waitingTimeMs = 5 * 60 * 1000;
    log.info("Waiting for recovery being triggered by periodic recovery for " + (waitingTimeMs / (60 * 1000)) + " minutes");
    // Waiting for some time to get recovery being triggered by thread
    try {
      Thread.sleep(waitingTimeMs);
    } catch (InterruptedException ex) {
      log.warn("Waiting for recovery was interrupted", ex);
      Thread.currentThread().interrupt();
    }
    log.info("Waiting for recovery is done - let's resume in test execution");
  }

  /**
   * Stolen from JBossTS project from CrashRecoveryDelays prod the recovery
   * manager via its socket. This avoid any sleep delay.
   */
  public static void triggerRecovery(String recoveryManagerHost, int recoveryManagerPort) throws InterruptedException {
    log.info("doRecovery#host = " + recoveryManagerHost);
    log.info("doRecovery#port = " + recoveryManagerPort);

    BufferedReader in = null;
    PrintStream out = null;
    Socket sckt = null;

    try {
      sckt = new Socket(recoveryManagerHost, recoveryManagerPort);

      in = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
      out = new PrintStream(sckt.getOutputStream());

      // Output ping message
      out.println("SCAN");
      out.flush();

      // Receive pong message
      String inMessage = in.readLine();

      if (!inMessage.equals("DONE")) {
        log.error("Recovery failed with message: " + inMessage);
      }
    } catch (Exception ex) {
      log.error("triggerRecovery failed", ex);
    } finally {
      try {
        if (in != null) {
          in.close();
        }

        if (out != null) {
          out.close();
        }

        sckt.close();
      } catch (Exception e) {
      }
    }
  }

}
