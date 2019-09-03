#FROM frolvlad/alpine-python3:latest
#FROM arm32v6/python:3.5-alpine
# Alpine base image v.3.10 that contains python 3.5.7
FROM python:3.5.7-alpine3.10
#FROM multiarch/ubuntu-core:armhf-xenial

LABEL maintainer="GOCAS"

# Environment Variables
ENV DEVICE                   RPI
#ENV DEVICE                   LAPTOP

# Copy files
COPY . /Workbench1ControllerMs
# Set working directory
WORKDIR /Workbench1ControllerMs

# Remove cache,tmp files and update
#RUN rm -rf /var/cache/apk/* && \
#    rm -rf /tmp/* && \
#    apt-get update && \
# Install necessary libs for rpi.gpio
#    apt-get install -y gcc musl-dev python-pip && \
# Install rpi.gpio  to have access to gpio pins from docker
#    pip install --no-cache-dir rpi.gpio && \
# Remove libs
#    apt-get remove -y gcc musl-dev

# Remove cache,tmp files and update
RUN rm -rf /var/cache/apk/* && \
    rm -rf /tmp/* && \
    apk update && \
# Install necessary libs for rpi.gpio
    apk add --no-cache gcc musl-dev && \
# Install rpi.gpio  to have access to gpio pins from docker
    pip install --no-cache-dir rpi.gpio && \
# Remove libs
    apk del remove gcc musl-dev

# Final command
ENTRYPOINT ["sh","-c","python3 main.py ${DEVICE}"]


