# FROM maven:alpine
FROM ubuntu:16.04
LABEL MAINTAINER GOCAS

ENV JAVA_HOME       /usr/lib/jvm/java-8-openjdk-amd64/
ENV LANG            en_US.UTF-8
ENV LC_ALL          en_US.UTF-8


COPY . /w1
WORKDIR /w1

RUN apt-get update && \
  apt-get install -y --no-install-recommends locales && \
  locale-gen ${LANG} && dpkg-reconfigure locales && \
  apt-get dist-upgrade -y && \
  apt-get --purge remove openjdk* && \
  apt-get update && \
  apt-get install -y openjdk-8-jdk maven && \
  apt-get clean all && \
  export JAVA_HOME=${JAVA_HOME} && \
  export PATH=$JAVA_HOME/jre/bin:$PATH && \
  mvn install:install-file -Dfile=GenericStateMachine-1.0-SNAPSHOT.jar -DgroupId=uml4iot -DartifactId=GenericStateMachine -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true && \
  mvn clean install -DskipTests && \
  mvn package -DskipTests

ENTRYPOINT ["java","-jar","target/Workbench1_CPMS-0.0.1-SNAPSHOT.jar"]
