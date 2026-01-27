# Быстрый старт 🚀

## Для тех, кто хочет просто запустить приложение

### ✅ Способ 1: Android Studio (САМЫЙ ПРОСТОЙ)

1. Установите [Android Studio](https://developer.android.com/studio)
2. Откройте проект: `File → Open → выберите папку lab1`
3. Дождитесь синхронизации Gradle (занимает 2-5 минут)
4. Нажмите зеленую кнопку ▶️ `Run`

**Готово!** Приложение запустится автоматически.

---

### 🔨 Способ 2: Сборка через терминал VS Code

1. **Откройте терминал** в VS Code (`` Ctrl + ` ``)

2. **Перейдите в папку проекта:**
   ```bash
   cd "/Users/noplana/Desktop/ /Mobile/4rd/моб_разр/lab1"
   ```

3. **Соберите проект:**
   ```bash
   ./gradlew assembleDebug
   ```
   *Первая сборка займет 5-10 минут (скачиваются зависимости)*

4. **Запустите эмулятор:**
   - Вариант А: Через Android Studio → `Tools → Device Manager` → запустите устройство
   - Вариант Б: Через терминал (если есть AVD):
     ```bash
     emulator -list-avds  # посмотреть список
     emulator -avd <имя_устройства> &  # запустить
     ```

5. **Установите приложение:**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

6. **Запустите приложение:**
   ```bash
   adb shell am start -n com.example.messenger/.MainActivity
   ```

---

### 📱 Способ 3: Установка на физическое устройство

1. **Включите режим разработчика** на Android:
   - `Настройки → О телефоне → Номер сборки` (тапните 7 раз)

2. **Включите отладку по USB:**
   - `Настройки → Для разработчиков → Отладка по USB`

3. **Подключите устройство к компьютеру**

4. **Установите приложение:**
   ```bash
   cd "/Users/noplana/Desktop/ /Mobile/4rd/моб_разр/lab1"
   ./gradlew installDebug
   ```

---

## 📋 Просмотр логов жизненного цикла

После запуска приложения выполните:

```bash
adb logcat -s MainActivity:D NewsFeedFragment:D ProfileFragment:D SettingsFragment:D
```

Вы увидите логи вроде:
```
D/MainActivity: onCreate: MainActivity создана
D/NewsFeedFragment: onCreateView: NewsFeedFragment создание View
...
```

---

## ❓ Проблемы?

### "SDK location not found"
Создайте файл `local.properties`:
```bash
echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
```

### "Permission denied: ./gradlew"
```bash
chmod +x gradlew
```

### Нет Android SDK
Установите Android Studio - SDK установится автоматически.

---

## 📚 Подробные инструкции

Смотрите файл `SETUP_VSCODE.md` для детальных объяснений.

---

## 🎯 Что делает приложение?

- **Новости** - заглушка с иконкой
- **Профиль** - информация о пользователе (имя, email, телефон)
- **Настройки** - переключатель темной темы (работает и сохраняется!)

Навигация между экранами через нижнюю панель (Bottom Navigation).

---

**Удачи! 🎉**
