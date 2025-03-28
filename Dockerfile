# Используем официальный Maven-образ для сборки приложения
FROM maven:3.8.6-eclipse-temurin-8 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем все файлы проекта
COPY . .

# Сборка проекта с помощью Maven
RUN mvn clean package -DskipTests

# Используем минимальный JRE-образ для запуска приложения
FROM openjdk:8-jre-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar из предыдущего builder-этапа
COPY --from=build /app/target/practice-0.0.1.jar app.jar

# Указываем порт, который будет пробрасываться наружу
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
