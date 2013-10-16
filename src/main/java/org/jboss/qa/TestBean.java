package org.jboss.qa;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class TestBean {
  @Resource(lookup = "java:jboss/xa-datasources/TestDS")
  DataSource ds;

  public void go() {
    try {
      ds.getConnection();
      System.out.println("Go");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}