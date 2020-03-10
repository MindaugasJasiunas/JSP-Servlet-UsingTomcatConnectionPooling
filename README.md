# JSP-Servlet-UsingTomcatConnectionPooling
JSP+Servlet example using Tomcat Connection Pooling (doesnt work with JBoss)</br>
<b>This is personal cheat sheet for TomCat connection pooling</b>
</br>For this to work you need to create database "hibernatetutdb" and using "postgre_students_table_creation.sql" script create db table "students", add some users.
</br>
</br>

<b>Student.java</b>  POJO </br>
<b>pom.xml</b> Maven file containing dependencies (Dependencies duplicated to web->WEB-INF->lib folder).</br>

<b>web/META-INF/context.xml</b> file contains information for TomCat connection pooling </br>
<b>src/main/java/Controller/Model/DBUtil.java</b> reads web->META-INF->context.xml file and returns dataSource from it.</br>
<b>src/main/java/Controller/Model/StudentDAO</b> gets dataSource from DBUtil.java and manipulates DB(SELECT,INSERT,DELETE)</br>
<b>src/main/java/Controller/StudentManagementServlet.java</b> connects to DB</br>
 
