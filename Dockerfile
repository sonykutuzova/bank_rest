# Используем официальный образ Java 17
FROM openjdk:17-jdk-slim

# Создаем директорию для приложения
WORKDIR /app

# Копируем JAR файл в контейнер
COPY target/bank-card-system-1.0.0.jar app.jar

# Открываем порт приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]