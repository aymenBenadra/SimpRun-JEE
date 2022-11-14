FROM quay.io/wildfly/wildfly as dev
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

FROM dev as prod
ADD ./target/SimpRun-JEE-1.0.war /opt/jboss/wildfly/standalone/deployments/