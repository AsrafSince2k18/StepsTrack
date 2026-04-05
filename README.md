# 🚶‍♂️ Real-Time Steps Tracking Application

A real-time Steps Tracking Android application built using modern Android architecture and a scalable Spring Boot backend. The app tracks user steps, live location, and visualizes movement on Google Maps with dynamic polyline updates.

## ✨ Features

* 📍 Real-time steps tracking
* 🗺️ Live location tracking using Google Maps SDK
* 🎯 Dynamic polyline coloring based on movement
* 🧭 Marker bearing rotation for direction tracking
* 🔐 JWT Authentication (Access + Refresh Token)
* 🔄 Secure token lifecycle management
* 🧩 Multi-module Clean Architecture
* 💉 Dependency Injection using Koin
* ⚙️ Foreground Service for continuous tracking
* 📡 Fused location provider integration
* 🌐 Connectivity monitoring
* 🐳 Dockerized Spring Boot backend
* 🔒 HTTPS-enabled cloud deployment

## 🏗️ Tech Stack

### Android

* Kotlin
* Jetpack Compose
* Clean Architecture
* Koin Dependency Injection
* Google Maps SDK
* Fused Location Provider
* Foreground Service

### Backend

* Spring Boot (Kotlin)
* MongoDB
* JWT Authentication
* REST API

### DevOps

* Docker
* Cloud Deployment
* HTTPS Configuration

## 📱 Architecture

* Multi-module setup
* MVVM Pattern
* Repository Pattern
* UseCases / Domain layer separation
* DI using Koin

## 🔐 Authentication Flow

* User Login
* Access Token issued
* Refresh Token stored securely
* Access token expiry handled
* Refresh token used to generate new access token

## 🚀 Backend Deployment

* Dockerized Spring Boot application
* Deployed to cloud server
* HTTPS enabled for secure communication

## 📸 App Capabilities

* Background step tracking
* Real-time map updates
* Route visualization
* Accurate movement direction
* Robust connectivity handling

## 📂 Project Structure

```
app/
core/
data/
domain/
di/
tracking/
auth/
```

## 👨‍💻 Author

Mohamed Asraf Ali  
Android Developer | Kotlin | Spring Boot
