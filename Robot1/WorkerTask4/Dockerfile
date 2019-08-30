FROM frolvlad/alpine-python3:latest
#FROM arm32v6/python:3.5-alpine
LABEL MAINTAINER GOCAS

COPY . /WorkerTask4
WORKDIR /WorkerTask4

# RUN pip install -r requirements.txt
ENTRYPOINT ["python3","main.py"]
