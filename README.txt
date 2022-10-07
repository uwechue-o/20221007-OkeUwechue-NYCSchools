===================
DESCRIPTION
===================
Basic app that accesses the New York Schools API.

Retrieves and displays a list of schools with detailed info for each.
When user selects a particular school from the resultset, it fetches and displays SAT scores for that school.

Uses Jetpack Navigation Component architecture, Jetpack DataBinding, and RxJava/Retrofit for generating and processing
streams of observable data.

Utilizes the Single-Activity architecture.
All screens are simply fragments tied together via the navigation infrastructure.

====================
FURTHER WORK / ToDo
====================

- replace use of Observable class with PropertyChangeListener interface
- refactor all viewmodels to use same pattern as SharedViewmodel i.e. managed by Android directly
- add data paging functionality so that schools data is fetched as individual batches on demand,
  to minimize latency perceived by user for startup page
- add on-screen Back button widget for Scores fragment
- refactor entire java legacy app as Kotlin and replace XML views with Composables
