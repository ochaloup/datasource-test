Test of datasource 
========================================================

This is just a skeleton of a test to connect to a XA datasource.

Currently it could be maybe used to get a solution to pass properties from maven -> maven surefire -> arquillian -> jboss server. Nothing more.

To try it:
export JBOSS_HOME=<path_to_jboss_eap_home>
mvn clean test -Djdbc.url=<jdbc-url> -Djdbc.username=<username> -Djdbc.password=<password> -Ddriver=<absolute_path_to_driver_jar>
