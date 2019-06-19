FROM maven:alpine
WORKDIR /
COPY . .
#EXPOSE 8563:8563/udp
RUN mvn install:install-file -Dfile=GenericStateMachine-1.0-SNAPSHOT.jar -DgroupId=uml4iot -DartifactId=GenericStateMachine -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
RUN mvn clean install -DskipTests
RUN mvn package -DskipTests
ENTRYPOINT ["mvn","spring-boot:run"]
#ENTRYPOINT ["java","-jar","myjar.jar"]


