FROM amazoncorretto:11
COPY target/*.jar appStatsService.jar
ENTRYPOINT ["java","-jar","/appStatsService.jar"]
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://stats-db:5432/stats_service
ENV POSTGRES_USER=statsUser
ENV POSTGRES_PASSWORD=p#12H8sd