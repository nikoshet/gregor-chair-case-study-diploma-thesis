#FROM raspbian/stretch:latest
#FROM ubuntu:16.04
#FROM arm32v6/alpine
FROM ubuntu:xenial
LABEL MAINTAINER GOCAS
  
ENV JAVA_HOME                /usr/lib/jvm/java-1.8.0-openjdk-armhf
#ENV JAVA_HOME                /usr/lib/jvm/java-8-openjdk-amd64/
ENV LANG            	     en_US.UTF-8
ENV LC_ALL          	     en_US.UTF-8
ENV W1_COAP_SERVER           coap://192.168.10.122:8563
ENV W2_COAP_SERVER           coap://192.168.10.125:8565
			
		
COPY . /Robot2Coordinator
WORKDIR /Robot2Coordinator

RUN apt-get update && \
#  apt-get install -y --no-install-recommends locales && \
#  locale-gen ${LANG} && dpkg-reconfigure locales && \
#  apt-get dist-upgrade -y && \
  apt-get --purge remove openjdk* && \
  apt-get update && \
  apt-get install -y iputils-ping && \
  apt-get install -y openjdk-8-jdk maven && \
  apt-get clean all && \
  export JAVA_HOME=${JAVA_HOME} && \
  export PATH=$JAVA_HOME/jre/bin:$PATH && \
  mvn install:install-file -Dfile=GenericStateMachine-1.0-SNAPSHOT.jar -DgroupId=uml4iot -DartifactId=GenericStateMachine -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true && \
  mvn clean install -DskipTests 


ENTRYPOINT ["sh","-c","java -jar target/Robot2_Coordinator-0.0.1-SNAPSHOT.jar ${W1_COAP_SERVER} ${W2_COAP_SERVER}"]

