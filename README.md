# HidePlayer
自分以外のプレイヤーを非表示にすることができる **Plugin** だよ！

## > 対応バージョン
- MC 1.12.2 ~

## > 機能
- アイテム右クリックで show/hide を切り替えることができます。
  - 初期設定では 時計に"なんかすごい時計"と、名前を付けたら使用できます。
  - configや変更方法は後述。
 
https://github.com/Ryukkun/HidePlayer/assets/83561145/e0a78708-6489-4253-b5fd-5a62d76c10d9
    
- サーバー負荷が気になる場合はインターバルを好きに設定することができます。

## > Commands
- `/show` : 表示
- `/hide` : 非表示
- `/hideplayer reloadConfig` : configファイルを再読み込み
- `/hideplayer set-item ...` : show/hide を切り替えるアイテム関係の設定。
- `/hideplayer set-interval <double>` : show/hide を切り替える際のインターバルを指定。単位は秒。初期設定は0.5。
- `/hideplayer set-message ...` : メッセージを設定。
- `/hideplayer get <String path>` : configの取得。

## > Config
```YAML
item:
  # hide/showを切り替える際 アイテムも入れ替えるか設定できます。  
  ## falseの場合は hide/showどちらののアイテムでも show/hideを切り替えられます。  
  change_item: false  
  
  ## アイテムの判別にnameとidを用います。
  ## nameが空白の場合は デフォルトの名前（時計なら”時計”） で反応します。
  hide:
    id: WATCH
    name: なんかすごい時計
    lore: ''

  show:
    id: COMPASS
    name: なんかすごいコンパス
    lore: ''

## 単位は秒。 0.0でもいいよん
interval: 0.5

## 空欄にすればメッセージは送信されません。
message:
  ## 接頭辞。メッセージの最初についてるやつ
  prefix:
    success: §2H§aide§2P§alayer §2>> §a
    warning: §6H§eide§6P§elayer §6>> §e
    error: §4H§cide§4P§clayer §4>> §c

  ## プレイヤーを消すときのメッセージ
  hide: §r§2§lhid §r§aall players
  show: §r§2§lshowed §r§aall players

  already_hide: already hidden all players =)
  already_show: already shown all players =)

  ## intervalは `%s` で参照できます。
  interval: please wait %s seconds ...
```
