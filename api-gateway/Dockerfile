FROM eclipse-temurin:21-jdk-alpine

# Carpeta donde vivirá tu app en el contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]