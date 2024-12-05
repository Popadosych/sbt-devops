FROM python:3.10-slim
WORKDIR /app
COPY requirements.txt requirements.txt
COPY app.py app.py
RUN pip install -r requirements.txt
EXPOSE 8080
CMD ["python", "app.py"]