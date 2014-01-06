Test of datasource 
========================================================

This is just a skeleton of a test to connect to a XA datasource.

Currently it could be maybe used to get a solution to pass properties from maven -> maven surefire -> arquillian -> jboss server. Nothing more.

To try it:
export JBOSS_HOME=&lt;path_to_jboss_eap_home&gt;<br/>
<pre>mvn clean test -Djdbc.url=&lt;jdbc-url&gt; -Djdbc.username=&lt;username&gt; -Djdbc.password=&lt;password&gt; -Ddriver=&lt;absolute_path_to_driver_jar&gt;</pre><br/>
or instead of clean to get cleaned the $JBOSS_HOME/standalone/deployments folder as well<br/>
<pre>
mvn test -Djdbc.url=&lt;jdbc-url&gt; -Djdbc.username=&lt;username&gt; -Djdbc.password=&lt;password&gt; -Ddriver=&lt;absolute_path_to_driver_jar&gt; -Ddelete
</pre>

when you use the xa-ds.xml you need to specify XA class as well<br/>
<pre>
mvn test -Djdbc.url=&lt;jdbc-url&gt; -Djdbc.username=&lt;username&gt; -Djdbc.password=&lt;password&gt; -Ddriver=&lt;absolute_path_to_driver_jar&gt; -Djdbc.xa.class=&lt;some_xa_class_implementation&gt;
</pre>

when you want to run specific test case
<pre>
mvn test -Djdbc.url=&lt;jdbc-url&gt; -Djdbc.username=&lt;username&gt; -Djdbc.password=&lt;password&gt; -Ddriver=&lt;absolute_path_to_driver_jar&gt; -Djdbc.xa.class=&lt;some_xa_class_implementation&gt; -Dtest=org.jboss.qa.SimpleJPATest
</pre>

<p>
Currently the plugin com.github.goldin:copy-maven-plugin does not work with maven 3.1 and later. Check the bug<br/>
https://github.com/evgeny-goldin/maven-plugins/issues/10
<br/><br/>
Changing maven distro<br/>
<pre>
export MAVEN_HOME=...
export PATH=...
</pre>
</p>
