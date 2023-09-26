# HidePlayer
自分以外のプレイヤーを非表示にすることができる **Plugin** だよ！

## > 対応バージョン
- MC 1.12.2 ~

## > 機能
- アイテム右クリックで show/hide を切り替えることができます。
  - 初期設定では 時計に"なんかすごい時計"と、名前を付けたら使用できます。
  - configや変更方法は後述。
- サーバー負荷が気になる場合はインターバルを好きに設定することができます。

## > Commands
- `/show` : 表示
- `/hide` : 非表示
- `/hideplayer reloadConfig` : configファイルを再読み込み
- `/hideplayer set-item ...` : show/hide を切り替えるアイテム関係の設定。
- `/hideplayer set-interval <double>` : show/hide を切り替える際のインターバルを指定。単位は秒。初期設定は0.5。
- `/hideplayer set-message ...` : メッセージを設定。
- `/hideplayer get <String path>` : configの取得。
