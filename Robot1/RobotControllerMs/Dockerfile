FROM frolvlad/alpine-python3:latest
#FROM arm32v7/python:2.7.13-jessie
#FROM arm32v7/python:3.5.7-stretch????
#FROM arm32v6/python:3.5-alpine
LABEL MAINTAINER GOCAS

#ENV DEVICE                   RPI
ENV DEVICE                   LAPTOP

COPY . /RobotControllerMs
WORKDIR /RobotControllerMs

# RUN pip install -r requirements.txt
# RUN apk add --no-cache gcc musl-dev &&\
# pip install --no-cache-dir rpi.gpio
#pip install RPi.GPIO
ENTRYPOINT ["sh","-c","python3 main.py ${DEVICE}"]
