FROM maven:3.8.5-openjdk-17

RUN microdnf install -y unzip \
    && curl -Ssl https://downloads.gauge.org/stable | sh

# Install gauge plugins
RUN gauge install java && \
    gauge install screenshot && \
    gauge install html-report

ENV PATH=$HOME/.gauge:$PATH