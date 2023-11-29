FROM tomcat:latest
#RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
#RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
COPY target/ROOT.war /usr/local/tomcat/webapps/
#RUN mv /usr/local/tomcat/webapps/gs-maven-0.1.0 /usr/local/tomcat/webapps/ROOT
EXPOSE 8080
CMD ["catalina.sh", "run"]


#WORKDIR /usr/local/tomcat/webapps
#COPY target/gs-maven-0.1.0.jar .
#RUN mv gs-maven-0.1.0.jar ROOT.jar
#RUN rm -rf ROOT