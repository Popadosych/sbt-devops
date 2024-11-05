FROM python:3.9

RUN apt-get update && apt-get install -y python3-pip

RUN python3 -m venv /opt/venv

ENV PATH="/opt/venv/bin:$PATH"

EXPOSE 80