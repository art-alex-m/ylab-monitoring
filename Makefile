include .env
export

clean:
	./mvnw clean

build:
	./mvnw package

run:
	${JAVA_HOME}/bin/java -jar ./app-console/target/monitoring-app-console-1.0.0.jar
