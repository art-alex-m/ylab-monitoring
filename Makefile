include .env
export

PROJECT_DIRS = . ./app-springboot

clean:
	for POM in $$PROJECT_DIRS ; do \
    	./mvnw clean -f $$POM/pom.xml ; \
    done

build:
	for POM in $$PROJECT_DIRS ; do \
    	./mvnw package -f $$POM/pom.xml ; \
    done

build-fast:
	for POM in $$PROJECT_DIRS ; do \
		./mvnw clean install -Dmaven.test.skip -f $$POM/pom.xml ; \
	done

run:
	${JAVA_HOME}/bin/java -jar ./app-console/target/monitoring-app-console-1.0.0.jar

boot:
	${JAVA_HOME}/bin/java -jar ./app-springboot/webapp/target/monitoring-app-springboot-webapp-1.0.0.jar
