FROM openjdk:11-jre-slim

ENV VERTICLE_FILE poison-pill-fat.jar

# Set vertx option
ENV VERTX_OPTIONS "-ha"

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

# Copy your fat jar to the container
COPY ./verticles $VERTICLE_HOME

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $VERTICLE_FILE $VERTX_OPTIONS"]