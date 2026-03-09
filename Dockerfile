FROM open-liberty:full-java17-openj9

COPY --chown=1001:0 target/*.war /config/dropins/app.war

RUN configure.sh

EXPOSE 9080