# Mangopay Client

[![License](https://img.shields.io/github/license/MicroWorld/mangopay-client.svg?maxAge=2592000)](https://github.com/MicroWorld/mangopay-client/blob/master/LICENSE.txt)

Mangopay Client is a Java 8 client library to work with [Mangopay REST API v2.01](https://docs.mangopay.com/api-references/).  
The official Mangopay client ([MangopaySDK](https://github.com/Mangopay/mangopay2-java-sdk)) does not adhere much with Java conventions and has many methods throwing `Exception`, all that making it annoying to use, hence the creation of this Mangopay Client.

This library is in its early development phase and does not cover much of Mangopay API yet. More coming soon.

## Installation

This project is built using [gradle](https://gradle.org/):

    $ ./gradlew build

The generated `jar` file is located under the `build/libs` folder.

This library is not yet published on any repository like [maven central](http://search.maven.org/).

## Usage example

    String host = "api.sandbox.mangopay.com";
    String clientId = "sdk-unit-tests";
    String passphrase = "cqFfFrWfCcb7UadHNxx2C9Lo6Djw8ZduLi7J9USTmu8bhxxpju";
    MangopayConnection connection = MangopayConnection.createDefault(host, clientId, passphrase);
    MangopayClient client = MangopayClient.createDefault(connection);
    User user = client.getUserService().get("7571175");
