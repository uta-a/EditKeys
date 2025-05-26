# EditKeys Plugin

このプラグインは `EditKeys` に依存する Spigot/Bukkit 向けの Minecraft プラグインです。

---

## ✅ 使用方法

### 🔧 開発環境でのセットアップ

1. プロジェクトディレクトリ内に `libs` フォルダを作成（存在しない場合）
2. `EditKeys.jar` を `libs/` フォルダ内に配置
3. `build.gradle` に以下を追加：

```gradle
dependencies {
    compileOnly files("libs/EditKeys.jar")
}
```

---

### 🚀 サーバーでの使用方法

1. `plugin.yml` に以下を追記：

```yaml
depend: [EditKeys]
```

2. サーバーの `plugins` フォルダに以下の2つのファイルを配置：
   - EditKeysの機能を使用するプラグイン `.jar`
   - `EditKeys.jar`

---

## 📁 プロジェクト構成例

```
YourPlugin/
├── build.gradle
├── libs/
│   └── EditKeys.jar
├── src/
│   └── main/
│       └── java/
│           └── your/
│               └── package/
│                   └── Main.java
├── plugin.yml
└── README.md
```

---

## 実際の使用例
```java
// set string
ItemStack item = player.getInventory().getItemInMainHand();
ItemStack newItem = EditItemKeys.setString(item, "test-key", "test-value");
player.getInventory().setItem(0, newItem);
```
```java
// get douoble
ItemStack item = player.getInventory().getItemInMainHand();
Double value = EditItemKeys.getDouble(item, "double-key");
```
```java
// has
ItemStack item = player.getInventory().getItemInMainHand();
Boolean isKey = EditItemKeys.has(item, "test-key3");
```
```java
// player get
Float value = EditPlayerKeys.get(player, "test-key4");
```




