
[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org)
[![GPL 3.0](https://img.shields.io/github/license/TeaClient-MC/Authenticator?style=for-the-badge)](https://github.com/TeaClient-MC/Authenticator/blob/main/LICENSE)
[![Release](https://img.shields.io/github/downloads/TeaClient-MC/Authenticator/release/total?style=for-the-badge)](https://github.com/TeaClient-MC/Authenticator/releases)
![Version](https://img.shields.io/github/v/release/TeaClient-MC/Authenticator?include_prereleases&style=for-the-badge)

# TeaClient Authenticator

### Table of  Contents
 - [Introduction](#introduction)
 - [Installation](#installation)
 - [How to use it](#how-to-use-it)
 - [Logging in to Minecraft](#logging-in-to-minecraft)

## Introduction

This is authenticator that is used in TeaClient.
It provides a simple way to login to Microsoft Account and then getting your Minecraft Account from that.

## Installation

 - [Gradle (Groovy)](#gradle-groovy)
 - [Gradle (Kotlin)](#gradle-kotlin)
 - [Maven](#maven)
### Gradle Groovy

#### Including repository

```groovy 
repositories {
	maven { url 'https://repo.teaclient.dev' }
}
```

#### Including Dependency
```groovy
dependencies {
	implementation 'tk.teaclient:Authenticator:version'
}
```
 
 ### Gradle Kotlin

#### Including Repository
```kotlin 
repositories {
	maven("https://repo.teaclient.dev")
}
```

#### Including Dependency
```kotlin
dependencies {
	implementation("tk.teaclient:Authenticator:version")
}
```
 
 ### Maven
 ```xml
 <repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://repo.teaclient.dev</url>
	</repostiory>
 </repostiories>
 <dependencies>
	<dependency>
		<groupId>tk.teaclient</groupId>
		<artifactId>Authenticator</artifactId>
		<version>version</version>
	</dependency>
 </dependencies>
 ```

## How to use it

### Azure Portal
Create a Azure Active Directory App and add Offline Access Scope

- Log in to the Azure Portal.
- Click on "App registrations" in the left-hand menu.
- Click on the "+ New registration" button at the top.
- Enter a name for your app and select the appropriate account type (single-tenant or multi-tenant).
- In the "Redirect URI" section, add http://localhost uri
- Click on "Register" to create the app.
- In the app overview page, note the "Application (client) ID" and "Directory (tenant) ID". You will need these values later.
- Click on "API permissions" in the left-hand menu.
- Click on the "+ Add a permission" button.
- Select "Microsoft Graph" and then "Delegated permissions".
- Scroll down and select the "offline_access" permission.
- Click on "Add permissions" to save the changes.

Now get Client ID and Secret
-  In the Azure Active Directory blade, select "App registrations".
-  Select the application for which you want to get the Client ID and Client Secret.
-  In the application blade, select "Certificates & secrets".
-  Under "Client secrets", click "New client secret".
-  Enter a description for the secret and select the expiration period, then click "Add".
-  The value of the secret will be displayed. Copy and save this value as it will only be displayed once and cannot be retrieved later.

### Code

So we have Azure AD app now so we go back to our IDE

First you should make Authenticator Instance
by
```kotlin
val authenticator = Authenticator(clientID, clientSecret, true | false)
```
```java
Authenticator authenticator = new Authenticator(clientID, clientSecret, true | false)
```
| Property | Type | Description
| -- | -- | --|
|clientID|String|The Application Client ID, which should provide a localhost Redirect URI.
|clientSecret|String|The Application Client Secret.|
|changeSession|Boolean|If true, changes the Minecraft session using a `SessionChangerProvider` object. By default is true.|

Now when you want to login
call method `login()`
```kotlin
authenticator.login().get()
```
This will start server on random port, and open login page in your browser.

It will return CompletableFuture of `MinecraftSessionResult`:

| Property | Type | Description
| -- | -- | --|
|username|String|The username of the authenticated user.
|uuid|UUID|The UUID of the authenticated user.|
|accessToken|String|The access token of the authenticated user.|
|refreshToken|String|The refresh token, used to get new access token.|

Okay, now you now have `MinecraftSessionResult`

You can now set it by [SessionChangerProvider](#sessionChangerProvider)
or by
#### Logging in to Minecraft
```kotlin
// mcp or access transformers
Minecraft.getMinecraft().session = Session(sessionResult.username,sessionResult.uuid, sessionResult.accessToken, "microsoft")
// mixins
IMixinMinecraft.instance.session = Session(sessionResult.username,sessionResult.uuid, sessionResult.accessToken, "microsoft")
```
```java
// mcp or access transformers
Minecraft.getMinecraft().session = new Session(sessionResult.username,sessionResult.uuid, sessionResult.accessToken, "microsoft");
// mixins
IMixinMinecraft.getInstance().setSession(new Session(sessionResult.username,sessionResult.uuid, sessionResult.accessToken, "microsoft"));
```
But when relaunching you need to login again :cry:

You can save account to file with [GSON](https://search.maven.org/artifact/com.google.code.gson/gson/2.10.1/jar)
and then load it.

But we are not logged in.

Call ``refresh()`` method
there are two ``refresh()`` methods

``refresh(String)``
``refresh(MinecraftSessionResult)``

These two return CompletableFuture of ``MinecraftSessionResult``
Now go back to [Logging in to Minecraft](#logging-in-to-minecraft)

### SessionChangerProvider

|Method|Signature|Description|
|--|--|--|
|changeSession|void (MinecraftSessionResult)|Changes the current session for a Minecraft account to the provided session result.|

You can use service to change session
first create a file in

`META-INF/services/tk.teaclient.auth.service.SessionChangerProvider`

if our class is `myclient.auth.SessionChangerImpl`

write in that file `myclient.auth.SessionChangerImpl`


###   **Issue tab is yours!**
