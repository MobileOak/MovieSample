# Movie Listing App

This is a sample movie listing app. It makes a network call to get the most popular movies and then
displays it in a list. Each item on the list is clickable and takes the user to a detailed page
with a (non-functional) "Buy Tickets" button.

I made the following decisions when writing it:

1. Kotlin
Android development is clearly moving towards Kotlin and away from Java. Kotlin provides better
   protection against Null Pointer Exceptions than Java as well as a more readable syntax that
   eliminates boilerplate code.
   
2. Retrofit
While the official documentation for Android uses Volley for network requests, Retrofit is more
   widely used. While not completely used in this example, Retrofit is set up to work more
   seamlessly with Kotlin coroutines than Volley is.
   
3. Glide
There are other newer alternatives for loading images, but Glide is extremely easy to use.
   
4. MVVM
Until recently there has not been a clear winner in terms of code architecture on Android. Various
   companies tried different approaches (MVP, MVC, RIBS, etc), but lately the industry has settled
   on MVVM as the de facto standard. This approach allows business logic to reside in the ViewModel
   which allows for more easily unit-tested business logic.
   
5. Dagger/Hilt Injection
While Dagger was easy to use for Java-based back-end servers, in Android it was more difficult,
   because Android hides access to constructors, which makes it difficult to inject objects directly
   Hilt (and the Kotlin view models library) largely fixes that with annotations that make it 
   easier for Android developers to use it. Since the Injection is done at compile time, this makes
   it superior, in my opinion to KOIN.
   
# Testing
This app is simple enough that there isn't much to test. There isn't really much in the way of
business logic in the Model. If I was working with QA on this, I would make sure they tested that
network outages wouldn't crash the app, and that the images and data loaded properly.