# tomcatParallelStreamIssue
Code to reproduce an issue we are having Apache Tomcat, parallelStreams and JNDI

Create a test1 and test2.xml in the /usr/tomcat/conf/Catalina/localhost directory
Run maven package test and place the .war file in /usr/tomcat/webapps but call it test1.war and test2.war
 
 Be sure to change the pag and value accordingly
 
<Context path="/test2" swallowOutput="false" reloadable="false" override="true">
    <Environment name="testName" value="Test Instance 2"   type="java.lang.String"/>
</Context>

The URL for the apps will be:
http://localhost:8080/test1/test
and
http://localhost:8080/test2/test