 #local setup

 #profile
 export SPRING_PROFILES_ACTIVE=dev
 ##-- or in  --- : java -jar -Dspring.profiles.active=dev spring-boot-profiles-demo-0.0.1-SNAPSHOT.jar

 #docker compose
 docker compose -f docker-compose-dev.yml up -d

 #run jar
 java -jar target/spring-microservice-project-0.0.1-SNAPSHOT.jar
