# About

Oracle Identity Cloud Service(IDCS)と Helidon MP を使用した FIDO2 のサンプル実装です。

## How to

application.yaml を自分のテナントの設定に合わせ修正します。

```yaml
security:
  # Set to true for production - if set to true, clear text passwords will cause failure
  config.require-encryption: false
  properties:
    # Identity Provider - IDCS
    idcs-uri: "your tenant uri"
    idcs-client-id: "your client id"
    idcs-client-secret: "your secret"
    # ...
```

Build & Run

```bash
# fido2-idcs-example/auth
mvn package -DskipTests
java -jar target/auth.jar
```
