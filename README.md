<h1>SPA バックエンド(予約管理)</h1>

#目次

<p>0.操作方法</p>
<p>1.プロジェクトの概要</p>
<p>2.プロジェクトの作成経緯</p>
<p>3.構成</p>
<p>4.改善課題</p>

<h2>0.操作方法</h2>
<p>ローカルのでの運用方法はルートディレクトリでvscode+jdkの構成で実行していたので、vscode上部の検索バーから「デバッグを選択」</p>
<p>注意としてstripe_api_keyとgoogle_map_keyは個別の値を差し込むこと(サーバー上での運用手順は追記予定)</p>

<h2>1.プロジェクトの概要</h2>
<p>react+springbootで予約、自動決済機能を活用したSPA(single-page-Application)であり、このrepositoryは以下バックエンド枠の予約管理管理コンテナのソース管理兼メモとして利用してます。</p>
<p>現在の進捗としてローカルでの挙動まで確認し、今後2ヶ月の目標としてサイトとして一般的にブラウザで利用できるところまで作成する予定です。</p>

<img width="800" alt="構成" src="https://github.com/user-attachments/assets/312c6f5f-8c8e-4421-9868-f2447734a15f" />

<h2>2.プロジェクトの作成経緯</h2>
<p>元の題材としてはweb系で何かECサイトをコピーしてみるというところで形にならなかった中で、行きつけのお店が予約時に決済をとるアプリ機能を採用していたが、</p>
<p>レビューで予約直後の決済はキャンセルできない不満が散見されたので、興味が湧いて、もし当事者ならどのように機能改善したかという発想をベースにSPAとして切り出してみただけです。</p>
<p>上記の構成図みて</p>
<p>・mqいらなくない？</p>
<p>・なんでコンテナ分けたの?　1個のコンテナでバッチとして運用すればよくない？</p>
<p>と疑問を思われると思いますが、全くその通りだと思います。こちらに関してはspring未経験で学習目的なので慣れるために色々機能使いたかっただけです。納品するプロジェクト業務ではちゃんと1つにまとめます。</p>

<h2>3.構成</h2>
<h3>3-1.開発環境</h3>
<pre>
| 言語・主にインストールしたlib | バージョン |
| --------------------- | ---------- |
| java                  | 17         |
| spring-boot           | 3.5.0-Snapshot|
| myqsl                 | 8.0.42     |
</pre>

<h3>3-2.ディレクトリ</h3>
<pre>
~/maiin
├── java
│   └── com
│       └── restapi
│           └── cartcontrol(ECサイトで運用しようとしていた名残でファイル名cartになってます。今利用してるのはShop,Menu,Reservationクラスと関連コントローラーのみ)
│               ├── CartcontrolApplication.java
│               ├── controller
│               │   ├── FavoriteController.java
│               │   ├── MenuController.java
│               │   ├── ReservationController.java
│               │   └── ShopController.java
│               ├── model
│               │   ├── DTO(不具合発生時に課題2の解決案として実装したもののなくても解決したので特に利用してない)
│               │   │   └── MenuDto.java
│               │   ├── entity
│               │   │   ├── Favorite.java
│               │   │   ├── Menu.java
│               │   │   ├── Reservation.java
│               │   │   └── Shop.java
│               │   └── repository
│               │       ├── FavoriteRepository.java
│               │       ├── MenuRepository.java
│               │       ├── ReservationRepository.java
│               │       └── ShopRepository.java
│               ├── requestbody(各クラスの登録APIで必要なパラメータのオプション)
│               │   ├── Favorite
│               │   │   └── RegistBody.java
│               │   ├── Menu
│               │   │   └── RegistBody.java
│               │   ├── Reservation
│               │   │   └── RegistBody.java
│               │   └── Shop
│               │       └── RegistBody.java
│               ├── schedular(予約時間を過ぎたら自動的にstripeサーバーに決済処理をコール)
│               │   ├── MQ(MQ設定)
│               │   │   ├── PaymentPackage.java
│               │   │   ├── PaymentSender.java
│               │   │   └── RabbitMQConfig.java
│               │   ├── reservation(送信キュー)
│               │   │   └── reservationschedular.java
│               │   └── shop
│               └── security
│                   └── SecurityConfig.java
└── resources
    ├── application.properties
    └── logback-spring.xml
</pre>

<h2>4.改善課題</h2>
<p>1.予約登録時に以下の要点を記載したメールアドレスを送信(ローカルから実機に対して検証する術が調べても見つからなかったので暫定未実装)、サーバー上で稼働できる状態になったら実装</p>
・店舗への連絡先<br/>
・予約キャンセル用ページの専用url
<p>2.jparepositoryで利用するsqlクエリで部分参照のselect文がnativequery設定だとできなかった(Dtoを挟む方法はあったものの、うまくいかなったので余裕があれば修正)</p>
<p>3.stripe決済用のサーバーからキューでstripeとの通信ステータスを受け取り、予約ステータスの区分を詳細化して管理できるようにする。(現在は決済待ち、完了、失効のみ利用している)</p>
<p>4.課題1にともなうキャンセルAPIの作成 -> 作成&postmanでDB疎通確認(26/7/1)</p>
<p>5.メール実装の際のロールバックなど必要なトランザクションの見直し</p>