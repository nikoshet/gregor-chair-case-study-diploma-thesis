# Alpine base image v.3.10 that contains python 3.5.7
FROM python:3.5.7-alpine3.10
#FROM arm32v7/python:3.8-rc-alpine3.10
#FROM arm32v6/python:3.5-alpine

LABEL maintainer="GOCAS"

# Copy files
COPY . /WorkerTask3
# Set working directory
WORKDIR /WorkerTask3

# Remove cache,tmp files and update
RUN rm -rf /var/cache/apk/* && \
    rm -rf /tmp/* && \
    apk update 

# Final command
ENTRYPOINT ["python3","main.py"]











