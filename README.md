# 📸 ScreenSpy (Minecraft Forge 1.12.2)

**ScreenSpy** to mod klientowy dla Minecraft Forge 1.12.2, który automatycznie wykonuje zrzuty ekranu całego ekranu co 1 sekundę i usuwa je po określonym czasie. Idealny do debugowania, monitorowania lub... szpiegowania siebie 😎

---

## 🧰 Funkcje

- 📷 Screenshot co 1 sekundę (pełny ekran gry)
- 🗑️ Automatyczne usuwanie plików po określonym czasie (domyślnie 60 sekund)
- 🎛️ Przycisk w menu pauzy [ESC] do włączania/wyłączania działania moda
- 📁 Screenshoty zapisywane w `.minecraft/screenshots/screenspy/`
- ⚙️ Konfigurowalne przez `screenspy.cfg`

---

## 🔧 Konfiguracja (`screenspy.cfg`)

Plik zostanie utworzony automatycznie w folderze:
`<GameDir>/config/screenspy.cfg`

Dostępne opcje:

| Opcja              | Domyślna | Opis                                        |
|-------------------|----------|---------------------------------------------|
| `retentionSeconds`| `60`     | Czas (w sekundach), po którym screenshoty są usuwane |

---

## 📦 Wymagania

- Minecraft **1.12.2**
- Forge **1.12.2-14.23.5.2768** lub **nowszy** (np. 2860)

---
