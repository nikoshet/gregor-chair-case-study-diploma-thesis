#FROM frolvlad/alpine-python3:latest
FROM arm32v6/python:3.5-alpine
COPY . /Workbench1ControllerMs
WORKDIR /Workbench1ControllerMs
# RUN pip install -r requirements.txt
RUN apk add --no-cache gcc musl-dev &&\
pip install --no-cache-dir rpi.gpio
#pip install RPi.GPIO
ENTRYPOINT ["python3","main.py"]

