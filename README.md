# Duodingo - Приложение для изучения английских слов

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-brightgreen)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Duodingo - это мобильное приложение для изучения английских слов с использованием современных технологий Android разработки.

## 📱 Основные функции

- 🗂️ Изучение слов по темам (Животные, Еда, Дом и др.)
- 🧩 Упражнения на сопоставление слов
- 📅 Ежедневное слово для изучения
- 🔍 Переводчик английских слов
- 🌙 Поддержка светлой и темной темы

## 🛠️ Технологический стек

- **Язык**: Kotlin
- **UI**: Jetpack Compose
- **Архитектура**: MVVM
- **DI**: Hilt
- **Асинхронность**: Kotlin Coroutines
- **Сетевые запросы**: OkHttp
- **Тестирование**: JUnit, Mockito, Compose Testing

## 📦 Структура проекта
duodingo/app/src/
- main/ - Основной код приложения
- test/ - Unit тесты
- androidTest/ - Инструментальные тесты
-    build.gradle.kts - Конфигурация сборки
-    settings.gradle.kts - Настройки проекта

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/yourusername/duodingo.git
2. Откройте проект в Android Studio

3. Запустите приложение на эмуляторе или устройстве

Проект включает unit и UI тесты:

bash
# Запуск unit тестов
./gradlew testDebugUnitTest

# Запуск инструментальных тестов
./gradlew connectedDebugAndroidTest
