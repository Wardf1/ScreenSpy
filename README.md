# 🖼 ScreenSpy – Forge 1.12.2

Mod kliencki do Minecraft Forge 1.12.2, który **automatycznie wykonuje zrzuty ekranu co 1 sekundę** i zapisuje je do folderu `.minecraft/screenshots/screenspy/`.

## ✨ Funkcje

- 📸 Automatyczny screenshot co 1 sekundę (20 ticków).
- 💾 Zapisywanie plików do `./screenshots/screenspy/<data>-<godzina>.png`.
- 🗑️ Automatyczne czyszczenie starych zrzutów po określonym czasie (`retentionSeconds`).
- 🧹 Usuwanie zbyt starych plików (>30 minut) przy starcie launchera.
- 🧮 Kompresja PNG – mniejsze pliki, bez utraty jakości.
- 🎛️ Możliwość przełączania trybu (ON/OFF) klawiszem **F9**.
- 🧩 Konfigurowalne w `screenspy.cfg`.

## 🧷 Sterowanie

| Klawisz | Funkcja                      |
|--------:|------------------------------|
| `F9`    | Przełączenie nagrywania ON/OFF |

## ⚙️ Konfiguracja (`config/screenspy.cfg`)

```cfg
# Czas trzymania screenshotów w sekundach
retentionSeconds=1800
```

## 🗂 Lokalizacja zrzutów

```
.minecraft/
└── screenshots/
    └── screenspy/
        ├── 2025-06-13_21-55-00.png
        ├── ...
```

## 🧪 Wymagania

- Minecraft Forge `1.12.2-14.23.5.2768` lub wyżej.
- Java 8
- Tylko **klient** (nie działa po stronie serwera)

---

## 📖 Licencja

MIT © Wardf
