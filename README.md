#  URL shortener

This service will provide short aliases redirecting to long URLs.

## Why do we need URL shortening?

URL shortening is used to create shorter aliases for long URLs. We call these shortened aliases “short links.” Users are redirected to the original URL when they hit these short links. Short links save a lot of space when displayed, printed, messaged, or tweeted. Additionally, users are less likely to mistype shorter URLs.

For example, if we shorten this page through TinyURL:

```
http://localhost:9080/url/swagger-ui.html#/UrlManagement/createUsingPOST
```
We would get:
```
http://localhost:9080/url/1165814f
```

### Requirements and Goals of the System

* This URL shortener should have a well defined API for URLs created, including analytics of usage

* URLs can be randomly generated (via any method you choose), or specified upon creation

* No duplicate URLs are allowed to be created

* Short links can not be easily enumerated

* Short links can expire at a future time, or can live forever

* Short links can be deleted

* If a short link is created that was once deleted, it will have no "knowledge" of its previous version

### Capacity Estimation
This system will be read-heavy. There will be lots of redirection requests compared to new URL shortenings. Let’s assume a 100:1 ratio between read and write.

### System APIs

We have REST APIs to expose the functionality of the service.

create url(url)
getAllCreatedUrls()
getUrl(id)
#### Parameters:
url (string): Original URL to be shortened.
id (String) : short URL.

### Database Design
A few observations about the nature of the data we will store:
* We need to store billions of records.
* Each object we store is small (less than 1K).
* There are no relationships between records—other than storing which user created a URL.
* Our service is read-heavy. 
  
What kind of database should we use? Since we anticipate storing billions of rows, and we don’t need to use relationships between objects – a NoSQL store is a better choice

### Algorithm

The problem we are solving here is how to generate a short and unique key for a given URL.

the shortened URL is “http://localhost:9080/url/1165814f”. The last eight characters of this URL is the short key we want to generate
