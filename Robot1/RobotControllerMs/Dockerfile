# Alpine base image v.3.10 that contains python 3.5.7
FROM python:3.5.7-alpine3.10
#FROM arm32v7/python:3.8-rc-alpine3.10
#FROM arm32v6/python:3.5-alpine

LABEL maintainer="GOCAS"

# Environment Variables
#ENV DEVICE                   RPI
ENV DEVICE                   LAPTOP

# Copy files
COPY . /Robot1ControllerMs
# Set working directory
WORKDIR /Robot1ControllerMs

# Remove cache,tmp files and update
RUN rm -rf /var/cache/apk/* && \
    rm -rf /tmp/* && \
    apk update && \
# Install necessary libs for rpi.gpio
    apk add --no-cache gcc musl-dev && \
# Install rpi.gpio  to have access to gpio pins from docker
    pip install --no-cache-dir rpi.gpio && \
# Remove libs
    apk del gcc musl-dev

# Final command
ENTRYPOINT ["sh","-c","python3 main.py ${DEVICE}"]

