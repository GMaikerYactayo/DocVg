FROM jboss/wildfly
 
ADD target/DocVG.war /opt/jboss/wildfly/standalone/deployments/
 
EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]