# FROM openjdk:1.8-jdk
# COPY ikk.jar ikk.jar
# ENTRYPOINT [ "java","-Xms128m","-Xmx256m","-jar","-Dspring.profiles.active=local","/ikk.jar" ]
FROM openjdk:8-jdk
COPY target/ikk-2.0.0.jar ikk-2.0.0.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","/ikk-2.0.0.jar"]