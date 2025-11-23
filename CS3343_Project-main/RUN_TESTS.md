# 如何運行測試

## 方法 1: 使用 IDE (推薦)

### IntelliJ IDEA
1. 右鍵點擊 `testCmdCheckVacancyAllEmpty.java` 或 `testCmdCheckVacancyAllOccupied.java`
2. 選擇 "Run 'testCmdCheckVacancyAllEmpty'"
3. 要同時運行兩個測試類：
   - 在項目視圖中選擇兩個測試類文件
   - 右鍵點擊 → "Run 'All Tests'"
   - 或者創建一個運行配置，包含兩個測試類

### Eclipse
1. 右鍵點擊測試類
2. 選擇 "Run As" → "JUnit Test"
3. 要同時運行兩個測試類：
   - 創建一個 JUnit Test Suite
   - 或者選擇多個測試類一起運行

## 方法 2: 使用命令行 (需要 JUnit 依賴)

如果您有 JUnit 5 的 jar 文件，可以使用以下命令：

```bash
# 編譯源代碼
javac -cp "junit-platform-console-standalone.jar" -d out src/parkinglot/*.java src/testparkinglot/*.java

# 運行測試
java -jar junit-platform-console-standalone.jar --class-path out --select-class testparkinglot.testCmdCheckVacancyAllEmpty --select-class testparkinglot.testCmdCheckVacancyAllOccupied
```

## 驗證測試修復

這兩個測試現在應該可以一起運行了，因為：
1. 每個測試在 `setUp()` 中都會清理停車位狀態
2. 每個測試在 `tearDown()` 中都會清理停車位狀態
3. `testCmdCheckVacancyAllOccupied` 使用固定的數字 10 而不是動態的 `privateSpots.size()`

