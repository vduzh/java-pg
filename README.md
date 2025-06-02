# Java Projects

Коллекция Java проектов для изучения различных аспектов языка и библиотек.

## Запуск

### Основные команды Gradle

* `./gradlew compileJava compileTestJava`
* `./gradlew build` - собрать проект
* `./gradlew clean` - очистить собранные файлы
* `./gradlew test` - запустить все тесты
* `./gradlew test --tests "by.duzh.jse.*"` - запустить тесты конкретного пакета
* `./gradlew test --tests "by.duzh.jse.util.StringTokenizerTest"` - запустить конкретный тестовый класс
* `./gradlew test --tests "by.duzh.jse.util.StringTokenizerTest#testNextToken"` - запустить конкретный тест
* `./gradlew test --info` - запустить тесты с подробным выводом
* `./gradlew test --debug` - запустить тесты с отладочным выводом
* `./gradlew test --stacktrace` - запустить тесты с выводом стека ошибок
* `./gradlew test --scan` - запустить тесты с полным анализом сборки


* `./gradlew lib:test` - запустить тесты с полным анализом сборки


### Полезные опции

* `--warning-mode all` - показать все предупреждения
* `--stacktrace` - показать полный стек ошибок
* `--info` - показать подробную информацию о сборке
* `--debug` - показать отладочную информацию
* `--scan` - получить полный анализ сборки

### Очистка и перезапуск

Если возникают проблемы с запуском тестов или сборкой:

1. Остановить все процессы Java:
   ```bash
   taskkill /F /IM java.exe
   ```

2. Очистить проект и запустить тесты:
   ```bash
   ./gradlew clean test
   ```

### Docker

* `./gradlew build` - собрать проект в Docker
