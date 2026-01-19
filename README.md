# 🔍 Android Image & String AutoSearch Demo

## 📌 專案概述 (Overview)

這是一個 **Android 原生開發 (Native Android)** 的技術展示專案（Technical Demo），  
重點實作 **即時搜尋建議（Auto Complete）** 與 **動態 UI 控制**。

本專案模擬商業 App（如：股市查詢、商品檢索）中常見的搜尋邏輯，  
並透過 **Adapter Pattern** 確保資料層與 UI 層解耦，  
用以展示對 Android 基礎元件與架構概念的掌握程度。

---

## 📸 功能演示 (Screenshots)

| 主畫面與透明度控制 | 搜尋與自動完成 |
|:---:|:---:|
| <img src="demo1.png" width="300" alt="Main Activity Demo" /> | <img src="demo2.png" width="300" alt="Search Activity Demo" /> |
| 透過 SeekBar 動態調整圖片 Alpha 值，<br>並實作圖片切換邏輯 | 輸入關鍵字（如 `T`）即時觸發<br>AutoCompleteTextView 篩選 |

> ⚠️ 若圖片無法顯示，請確認圖片路徑是否正確，例如：  
> `images/demo1.png`、`images/demo2.png`

---

## 📱 核心功能 (Features)

### 1️⃣ 智慧搜尋（Smart Auto-Complete）

- **即時篩選邏輯**  
  使用者輸入關鍵字時，透過 `AutoCompleteTextView` 內建機制即時比對資料來源。

- **搜尋列表客製化**  
  使用自訂 `listitem.xml` 來設計下拉選單的背景色、字體大小與文字顏色，  
  避免使用原生預設樣式，提升 UI 可讀性。

---

### 2️⃣ 圖片瀏覽與特效（Ima
