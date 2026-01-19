# 🔍 Android Image & String AutoSearch Demo

> **專案概述**：
>這是一個 Android 原生開發的技術展示專案 (Technical Demo)，重點實作 **即時搜尋建議 (Auto Complete)** 與 **動態 UI 控制**。
>
>本專案模擬了商業 App 常見的搜尋邏輯，並採用標準的 **MVC 架構** 與 **Adapter Pattern**，確保資料與介面的解耦。

---

## 📱 功能演示 (Features)

### 1. 智慧搜尋 (Smart Auto-Complete)
* **即時篩選**：使用者輸入關鍵字（如 "Taipei"）時，系統利用 `TextWatcher` 機制即時比對資料庫，並列出候選結果。
* **客製化列表**：搜尋結果的下拉選單經過 UI 客製化（背景色、字體），非 Android 預設樣式。

### 2. 圖片瀏覽與特效 (Image Viewer)
* **多媒體控制**：實作圖片的「上一張/下一張」切換邏輯。
* **透明度調整**：透過 `SeekBar` 動態調整圖片的 Alpha 值 (0-100%)，展示對 View 屬性的即時操作能力。

---

## 📸 執行截圖 (Screenshots)

| 主畫面與圖片控制 | 搜尋功能演示 |
|:---:|:---:|
| *(請在此處放入你的主畫面截圖，例如 main_view.png)* | *(請在此處放入搜尋時跳出選單的截圖，例如 search_demo.png)* |
| **圖 1：透明度調整與圖片切換** | **圖 2：輸入 'T' 即顯示相關候選字** |

---

## 🛠 技術架構與程式碼亮點 (Technical Highlights)

此專案重點展示了以下 Android 開發核心觀念：

### 1. Adapter 模式應用 (Adapter Pattern)
為了高效處理列表資料，我使用了 Android SDK 標準的 `ArrayAdapter` 搭配 `AutoCompleteTextView`。這體現了我對 Android 列表元件機制的理解。

```java
// 核心程式碼片段 (A108222040_AutoSearch.java)
// 將資料源 (autoString) 透過 Adapter 轉換並綁定到 UI (listitem)
String [] autoString = getResources().getStringArray(R.array.PlaceArray);
ArrayAdapter<String> ap = new ArrayAdapter<>(this, R.layout.listitem, autoString);

// 綁定 Adapter，實現自動篩選邏輯
autov.setAdapter(ap);
