FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY target/memefy-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Xms64m -Xmx192m -XX:MaxMetaspaceSize=96m -Xss512k -XX:+UseG1GC -XX:+UseStringDeduplication"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]