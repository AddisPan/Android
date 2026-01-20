# 🔍 Android Practice: Image Viewer & AutoSearch

> **專案簡介 (Introduction)**
> 
> 這是我在大學時期練習 Android 原生開發 (Java) 的專案。
> 主要目的是熟悉 Android 基礎 UI 元件的使用，包含 **SeekBar 監聽**、**ImageView 屬性控制** 以及 **AutoCompleteTextView 資料綁定**。

---

## 📱 功能展示 (Features)

### 1. 圖片瀏覽與透明度控制 (Image & Alpha)
* **功能描述**：使用者可以切換圖片，並透過拖動 SeekBar 來改變圖片的透明度。
* **技術重點**：
    * 使用 `ImageView` 顯示圖片資源。
    * 實作 `OnSeekBarChangeListener`，將 SeekBar 的數值 (0-255) 即時反映到圖片的 Alpha 屬性上。

### 2. 關鍵字搜尋建議 (Auto Complete)
* **功能描述**：模擬搜尋功能。當使用者輸入文字（如 "T"），系統會自動列出符合的地點建議（如 "Taipei"）。
* **技術重點**：
    * 使用 `AutoCompleteTextView` 元件。
    * **關鍵觀念**：使用 `ArrayAdapter` 作為資料與介面之間的橋樑，將 `strings.xml` 裡的字串陣列綁定到下拉選單中。

---

## 🛠 程式碼邏輯 (Code Snippets)

### 核心觀念：Adapter (適配器)
這段程式碼展示了如何將資料來源 (Data) 連結到畫面 (View)：

```java
// 1. 準備資料 (從 strings.xml 取得地名清單)
String[] places = getResources().getStringArray(R.array.PlaceArray);

// 2. 建立 Adapter (橋樑)
// 參數：Context, 下拉選單樣式(listitem), 資料陣列
ArrayAdapter<String> adapter = new ArrayAdapter<>(
    this, 
    R.layout.listitem, 
    places
);

// 3. 設定給 AutoCompleteTextView
autoCompleteView.setAdapter(adapter);
```

📂 專案結構
MainActivity.java: 處理圖片切換與 SeekBar 邏輯。

AutoSearchActivity.java: 處理搜尋與 Adapter 設定。

res/layout/listitem.xml: 客製化搜尋下拉選單的樣式（修改了字體與背景色）。

👤 開發者
潘凌徵
