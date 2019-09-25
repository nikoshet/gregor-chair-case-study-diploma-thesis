# Debian 9(Stretch) base image
FROM debian:9-slim

LABEL maintainer="GOCAS"

# Environment Variables
ENV JAVA_HOME                /usr/lib/jvm/java-1.8.0-openjdk-armhf
#ENV JAVA_HOME                /usr/lib/jvm/java-8-openjdk-amd64/
ENV LANG                     en_US.UTF-8
ENV TZ                       Europe/Athens
ENV W2_COAP_SERVER           192.168.5.50
# Copy files
COPY target/Workbench2_Coordinator-0.0.1-SNAPSHOT.jar /Workbench2Coordinator/Workbench2_Coordinator-0.0.1-SNAPSHOT.jar
# Set working directory
WORKDIR /Workbench2Coordinator

# Set locales,timezone
RUN export LANGUAGE=${LANG} && export LANG=${LANG} && \ 
     ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone && \
# Bug fix for openjdk-8 on Debian
     mkdir -p /usr/share/man/man1 && \
# Updates and Installations
     apt-get update && \
     apt-get install -y \
        openjdk-8-jdk-headless \
        git \
        sudo \
        make \
        gcc && \
     git clone https://github.com/wiringpi/wiringpi && cd wiringpi && sudo ./build && cd .. && \
     apt-get remove --purge -y git make gcc && \
     rm -rf wiringpi && \
     apt-get clean all && apt autoremove -y && \
# Set java paths
     export JAVA_HOME=${JAVA_HOME} && \
     export PATH=$JAVA_HOME/jre/bin:$PATH

# Final command
ENTRYPOINT ["sh","-c","java -jar Workbench2_Coordinator-0.0.1-SNAPSHOT.jar ${W2_COAP_SERVER}"]


