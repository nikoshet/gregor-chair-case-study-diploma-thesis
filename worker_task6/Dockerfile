#FROM frolvlad/alpine-python3:latest
FROM arm32v6/python:3.5-alpine
LABEL MAINTAINER GOCAS

COPY . /WorkerTask6
WORKDIR /WorkerTask6

# RUN pip install -r requirements.txt
ENTRYPOINT ["python3","main.py"]
