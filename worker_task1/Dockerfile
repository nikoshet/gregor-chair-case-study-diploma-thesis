#FROM frolvlad/alpine-python3:latest
FROM arm32v6/python:3.5-alpine
LABEL MAINTAINER GOCAS

COPY . /WorkerTask1
WORKDIR /WorkerTask1

# RUN pip install -r requirements.txt
ENTRYPOINT ["python3","main.py"]
