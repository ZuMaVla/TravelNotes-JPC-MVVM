# Travel Notes - Android Application
​
 ## Overview
​
 Travel Notes is a personal travel application for Android. It allows users to create, manage, and browse their travel memories, each with a title, description, date, images, location, and rating. 
 All users can view public places, while private places are visible only to their owners. 
 The application also supports a guest mode, where unauthenticated users can view, filter and sort public places.
 Although originally designed for documenting travel experiences, the app's flexible structure makes it equally suitable for recording and showing information about various types of places, 
 such as local garages, hair salons, restaurants, or any other points of interest within a single city.

​
 ## Features
​
 - **User Authentication**: 
	 - Simple and effective login/sign-up system. If a user doesn't exist, an account is automatically created.
	 - Using a Google account.
 - **CRUD Operations**: Full Create, Read, Update, and Delete functionality for travel notes (except read, only for authenticated users).
 - **Rich Travel Notes**: Each note can include:
   - A title and description.
   - Images uploadable from the user's device gallery.
   - A specific date.
   - A rating from 1 to 5.
   - Map view enabled, but not yet functionally incorporated (work for future version).
 - **List Sorting**: A handy pop-up menu allows users to:
   - Sort notes by name.
   - Sort notes by date (descending).
   - Sort notes by rating (descending).
 - **List Filtering**: A top bar drop-down menu allows users to:
   - Show all users' public notes (Show Public).
   - Show all notes of the current user (Show My Places).
   - Show all notes available to the current user (Show Available, default view).
 - **Dynamic UI**: 
	 - The UI adapts based on the user's state (logged-in user vs. guest).
	 - Certain actions (add, edit, delete, long-press actions) are hidden for guest users.
   - Places are editable only by owners (even if public).
   - Only authorised users can vote.
​
 ## Architecture
​
 This application is built following the **Model–View–ViewModel (MVVM)** architectural pattern to ensure a clean separation of concerns, making the codebase scalable and easy to maintain.
​
 - **Model**: Represents application data (Place). Data is stored in Firebase Firestore and Firebase Storage.
 - **View**: Implemented using Jetpack Compose (JPC – Jetpack Compose) and displays UI state and forwards user actions to the ViewModel.
 - **ViewModel**: Holds UI state using StateFlow, contains business logic, communicates with repositories and exposes state to the View.
 - **Repository**: Handles data operations and acts as a single source of truth between Firebase and ViewModels.
​
 ## Technical Details
​
 - **Language**: 100% [Kotlin](https://kotlinlang.org/).
 - **Minimum Android Version:** Android 11 (API Level 30)
 - **Target Android Version:** Android 16 (API Level 36)
 - **UI**: Jetpack Compose (JPC).
 - **Architecture**: Model–View–ViewModel (MVVM).
 - **Firebase Authentication**: user login.
 - **Firebase Firestore**: storing places data.
 - **Firebase Storage**: storing images.
 - **Firestore Security Rules**: are used to control access for non-authorised and authorised users.
 - **Image Loading**: Images are displayed efficiently using the [Coil](https://coil-kt.github.io/coil/) library.
 - **Logging**: [Timber](https://github.com/JakeWharton/timber) is used for clear and organised logging.
 - **Maps**: [Google Maps Platform](https://developers.google.com/maps/documentation/android-sdk) not used a lot for last version 2.0.2.
​
 ## How to Run
​
 1.  Clone the repository.
 2.  Open the project in Android Studio.
 3.  Connect Firebase to the project.
 4.  Obtain a Google Maps API key from the Google Cloud Console.
 5.  Add your API key to the `app/src/main/res/values/keys.xml` file.
 6.  Build and run the application on an Android device or emulator.