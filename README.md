ch Demo

> **專案概述 (Overview)**
> 
> 這是一個 Android 原生開發的技術展示專案 (Technical Demo)，重點實作 **即時搜尋建議 (Auto Complete)** 與 **動態 UI 控制**。
>
> 本專案模擬了商業 App (如股市查詢、商品檢索) 常見的搜尋邏輯，並採用標準的 **Adapter Pattern** 確保資料與介面的解耦，展示了對 Android 基礎元件的深度掌握。

---

## 📸 功能演示 (Screenshots)

| **1. 主畫面與透明度控制** | **2. 搜尋與自動完成** |
|:---:|:---:|
| <img src="demo1.png" width="300" alt="Main Activity Demo" /> | <img src="demo2.png" width="300" alt="Search Activity Demo" /> |
| *透過 SeekBar 動態調整圖片 Alpha 值，<br>並實作圖片切換邏輯。* | *輸入關鍵字 (如 "T") 即時觸發<br>AutoCompleteTextView 篩選。* |

---

## 📱 核心功能 (Features)

### 1. 智慧搜尋 (Smart Auto-Complete)
* **即時篩選 Logic**：使用者輸入關鍵字時，系統利用 `AutoCompleteTextView` 機制即時比對資料庫。
* **客製化列表**：透過 `listitem.xml` 客製化下拉選單的 UI（背景色、字體），優化原生單調的樣式。

### 2. 圖片瀏覽與特效 (Image Viewer)
* **多媒體互動**：實作 `ImageView` 的資源切換（上一張/下一張）。
* **屬性操作**：透過 `SeekBar` 監聽器動態改變圖片的 **Alpha (透明度)** 值，展示對 View 屬性的程式化控制。

---

## 🛠 技術架構與亮點 (Technical Highlights)

此專案重點展示了以下 Android 開發核心觀念，特別針對**資料綁定**與**架構分層**：

### 1. Adapter 模式應用 (Adapter Pattern)
為了高效處理列表資料，我使用了 Android SDK 標準的 `ArrayAdapter`。這體現了我對 Android 列表元件機制的理解——**不重複造輪子，而是利用系統優化過的元件**。

```java
// 核心程式碼片段 (A108222040_AutoSearch.java)
// 1. 取得資料源 (Model)
String [] autoString = getResources().getStringArray(R.array.PlaceArray);

// 2. 建立 Adapter (Controller/Bridge) - 將資料綁定到 listitem (View)
ArrayAdapter<String> ap = new ArrayAdapter<>(this, R.layout.listitem, autoString);

// 3. 綁定到 AutoCompleteTextView
autov.setAdapter(ap);
2. 關注點分離 (Separation of Concerns)
為了模擬真實開發情境，我將「資料」與「邏輯」分離。搜尋的候選資料由 strings.xml 統一管理，而非寫死在 Java 邏輯中。

XML

<string-array name="PlaceArray">
    <item>Taipei</item>
    <item>Taipei 101</item>
    <item>Kaohsiung MRT</item>
    <item>Taiwan High Speed Rail</item>
    </string-array>
3. UI 元件客製化 (Custom View)
為了提升使用者體驗，我建立了 listitem.xml 來定義搜尋結果的呈現方式，而非使用預設樣式。

XML

<TextView 
    android:background="#D8DF21" 
    android:textColor="@color/purple_500"
    android:textSize="18sp" ... />
📂 專案結構說明 (Project Structure)
本專案遵循 MVC 分層概念進行管理：

View (介面層)

activity_main.xml: 主功能入口，包含 ImageView 與 SeekBar。

activity_a108222040__auto_search.xml: 搜尋功能專用頁面。

listitem.xml: 定義搜尋下拉選單的 Item 樣式。

Controller (邏輯層)

MainActivity.java: 負責 SeekBar 監聽 (OnSeekBarChangeListener)、圖片切換演算法與 Intent 頁面跳轉。

A108222040_AutoSearch.java: 負責 AutoCompleteTextView 的初始化與 Adapter 資料綁定。

Model (資料層)

res/values/strings.xml: 存放搜尋候選字串陣列 (PlaceArray)。

res/drawable/: 存放圖片資源 (flow01b ~ flow06b)。

🚀 未來優化展望 (Future Roadmap)
若將此功能擴展為正式上線產品，我計畫進行以下架構升級（這也是我目前持續精進的方向）：

資料源動態化：將目前的 XML 靜態資料，改為透過 Retrofit 呼叫 RESTful API，實現雲端資料同步。

效能優化：在資料量龐大時，為搜尋監聽加入 Debounce (防抖) 機制，避免使用者輸入過快導致 API 請求過於頻繁。

架構升級 (MVVM)：引入 Android Jetpack 的 ViewModel 與 LiveData，解決螢幕旋轉導致的資料狀態重置問題。

👤 Contact
Developer: [您的名字]

GitHub: [您的 GitHub 連結]
