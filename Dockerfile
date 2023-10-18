FROM openjdk:11-jdk
COPY ikk.jar ikk.jar
ENTRYPOINT [ "java","-Xms128m","-Xmx256m","-jar","-Dspring.profiles.active=dev","/ikk.jar" ]
