Test of datasource 
========================================================

This is just a skeleton of a test to connect to a XA datasource.

Currently it could be maybe used to get a solution to pass properties from maven -> maven surefire -> arquillian -> jboss server. Nothing more.

To try it:
export JBOSS_HOME=<path_to_jboss_eap_home>
mvn clean test -Djdbc.url=<jdbc-url> -Djdbc.username=<username> -Djdbc.password=<password> -Ddriver=<absolute_path_to_driver_jar>
or instead of clean to get cleaned the $JBOSS_HOME/standalone/deployments folder as well
mvn test -Djdbc.url=<jdbc-url> -Djdbc.username=<username> -Djdbc.password=<password> -Ddriver=<absolute_path_to_driver_jar> -Ddelete

when you use the xa-ds.xml you need to specify XA class as well
mvn test -Djdbc.url=<jdbc-url> -Djdbc.username=<username> -Djdbc.password=<password> -Ddriver=<absolute_path_to_driver_jar> -Djdbc.xa.class
