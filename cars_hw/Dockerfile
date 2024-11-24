FROM openjdk:20
WORKDIR /app
ADD target/cars_hw-1.0-SNAPSHOT.jar lab4.jar

COPY run-tests.sh run-tests.sh
RUN chmod +x run-tests.sh

ENTRYPOINT ["./run-tests.sh"]