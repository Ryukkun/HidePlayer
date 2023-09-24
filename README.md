# HidePlayer
自分以外のプレイヤーを非表示にすることができる **Plugin** だよ！

## > 対応バージョン
- MC 1.12.2 ~

## > 機能
- アイテム右クリックで show/hide を切り替えることができます。
  - アイテムはconfigで指定。初期設定は時計。
- サーバー負荷が気になる場合はインターバルを好きに設定することができます。

## > Commands
- `/show` : 表示
- `/hide` : 非表示
- `/hideplayer reload` : configファイルを再読み込み
- `/hideplayer set-itemId <string>` : show/hide を切り替えるアイテムを指定。初期設定は時計。
- `/hideplayer set-interval <double>` : show/hide を切り替える際のインターバルを指定。単位は秒。初期設定は0.5。
- `/hideplayer check-name <boolean>` : show/hide を切り替えるアイテムを名前でも判別するか指定。初期設定はfalse
- `/hideplayer set-name <string>` : アイテムの名前を指定
