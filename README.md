Just Eat Restaurant Lookup
==================================

Restaurant Lookup is an app that takes any chosen UK postcode, and returns data on the first 10 restaurants displaying the restaurant Name, Cuisines, Rating, and Address.

This app demonstrates the use of [Retrofit](https://square.github.io/retrofit/) to make REST requests to the web service, [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) to
handle the deserialization of the returned JSON to Kotlin data objects, and various basic principles used for Android App development 
including Composable functions, architecture components using ViewModel, coroutines for long-running tasks and lazy grid.

How to run
--------------

1. Only Android Studio required
2. Clone repository into Android Studio
3. Setup virtual android device (if one not already setup)
4. Run 'app'

Possible improvements
--------------

- Expand on user journey by adding homescreen with Just Eat logo and app title (e.g. Restaurant Finder), including button to navigate to main restaurant lookup page
- "Beautify" individual pages for better user experience, including logos, interface designs etc.
- Include a filter option to limit search to specific information (e.g. type of Cuisine, 5 star rating only)
- Include an option to increase or limit number of returned restaurants, allowing expanded searches