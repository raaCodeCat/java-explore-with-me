FROM amazoncorretto:11
COPY target/*.jar appMainService.jar
ENTRYPOINT ["java","-jar","/appMainService.jar"]
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://ewm-db:5432/ewm
ENV POSTGRES_USER=ewmUser
ENV POSTGRES_PASSWORD=f#57Hx9jz