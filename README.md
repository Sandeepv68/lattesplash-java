# LatteSplash v1.0.0

[![license](https://img.shields.io/github/license/SandeepVattapparambil/lattesplash-java.svg)](https://github.com/SandeepVattapparambil/lattesplash-java/blob/main/LICENSE) ![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg) [![CI](https://github.com/SandeepVattapparambil/lattesplash-java/workflows/CI/badge.svg)](https://github.com/SandeepVattapparambil/lattesplash-java/actions?query=workflow%3ACI) ![GitHub issues](https://img.shields.io/github/issues/SandeepVattapparambil/lattesplash-java.svg) ![GitHub forks](https://img.shields.io/github/forks/SandeepVattapparambil/lattesplash-java.svg) ![GitHub stars](https://img.shields.io/github/stars/SandeepVattapparambil/lattesplash-java.svg)


LatteSplash is a production-ready, synchronous + asynchronous API wrapper for the popular [Unsplash](https://unsplash.com/) platform, written in **Java 11**. It is a faithful port of the [wrapsplash](https://github.com/SandeepVattapparambil/wrapsplash) npm module, supporting all original features with idiomatic Java conventions.

Unsplash provides beautiful high quality free images and photos that you can download and use for any project without any attribution.

Before using the Unsplash API, you need to **register as a developer** and **read the API Guidelines.**

> **Note:**  Every application must abide by the [API Guidelines](https://unsplash.com/documentation). Specifically, remember to hotlink images and trigger a download when appropriate.

## Table of Contents
<!--ts-->
* [About](#lattesplash-v100)
* [Installation](#installation)
* [Sample Usage](#sample-usage)
* [Development](#development)
* [Feature Parity with wrapsplash](#feature-parity-with-wrapsplash)
* [Dependency](#dependency)
* [API Documentation](#api-documentation)
    * [Schema](#schema)
        * [Location](#location)
        * [Summary Objects](#summary-objects)
        * [Error Messages](#error-messages)
    * [Authorization](#authorization)
        * [Public Actions](#public-actions)
        * [User Authentication](#user-authentication)
        * [LatteSplash Constructor](#lattesplash-constructor)
        * [Generate Bearer Token](#generate-bearer-token)
    * [Users APIs](#users-apis)
        * [Get User's Public Profile](#get-users-public-profile)
        * [Get User's Portfolio Link](#get-users-portfolio-link)
        * [Get User's Photos](#get-users-photos)
        * [Get User Liked Photos](#get-user-liked-photos)
        * [Get User's Collections](#get-users-collections)
        * [Get User's Statistics](#get-users-statistics)
    * [Photos APIs](#photos-apis)
        * [List Photos](#list-photos)
        * [List Curated Photos](#list-curated-photos)
        * [Get a Photo by Id](#get-a-photo-by-id)
        * [Get a Random Photo](#get-a-random-photo)
        * [Get a Photo's Statistics](#get-a-photos-statistics)
        * [Get a Photo's Download Link](#get-a-photos-download-link)
        * [Update a Photo](#update-a-photo)
        * [Like a Photo](#like-a-photo)
        * [Unlike a Photo](#unlike-a-photo)
    * [Search APIs](#search-apis)
        * [Search Photos](#search-photos)
        * [Search Collections](#search-collections)
        * [Search Users](#search-users)
    * [Current User APIs](#current-user-apis)
        * [Get the user's profile](#get-users-profile)
        * [Update User's Profile](#update-users-profile)
    * [Stats APIs](#stats-apis)
        * [Totals](#stats-totals)
        * [Months](#stats-month)
    * [Collections APIs](#collections-apis)
        * [Link Relations](#link-relations)
        * [List Collections](#list-collections)
        * [List Featured Collections](#list-featured-collections)
        * [List Curated Collections](#list-curated-collections)
        * [Get a Collection](#get-a-collection)
        * [Get a Curated Collection](#get-a-curated-collection)
        * [Get a Collection's Photos](#get-a-collections-photos)
        * [Get a Curated Collection's Photos](#get-a-curated-collections-photos)
        * [List a Collection's Related Collections](#list-a-collections-related-collections)
        * [Create a New Collection](#create-a-new-collection)
        * [Update an Existing Collection](#update-an-existing-collection)
        * [Delete a Collection](#delete-a-collection)
        * [Add a Photo to a Collection](#add-a-photo-to-a-collection)
        * [Remove a Photo from a Collection](#remove-a-photo-from-a-collection)
* [Continuous Integration (CI)](#continuous-integration-ci)
* [Tests](#tests)
* [License](#license)
* [Acknowledgements](#acknowledgements)
<!--te-->

## Installation

### Maven
```xml
<dependency>
    <groupId>com.lattesplash</groupId>
    <artifactId>lattesplash</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
implementation 'com.lattesplash:lattesplash:1.0.0'
```

### Sample Usage
```java
import com.lattesplash.LatteSplash;
import com.lattesplash.LatteSplashConfig;
import com.lattesplash.LatteSplashResponse;

// Initialize with bearer token
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .bearerToken("<bearer-token>")
    .build();

LatteSplash unsplash = new LatteSplash(config);

// Synchronous
LatteSplashResponse result = unsplash.photos().getPhoto("<photo-id>");
System.out.println(result.getData());

// Asynchronous
unsplash.photos().getPhotoAsync("<photo-id>")
    .thenAccept(response -> System.out.println(response.getData()))
    .exceptionally(error -> {
        System.err.println(error.getMessage());
        return null;
    });
```

### Development
```sh
mvn compile
mvn test
mvn package        # Build the JAR
mvn javadoc:javadoc  # Generate Javadoc
```

### Feature Parity with wrapsplash
This Java port supports all features of the original [wrapsplash](https://github.com/SandeepVattapparambil/wrapsplash) npm module:

| Feature | Status |
| --- | --- |
| Bearer token authentication | Supported |
| Client-ID authentication | Supported |
| All 34 API methods | Supported |
| Sync + Async APIs (`CompletableFuture`) | Supported |
| Input validation | Supported |
| Retry with configurable delay | Supported |
| Configurable timeout | Supported |
| SHA-256 header hashing | Supported |
| Error normalization (`LatteSplashError`) | Supported |
| 204 Content Deleted handling | Supported |
| 403 Rate Limit handling | Supported |

### Dependency
This library depends on [OkHttp](https://square.github.io/okhttp/) and [Gson](https://github.com/google/gson) to make requests and handle JSON serialization for the [Unsplash API](https://unsplash.com/documentation). It uses [SLF4J](https://www.slf4j.org/) for logging.

> **Note:** This library uses the SLF4J API for logging but does **not** include a logging implementation. You must provide an SLF4J binding (e.g., [Logback](https://logback.qos.ch/), [Log4j2](https://logging.apache.org/log4j/2.x/), or [SLF4J Simple](https://www.slf4j.org/faq.html#shading)) in your application's classpath. Without a binding, you will see a warning at runtime and no log output will be produced.

### API Documentation

### Schema
#### Location
The API we are using is ```https://api.unsplash.com/```. Responses are sent as JSON.

#### Summary objects
When retrieving a list of objects, an abbreviated or summary version of that object is returned - i.e., a subset of its attributes. To get a full detailed version of that object, fetch it individually.

#### Error messages
If an error occurs, whether on the server or client side, the error message(s) will be returned in an ```errors``` array. 
For example:
```sh
422 Unprocessable Entity
```
```json
{
  "errors": ["Username is missing", "Password cannot be blank"]
}
```

### Authorization
#### Public Actions
Many actions can be performed without requiring authentication from a specific user. For example, downloading a photo does not require a user to log in.
To authenticate requests in this way, pass your application's access key via the HTTP ```Authorization``` header:
```sh
Authorization: Client-ID YOUR_ACCESS_KEY
```
You can also pass this value using a ```client_id``` query parameter:
```sh
https://api.unsplash.com/photos/?client_id=YOUR_ACCESS_KEY
```
If only your access key is sent, attempting to perform non-public actions that require user authorization will result in a ```401 Unauthorized response```.

#### User Authentication
The Unsplash API uses OAuth2 to authenticate and authorize Unsplash users. Unsplash's OAuth2 paths live at ```https://unsplash.com/oauth/```.

Before using LatteSplash: 
- Developers are required to create a developer account from [Unsplash](https://unsplash.com/developers).
- Create a new App from Your Apps page.
- Get the ```Access Key```, ```Secret key```, ```Callback URLs```, and ```Authorization code```.
- If you have a Bearer Token, then its super, or else you can generate it using **LatteSplash**.
> **Note:** ```Authorization code``` can be obtained by clicking the ```Authorize``` link  next to ```Callback URLs```. Also ```Authorization code``` is a one-time use code, you have to generate it again, if the action fails!.

#### LatteSplash Constructor
LatteSplash instance is created by passing configuration obtained from Unsplash developer account. The configuration is built using the `LatteSplashConfig.Builder` class. The following example shows all the available options.

```java
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .accessKey("<api-key>")
    .secretKey("<secret-key>")
    .redirectUri("<callback-url>")
    .code("<authorization-code>")
    .timeout(10000)       // optional, default: 10000ms
    .retries(2)           // optional, default: 2
    .retryDelayMs(100)    // optional, default: 100ms
    .build();

LatteSplash unsplash = new LatteSplash(config);
```
If you have a `bearer_token`, then only bearer token has to be passed in.
```java
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .bearerToken("<bearer-token>")
    .build();

LatteSplash unsplash = new LatteSplash(config);
```

#### Generate Bearer Token 
A method to generate a Bearer Token for ```write_access``` to private data.
The constructor in this case requires `accessKey`, `secretKey`, `redirectUri`, and `code` to generate bearer token.
> **Note:** No Parameters are required for this function.

```java
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .accessKey("<api-key>")
    .secretKey("<secret-key>")
    .redirectUri("<callback-url>")
    .code("<authorization-code>")
    .build();

LatteSplash unsplash = new LatteSplash(config);

// Synchronous
LatteSplashResponse result = unsplash.generateBearerToken();
System.out.println(result.getData());

// Asynchronous
unsplash.generateBearerTokenAsync()
    .thenAccept(response -> System.out.println(response.getData()))
    .exceptionally(error -> {
        System.err.println(error.getMessage());
        return null;
    });
```
If successful, the response body will be a JSON representation of your user's access token a.k.a bearer token:

```json
{
   "access_token": "091343ce13c8ae780065ecb3b13dc903475dd22cb78a05503c2e0c69c5e98044",
   "token_type": "bearer",
   "scope": "public read_photos write_photos",
   "created_at": 1436544465
}
```
and once you have your ```bearer_token``` you can use it in your app like this:
```java
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .bearerToken("<bearer-token>")
    .build();

LatteSplash unsplash = new LatteSplash(config);
```
### Users APIs
#### Get User's Public Profile
A method to retrieve public details on a given user.
```
GET /users/:username
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 
| **width** | *Integer* | Width of the profile picture in pixels | yes | 
| **height** | *Integer* | Height of the profile picture in pixels | yes | 

> **Note:**  When optional **height** & **width** are specified the profile image will be included in the "profile_image" object as "custom".

```java
unsplash.users().getPublicProfile("<username>", 600, 600);
```

#### Get User's Portfolio Link
A method to retrieve a single user's portfolio link.
```
GET /users/:username/portfolio
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 

```java
unsplash.users().getUserPortfolio("<username>");
```

#### Get User's Photos
A method to get a list of photos uploaded by a particular user.
```
GET /users/:username/photos
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
| **stats** | *Boolean* | Show the stats for each user's photo | yes | false
| **resolution** | *String* | The frequency of the stats | yes | days
| **quantity** | *Integer* | The amount of for each stat | yes | 30
| **orderBy** | *String* | How to sort the photos.(```Valid values: latest, oldest, popular```) | yes | latest

```java
unsplash.users().getUserPhotos("<username>", 1, 10, false, "days", 30, "latest");
```

#### Get User Liked Photos
A method to get a list of photos liked by a user.
```
GET /users/:username/likes
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
| **orderBy** | *String* | How to sort the photos.(```Valid values: latest, oldest, popular```) | yes | latest

```java
unsplash.users().getUserLikedPhotos("<username>", 1, 10, "latest");
```

#### Get User's Collections
A method to get a list of collections created by the user.
```
GET /users/:username/collections
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10

```java
unsplash.users().getUserCollections("<username>", 1, 10);
```

#### Get User's Statistics
A method to get a user's account statistics.
```
GET /users/:username/statistics
```

##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **username** | *String* | The username of the particular user | no | 
| **resolution** | *String* | The frequency of the stats | yes | days
| **quantity** | *Integer* | The amount of for each stat | yes | 30

```java
unsplash.users().getUserStatistics("<username>", "days", 30);
```

### Photos APIs
#### List Photos
A method to get a single page from the list of all photos.
```
GET /photos
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
| **orderBy** | *String* | How to sort the photos.(```Valid values: latest, oldest, popular```) | yes | latest

```java
unsplash.photos().listPhotos(1, 10, "latest");
```

#### List Curated Photos
A method to get a single page from the list of the curated photos.
```
GET /photos/curated
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
| **orderBy** | *String* | How to sort the photos.(```Valid values: latest, oldest, popular```) | yes | latest

```java
unsplash.photos().listCuratedPhotos(1, 10, "latest");
```

#### Get a Photo by Id
A method to retrieve a single photo.
```
GET /photos/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The photo's ID | no | 
| **width** | *Integer* | Image width in pixels | yes | 
| **height** | *Integer* | Image height in pixels | yes | 
| **rect** | *String* |4 comma-separated integers representing x, y, width, height of the cropped rectangle | yes | 
> **Note:** Supplying the optional **width** or **height** parameters will result
in the custom photo URL being added to the urls object:

```java
unsplash.photos().getPhoto("<id of the photo>", 500, 500, "x, y, width, height");
```

#### Get a Random Photo
A method to retrieve a single random photo, given optional filters.
```
GET /photos/random
```
##### Parameters
> **Note:** All parameters are optional, and can be combined to narrow the pool of photos from which a random one will be chosen.

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- | 
| **collections** | *String* | The public collection ID('s) to filter selection. If multiple, comma-separated | yes |
| **featured** | *Boolean* | Limit selection to featured photos | yes | false
| **username** | *String* | Limit selection to a single user | yes |
| **query** | *String* | Limit selection to photos matching a search term | yes |
| **width** | *Integer* | The Image width in pixels | yes |
| **height** | *Integer* | The Image height in pixels | yes |
| **orientation** | *String* | Filter search results by photo orientation. (```Valid values are landscape, portrait, and squarish```) | yes | landscape
| **count** | *Integer* | The number of photos to return. (```max: 30```) | yes | 1

> **Note:** You can't use the collections and query parameters in the same request.
> When supplying a **count** parameter - and only then - the response will be an array of photos,   even if the value of **count** is 1.

```java
unsplash.photos().getRandomPhoto(null, false, null, null, null, null, "landscape", 1);
```

#### Get a Photo's Statistics
A method to retrieve total number of downloads, views and likes of a single photo, as well as the historical breakdown of these stats in a specific timeframe (default is 30 days).
```
GET /photos/:id/statistics
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- | 
| **id** | *String* | The photo's ID | no | 
| **resolution** | *String* | The frequency of the stats | yes | days
| **quantity** | *Integer* | The amount of for each stat | yes | 30
> **Note:** Currently, the only resolution param supported is "days". The quantity param can be any number between 1 and 30.

```java
unsplash.photos().getPhotoStatistics("<photo-id>", "days", 10);
```

#### Get a Photo's Download Link
A method to retrieve a single photo's download link. Preferably hit this endpoint if a photo is downloaded in your application for use (example: to be displayed on a blog article, to be shared on social media, to be remixed, etc).
```
GET /photos/:id/download
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- | 
| **id** | *String* | The photo's ID | no | 
> **Note:** This is different than the concept of a view, which is tracked automatically when you hotlink an image.

```java
unsplash.photos().getPhotoLink("<photo-id>");
```

#### Update a Photo
A method to update a photo on behalf of the logged-in user. This requires the ```write_photos``` scope and ```bearer_token```.
```
PUT /photos/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The photo's ID | no | 
| **location** | *Map* | The location object holding location data | yes | 
| **exif** | *Map* | The exif object holding exif data | yes
> **Note:** **Exchangeable image file format** (officially Exif, according to JEIDA/JEITA/CIPA specifications) is a standard that specifies the formats for images, sound, and ancillary tags used by digital cameras (including smartphones), scanners and other systems handling image and sound files recorded by digital cameras. [Readmore](https://en.wikipedia.org/wiki/Exif)

##### location & exif map keys

| map[key] | Description |
| ----- | ----------- |
| location["latitude"] | The photo location's latitude (Optional) | 
| location["longitude"] | The photo location's longitude (Optional) |
| location["name"] | The photo location's name (Optional) |
| location["city"] | The photo location's city (Optional) |
| location["country"] | The photo location's country (Optional) |
| location["confidential"] | The photo location's confidentiality (Optional) | 
| exif["make"] | Camera's brand (Optional) |
| exif["model"] | Camera's model (Optional) |
| exif["exposure_time"] | Camera's exposure time (Optional) |
| exif["aperture_value"] | Camera's aperture value (Optional) |
| exif["focal_length"] | Camera's focal length (Optional) |
| exif["iso_speed_ratings"] | Camera's iso (Optional) |

```java
Map<String, Object> location = Map.of("country", "INDIA");
Map<String, Object> exif = Map.of("make", "Redmi Note 3");
unsplash.photos().updatePhoto("<photo-id>", location, exif);
```

#### Like a Photo
A method to like a photo on behalf of the logged-in user. This requires the ```write_likes``` scope.
```
POST /photos/:id/like
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The photo's ID | no | 
> **Note:**  This action is idempotent; sending the POST request to a single photo multiple times has no additional effect.

```java
unsplash.photos().likePhoto("<photo-id>");
```

#### Unlike a Photo
A method to remove a user's like of a photo. 
```
DELETE /photos/:id/like
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The photo's ID | no | 
> **Note:** This action is idempotent; sending the DELETE request to a single photo multiple times has no additional effect.

```java
unsplash.photos().unlikePhoto("<photo-id>");
```

### Search APIs
#### Search Photos
A method to get a single page of photo results for a particular query.
```
GET /search/photos
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **query** | *String* | The search query | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
| **collections** | *String* | Collection ID('s) to narrow search. If multiple, comma-separated. | yes | 
| **orientation** | *String* | Filter search results by photo orientation. (```Valid values are landscape, portrait, and squarish.```) | yes | landscape

```java
unsplash.search().searchPhotos("cars", 1, 10, "", "landscape");
```

#### Search Collections
A method to get a single page of collection results for a query.
```
GET /search/collections
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **query** | *String* | The search query | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10

```java
unsplash.search().searchCollections("cars", 1, 10);
```

#### Search Users
A method to get a single page of user results for a query.
```
GET /search/users
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **query** | *String* | The search query | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10

```java
unsplash.search().searchUsers("<search-keyword>", 1, 10);
```

### Current User APIs
#### Get User's Profile
A method to get the current User's profile. To access a user's private data, the user is required to authorize the ```read_user``` scope. Without it, this request will return a ```403 Forbidden response```.
```
GET /me
```
> **Note:** No Parameters are required.

> **Note:**  Without a Bearer token (i.e. using a ```Client-ID token```) this request will return a ```401 Unauthorized``` response.

```java
unsplash.currentUser().getCurrentUserProfile();
```

#### Update User's Profile
A method to update the current User's profile. 
```
PUT /me
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| username | *String* | The username of the current user | yes |
| firstName | *String* | The first name of the current user | yes |
| lastName | *String* | The last name of the current user | yes |
| email | *String* | The email id of the current user | yes |
| url | *String* | The Portfolio/personal URL of the current user | yes |
| location | *String* | The location of the current user | yes |
| bio | *String* | The About/bio of the current user | yes |
| instagramUsername | *String* | The Instagram username of the current user | yes |
> **Note:** This action requires the ```write_user scope```. Without it, it will return a ```403 Forbidden response```.

```java
unsplash.currentUser().updateCurrentUserProfile("<username>", "<first_name>", "<last_name>", "<email>", "<url>", "<location>", "<bio>", "<instagram_username>");
```

### Stats APIs
#### Stats Totals
A method to get a list of counts for all of Unsplash.
```
GET /stats/total
``` 
```java
unsplash.stats().getStatsTotals();
```
#### Response
```json
{
  "total_stats": {
    "photos": 10000,
    "downloads": 2000,
    "views": 5000,
    "likes": 800,
    "photographers": 100,
    "pixels": 200000,
    "downloads_per_second": 10,
    "views_per_second": 20,
    "developers": 20,
    "applications": 50,
    "requests": 8000
  }
}
```
#### Stats Month
A method to get the overall Unsplash stats for the past 30 days.
```
GET /stats/month
```
```java
unsplash.stats().getStatsMonth();
```
#### Response
```json
{
  "month_stats": {
    "downloads": 20,
    "views": 200,
    "likes": 60,
    "new_photos": 10,
    "new_photographers": 5,
    "new_pixels": 2000,
    "new_developers": 8,
    "new_applications": 5,
    "new_requests": 100
  }
}
```

### Collections APIs
#### Link Relations
Collections have the following link relations:

| rel | Description |
| --- | ----------- |
| ```self``` | API location of this collection |
| ```html``` | HTML location of this collection |
| ```photos``` | API location of this collection's photos |
| ```related``` | API location of this collection's related collections (Non-curated collections only) |
| ```download``` | Download location of this collection's zip file (Curated collections only) |

#### List Collections
A method to get a single page from the list of all collections.
```
GET /collections
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
```java
unsplash.collections().listCollections(null, null);
```

#### List Featured Collections
A method to get a single page from the list of featured collections.
```
GET /collections/featured
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
```java
unsplash.collections().listFeaturedCollections(null, null);
```

#### List Curated Collections
A method to get a single page from the list of curated collections.
```
GET /collections/curated
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
```java
unsplash.collections().listCuratedCollections(null, null);
```

#### Get a Collection
A method to retrieve a single collection. To view a user's private collections, the ```read_collections``` scope is required.
```
GET /collections/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
```java
unsplash.collections().getCollection("<collection-id>");
```

#### Get a Curated Collection
A method to retrieve a single curated collection. To view a user's private collections, the ```read_collections``` scope is required.
```
GET /collections/curated/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
```java
unsplash.collections().getCuratedCollection("<curated-collection-id>");
```

#### Get a Collection's Photos
A method to retrieve a collection's photos.
```
GET /collections/:id/photos
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
```java
unsplash.collections().getCollectionPhotos("<collection-id>", 1, 10);
```

#### Get a Curated Collection's Photos
A method to retrieve a curated collection's photos.
```
GET /collections/curated/:id/photos
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
| **page** | *Integer* | Page number to retrieve | yes | 1
| **perPage** | *Integer* | Number of items per page | yes | 10
```java
unsplash.collections().getCuratedCollectionPhotos("<curated-collection-id>", 1, 10);
```

#### List a Collection's Related Collections
A method to retrieve a list of collections related to this one.
```
GET /collections/:id/related
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
```java
unsplash.collections().listRelatedCollections("<collection-id>");
```

#### Create a New Collection
A method to create a new collection. This requires the ```write_collections``` scope.
```
POST /collections
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **title** | *String* | The title of the collection | no | 
| **description** | *String* | The collection's description | yes |
| **isPrivate** | *Boolean* | Whether to make this collection private | yes | false
```java
unsplash.collections().createCollection("<collection-name>", "<description>", false);
```

#### Update an Existing Collection
A method to update an existing collection belonging to the logged-in user. This requires the ```write_collections``` scope.
```
PUT /collections/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The collection id | no |
| **title** | *String* | The title of the collection | yes | 
| **description** | *String* | The collection's description | yes |
| **isPrivate** | *Boolean* | Whether to make this collection private | yes | false
```java
unsplash.collections().updateCollection("<collection-id>", "<collection-name>", "<description>", false);
```

#### Delete a Collection 
A method to delete a collection belonging to the logged-in user. This requires the ```write_collections``` scope.
```
DELETE /collections/:id
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **id** | *String* | The Collection ID  | no | 
```java
unsplash.collections().deleteCollection("<collection-id>");
```

#### Add a Photo to a Collection
A method to add a photo to one of the logged-in user's collections. Requires the ```write_collections``` scope.
```
POST /collections/:collection_id/add
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **collectionId** | *String* | The Collection ID  | no | 
| **photoId** | *String* | The Photo ID  | no | 
> **Note:**  If the photo is already in the collection, this action has no effect.

```java
unsplash.collections().addPhotoToCollection("<collection-id>", "<photo-id>");
```

#### Remove a Photo from a Collection
A method to remove a photo from one of the logged-in user's collections. Requires the ```write_collections``` scope.
```
DELETE  /collections/:collection_id/remove
```
##### Parameters

| Parameter | Type | Description | Optional | Default |
| ----- | ---- | ----------- | -------- | ------- |
| **collectionId** | *String* | The Collection ID  | no | 
| **photoId** | *String* | The Photo ID  | no | 
```java
unsplash.collections().removePhotoFromCollection("<collection-id>", "<photo-id>");
```


### Continuous Integration (CI)
This project uses Maven for build and JUnit 5 for testing. In CI, run:

```sh
mvn clean compile
mvn test
mvn package
```

A minimal GitHub Actions workflow is included in `.github/workflows/ci.yml` for automated build and test on Java 11, 17, and 21.

### Tests
LatteSplash uses `JUnit 5` as the testing framework with `Mockito` for mocking and `OkHttp MockWebServer` for HTTP-level tests. Test files are available in the `src/test/java/com/lattesplash/` folder. **95 tests** covering all API endpoints, error handling, validation, and async behavior.

### License
The MIT License

Copyright (c) 2018- Sandeep  Vattapparambil, http://www.sandeepv.in

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.


### Acknowledgements
Thanks, and Kudos to team [Unsplash](https://unsplash.com/) for creating a wonderful platform for sharing 
beautiful high quality free images and photos.

Port of [wrapsplash](https://github.com/SandeepVattapparambil/wrapsplash) npm module by [Sandeep Vattapparambil](https://github.com/SandeepVattapparambil).
