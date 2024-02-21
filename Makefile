include .env
export

clean:
	./mvnw clean

build:
	./mvnw package

build-fast:
	./mvnw clean package -Dmaven.test.skip

run:
	${JAVA_HOME}/bin/java -jar ./app-console/target/monitoring-app-console-1.0.0.jar

boot:
	${JAVA_HOME}/bin/java -jar ./app-springboot/webapp/target/monitoring-app-springboot-webapp-1.0.0.jar
