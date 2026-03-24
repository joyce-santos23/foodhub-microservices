# ---------- BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# 👇 copia só poms primeiro (CACHE DE DEPENDÊNCIA)
COPY pom.xml .
COPY foodhub-contracts/pom.xml foodhub-contracts/pom.xml
COPY auth-service/pom.xml auth-service/pom.xml
COPY user-service/pom.xml user-service/pom.xml
COPY restaurant-service/pom.xml restaurant-service/pom.xml
COPY order-service/pom.xml order-service/pom.xml
COPY payment-service/pom.xml payment-service/pom.xml

# baixa dependências (fica cacheado)
RUN mvn -B -q -e -DskipTests dependency:go-offline

# 👇 agora copia o resto
COPY . .

# builda só o módulo necessário
RUN mvn clean package -pl auth-service -am -DskipTests

# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/auth-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]