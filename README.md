# JAVA 8 client for MangoPay API v2

## Usage example

    final String host = "api.sandbox.mangopay.com";
    final String clientId = "sdk-unit-tests";
    final String passphrase = "cqFfFrWfCcb7UadHNxx2C9Lo6Djw8ZduLi7J9USTmu8bhxxpju";
    final MangoPayConnection connection = MangoPayConnection.createDefault(host, clientId, passphrase);
    final UserApi userApi = UserApi.createDefault(connection);
    final User user = userApi.get("7571175");

## License

Apache License Version 2.0.
