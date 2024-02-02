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
