#FROM frolvlad/alpine-python3:latest
FROM arm32v6/python:3.5-alpine
COPY . /WorkerTask2
WORKDIR /WorkerTask2
# RUN pip install -r requirements.txt
ENTRYPOINT ["python3","main.py"]
