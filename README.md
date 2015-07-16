# Mangopay Client

Mangopay Client is a Java 8 client library to work with [Mangopay REST API v2](https://docs.mangopay.com/api-references/).  
The official Mangopay client ([MangopaySDK](https://github.com/Mangopay/mangopay2-java-sdk)) is not built with `maven`, does not adhere much with Java conventions and has many methods throwing `Exception`, all that making it annoying to use, hence the creation of this Mangopay Client.

This library is in its early development phase and does not cover much of Mangopay API yet. More coming soon.

## Installation

This project is built using [maven](https://maven.apache.org/):

    $ mvn clean install

The generated `jar` file is located under the `target` folder.

This library is not yet published on [maven central](http://search.maven.org/).

## Usage example

    String host = "api.sandbox.mangopay.com";
    String clientId = "sdk-unit-tests";
    String passphrase = "cqFfFrWfCcb7UadHNxx2C9Lo6Djw8ZduLi7J9USTmu8bhxxpju";
    MangopayConnection connection = MangopayConnection.createDefault(host, clientId, passphrase);
    UserApi userApi = UserApi.createDefault(connection);
    User user = userApi.get("7571175");

## License

Apache License Version 2.0.
