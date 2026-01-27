# Инструкция по запуску проекта в VS Code

## Предварительные требования

1. **Java Development Kit (JDK) 8 или выше**
   - Проверка: `java -version`
   - Установка на macOS: `brew install openjdk@17`

2. **Android SDK**
   - Установите Android Studio (проще всего) или standalone Android SDK
   - После установки Android Studio, SDK обычно находится в: `~/Library/Android/sdk`

3. **Настройка переменных окружения**
   
   Добавьте в файл `~/.zshrc`:
   ```bash
   export ANDROID_HOME=$HOME/Library/Android/sdk
   export PATH=$PATH:$ANDROID_HOME/emulator
   export PATH=$PATH:$ANDROID_HOME/platform-tools
   export PATH=$PATH:$ANDROID_HOME/tools
   export PATH=$PATH:$ANDROID_HOME/tools/bin
   ```
   
   Затем выполните: `source ~/.zshrc`

## Способ 1: Использование Android Studio (РЕКОМЕНДУЕТСЯ)

Это самый простой способ для разработки Android-приложений:

1. Скачайте и установите [Android Studio](https://developer.android.com/studio)
2. Откройте Android Studio
3. Выберите `File → Open` и откройте папку проекта `lab1`
4. Дождитесь синхронизации Gradle (первый раз может занять несколько минут)
5. Создайте виртуальное устройство (эмулятор):
   - `Tools → Device Manager → Create Device`
   - Выберите устройство (например, Pixel 6)
   - Выберите образ системы (например, Android 14)
6. Нажмите зеленую кнопку ▶️ `Run` или `Shift + F10`

## Способ 2: Командная строка из VS Code

### Шаг 1: Сборка проекта

Откройте терминал в VS Code (`` Ctrl + ` ``) и выполните:

```bash
cd "/Users/noplana/Desktop/ /Mobile/4rd/моб_разр/lab1"
./gradlew assembleDebug
```

Первая сборка займет время, так как Gradle скачает все зависимости.

### Шаг 2: Запуск эмулятора

#### Вариант А: Через Android Studio
1. Откройте Android Studio
2. `Tools → Device Manager`
3. Запустите нужное виртуальное устройство

#### Вариант Б: Через командную строку
```bash
# Посмотреть список доступных AVD
emulator -list-avds

# Запустить конкретный AVD (замените имя на свое)
emulator -avd Pixel_6_API_34 &
```

### Шаг 3: Установка приложения

После того как эмулятор запустится:

```bash
# Проверить подключение устройства
adb devices

# Установить приложение
adb install app/build/outputs/apk/debug/app-debug.apk

# Или переустановить (если уже установлено)
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Шаг 4: Запуск приложения

```bash
adb shell am start -n com.example.messenger/.MainActivity
```

## Способ 3: Использование физического устройства

1. Включите режим разработчика на Android-устройстве:
   - `Настройки → О телефоне → Номер сборки` (нажать 7 раз)
   
2. Включите отладку по USB:
   - `Настройки → Для разработчиков → Отладка по USB`
   
3. Подключите устройство к компьютеру через USB

4. Проверьте подключение:
   ```bash
   adb devices
   ```
   
5. Установите приложение:
   ```bash
   ./gradlew installDebug
   ```

## Просмотр логов

Чтобы увидеть логи жизненного цикла:

```bash
adb logcat -s MainActivity:D NewsFeedFragment:D ProfileFragment:D SettingsFragment:D
```

Или все логи приложения:
```bash
adb logcat | grep "com.example.messenger"
```

## Полезные команды Gradle

```bash
# Очистка проекта
./gradlew clean

# Сборка debug версии
./gradlew assembleDebug

# Сборка release версии
./gradlew assembleRelease

# Запуск тестов
./gradlew test

# Установка приложения на устройство
./gradlew installDebug

# Полная пересборка
./gradlew clean assembleDebug
```

## Расширения VS Code для Android (опционально)

Установите следующие расширения для удобной работы:

1. **Kotlin** (от fwcd) - поддержка языка Kotlin
2. **Android iOS Emulator** (от DiemasMichiels) - быстрый запуск эмуляторов
3. **Gradle for Java** (от Microsoft) - поддержка Gradle
4. **XML** (от Red Hat) - подсветка синтаксиса XML

## Решение проблем

### Ошибка "ANDROID_HOME not set"
```bash
echo $ANDROID_HOME
# Если пусто, настройте переменные окружения (см. выше)
```

### Ошибка "Permission denied: ./gradlew"
```bash
chmod +x gradlew
```

### Ошибка "SDK location not found"
Создайте файл `local.properties`:
```bash
echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
```

### Эмулятор не запускается
Проверьте, включена ли виртуализация:
```bash
sysctl -a | grep machdep.cpu.features
# Должен быть VMX в списке
```

## Горячие клавиши (если используете Android Studio)

- `Shift + F10` - запуск приложения
- `Ctrl + F9` - сборка проекта
- `Shift + F9` - запуск в режиме отладки
- `Alt + F12` - открыть терминал
- `Cmd + Shift + A` - поиск действий

## Дополнительно

После успешной сборки APK будет находиться по пути:
```
app/build/outputs/apk/debug/app-debug.apk
```

Вы можете скопировать его для загрузки в Git:
```bash
cp app/build/outputs/apk/debug/app-debug.apk app-debug.apk
```
