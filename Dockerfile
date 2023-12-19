FROM openjdk:11-jdk

WORKDIR /app

COPY . /app

RUN javac -cp .:/app/libraries/* -d /app/bin /app/src/components/*.java /app/src/test_main/*.java

CMD ["java", "-cp", "/app/bin:/app/libraries/*", "test_main.test_main", "/app/accounts.xml", "/app/flows.json"]