# Base Alpine Linux based image with OpenJDK JRE only
FROM ubuntu:18.04
RUN apt update && apt install openjdk-8-jre-headless -y
# copy application WAR (with libraries inside)
COPY target/universal/etml-1.0-SNAPSHOT/ /
# specify default command
CMD ["/bin/etml"]