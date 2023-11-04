# 版控規範

## 概述
提交應包含以下結構性元素，用以向使用這套函式庫的使用者溝通當時的意圖：

- `fix`: 為 fix 類型 的提交，表示對程式修正了一個臭蟲（bug）（對應到語意化版本中的 修訂號 PATCH）。
- `feat`: 為 feat 類型 的提交，表示對程式增加了一個功能（對應到語意化版本中的 次版本 MINOR）。
- `BREAKING CHANGE`: 重大變更，如果提交的頁腳以 BREAKING CHANGE: 開頭，或是在類型、作用範圍後有 !，代表包含了重大 API 變更（對應到語意化版本中的 主版本 MAJOR）。 重大變更可以是任何 類型 提交的一部分。
- `docs`：文件（documentation）
- `style`：格式
   - 不影響程式碼運行的變動，例如：white-space, formatting, missing semicolons
- `refactor`：重構
  - 不是新增功能，也非修補 bug 的程式碼變動
- `perf`：改善效能（improves performance）
- `test`：增加測試（when adding missing tests）
- `chore`：maintain
  - 不影響程式碼運行，建構程序或輔助工具的變動，例如修改 config、Grunt Task 任務管理工具
- `revert`：撤銷回覆先前的 commit
  - 例如：revert：type(scope):subject

## 範例
```
feat: message 新增信件通知功能
feat(優惠券): 加入搜尋按鈕，調整畫面

fix: 圓餅圖圖例跑版
fix: 意見反應，信件看不到圖片問題

style: 統一換行符號 CRLF to LF

docs: 更新 README 相關資訊
docs: 修正型別註解

chore(submoudle): 變更 git url
chore: 調整單元測試環境

refactor(每日通知信件): 重構程式結構
```