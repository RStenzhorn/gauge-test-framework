FROM maven:3.8.1-openjdk-17

RUN mkdir -p /app

COPY manifest.json /app

WORKDIR /app

# Install gauge
RUN microdnf install -y unzip \
    && curl -Ssl https://downloads.gauge.org/stable | sh


RUN gauge install && gauge install screenshot

