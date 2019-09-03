#FROM raspbian/stretch:latest
FROM ubuntu:16.04
#FROM multiarch/ubuntu-core:x86_64-xenial
#FROM multiarch/ubuntu-core:x86_64-bionic
LABEL maintainer="GOCAS"

#ENV JAVA_HOME                /usr/lib/jvm/java-1.8.0-openjdk-armhf
ENV JAVA_HOME                /usr/lib/jvm/java-8-openjdk-amd64/
ENV LANG            	     en_US.UTF-8
ENV TZ			     Europe/Athens
ENV W1_COAP_SERVER           coap://10.42.77.144:8563
ENV W2_COAP_SERVER           coap://10.42.77.144:8565
			
		
COPY . /Robot1Coordinator
WORKDIR /Robot1Coordinator

#RUN apt-get update && \
#RUN  apt-get install -y --no-install-recommends locales
RUN export LANGUAGE=${LANG} && export LANG=${LANG} #&& export LC_ALL=${LANG} && locale-gen ${LANG}
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone
RUN  apt-get --purge remove openjdk*
RUN  apt-get update
RUN  apt-get install -y \
     iputils-ping \
     openjdk-8-jdk \
     maven
RUN  apt-get clean all
RUN  export JAVA_HOME=${JAVA_HOME}
RUN  export PATH=$JAVA_HOME/jre/bin:$PATH
RUN  mvn install:install-file -Dfile=GenericStateMachine-1.0-SNAPSHOT.jar -DgroupId=uml4iot -DartifactId=GenericStateMachine -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
RUN  mvn clean install -DskipTests

ENTRYPOINT ["sh","-c","java -jar target/Robot1_Coordinator-0.0.1-SNAPSHOT.jar ${W1_COAP_SERVER} ${W2_COAP_SERVER}"]

