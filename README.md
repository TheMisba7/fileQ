# FileQ - Lightweight File Queue Service

**FileQ** is a lightweight, topic-based file queue system built with **Spring Boot**.  
It enables **producers** to upload files to named topics and **consumers** to pull, process, and complete them via simple REST APIs.

This is ideal for workflows where files need to be processed asynchronously in a **queue-like** manner without a heavy message broker.

---

## 🚀 Features
- **Topic-based storage** — organize files by topics.
- **Push & Pull** — producers push files, consumers pull the next available file.
- **File download** — download files in their original form.
- **Mark complete** — Mark files as completed after processing.
- **Retention cleanup** — optional scheduled cleanup for files older than X days.
- **Simple REST API** — integrate with any system or language.

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/consumer/pull?topic={topic}` | Pull the next file from a topic queue. |
| `POST` | `/api/consumer/upload` | Upload (push) a file into a topic. |
| `GET` | `/api/consumer/download/{topicItemId}` | Download a file by its ID. |
| `POST` | `/api/consumer/complete` | Mark a file as processed/completed. |
