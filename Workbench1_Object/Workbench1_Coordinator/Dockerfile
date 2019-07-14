# FROM maven:alpine
FROM ubuntu:16.04
LABEL MAINTAINER GOCAS

ENV JAVA_HOME                /usr/lib/jvm/java-8-openjdk-amd64/
ENV LANG            	     en_US.UTF-8
ENV LC_ALL          	     en_US.UTF-8
ENV W1_COAP_SERVER           192.168.0.40


COPY . /Workbench1Coordinator
WORKDIR /Workbench1Coordinator

RUN apt-get update && \
#  apt-get install -y --no-install-recommends locales && \
#  locale-gen ${LANG} && dpkg-reconfigure locales && \
  apt-get --purge remove openjdk* && \
  apt-get update && \
  apt-get install -y iputils-ping && \
  apt-get install -y openjdk-8-jdk maven && \
  apt-get clean all && \
  export JAVA_HOME=${JAVA_HOME} && \
  export PATH=$JAVA_HOME/jre/bin:$PATH && \
  mvn clean install -DskipTests



ENTRYPOINT ["sh","-c","java -jar target/Workbench1_Coordinator-0.0.1-SNAPSHOT.jar ${W1_COAP_SERVER}"]


