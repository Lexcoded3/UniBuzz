# Unibuzz

**Unibuzz** is a modern, native Android campus marketplace and events app built for university students. Designed with a sleek, Netflix-inspired dark & light design system using **Kotlin** and **Jetpack Compose**.

---

## 🌟 Features & Highlights

### 📱 Five Main Application Tabs
1. **Items Marketplace (`ItemListScreen`)**
   - Hero spotlight banner showcasing featured marketplace items.
   - Live search bar for instant keyword filtering.
   - Category filter chips (*Books, Tech, Fashion, Sports, Room Items, Other*).
   - Trending marketplace deals & recently posted student listings.
   - Quick heart bookmarking and price badge tags.

2. **Campus Events (`EventListScreen`)**
   - Featured campus event spotlight banner.
   - Live search for events, venues, and organizers.
   - Event type filter chips (*Society, Guild, Party, Concert, Sports, Other*).
   - Upcoming campus events feed with venue and date tags.
   - Bookmark button with spring animations.

3. **Category Explorer (`CategoryScreen`)**
   - Browse listings filtered by campus department & item category.
   - Quick category pills and filtered feed views.

4. **Event Types (`TypeScreen`)**
   - Filter campus happenings by event type and organizer category.

5. **Campus Savings (`SavingsScreen`)**
   - Financial hub & budgeting preview tailored for university students.
   - Pulsing hero card with interactive launch notification sign-up.
   - Feature previews: *Student Goal Vaults*, *Verified Campus Deals*, *Group Buy Pools*, and *Marketplace Cashback*.

---

### 👤 User Account & Authentication
- **Student Authentication (`LoginScreen` & `RegisterScreen`)**:
  - Register with full name, student email, campus handle, and password.
  - Room-backed authentication with handle/email verification.
- **Account Dashboard (`AccountScreen`)**:
  - Student profile view with verified campus student badge.
  - Activity overview (*Listings posted*, *Event RSVPs*).
  - Built-in **App Theme Selector** (*Dark*, *Light*, *System*).
  - Quick access to settings, support, and session logout.

---

### ➕ Posting & Item Details
- **Post Item Listing (`PostItemScreen`)**:
  - Multi-photo picker (up to 4 images with live preview and delete options).
  - Item details (*title, description, price or FREE giveaway toggle, condition, category, pickup location hint, contact info*).
- **Post Event (`PostEventScreen`)**:
  - Event setup (*title, description, event type, venue, organizer, date/time picker, contact info, poster upload*).
- **Detail Views (`ItemDetailScreen` & `EventDetailScreen`)**:
  - Full hero banners with gradient overlays.
  - Price/Date tags, location/venue information cards, and direct contact actions.

---

### 🎨 Design & Dynamic Theming
- **Material 3 Design System**:
  - Adaptive **Dark Mode** (*Netflix Obsidian Dark*) and **Light Mode** (*Studio Clean Slate*).
  - Dynamic status bar and navigation bar light/dark appearance.
  - Custom animations: *Corona loading splash screen pulse*, *bounceOnClick* press feedback, *heart/bookmark scale animations*.
  - Full **Edge-to-Edge** window support.

---

## 🏗 Architecture & Stack

- **Language**: [Kotlin 1.9.22](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Database & Persistence**: [Room 2.6.1](https://developer.android.com/training/data-storage/room) with pre-populated campus seed data & `SharedPreferences` for user/theme preferences
- **Image Loading**: [Coil 2.5.0](https://coil-kt.github.io/coil/)
- **Architecture**: MVVM / Local-First with Kotlin Coroutines & `StateFlow`

---

## 📁 Project Structure

```
Unibuzz/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── app/
    ├── build.gradle.kts
    └── src/main/
        ├── AndroidManifest.xml
        └── java/com/unibuzz/campusapp/
            ├── Application.kt
            ├── MainActivity.kt
            ├── data/
            │   ├── local/
            │   │   ├── AppDatabase.kt       # Room Database (Items, Events, Users)
            │   │   ├── AuthManager.kt       # Auth Session Management
            │   │   └── ThemeManager.kt      # Light / Dark Theme Preferences
            │   └── model/
            │       ├── Event.kt             # Event Entity
            │       ├── Item.kt              # Marketplace Item Entity
            │       └── User.kt              # User Profile Entity
            └── ui/
                ├── App.kt                   # Navigation Host & Main App Scaffold
                ├── components/
                │   └── Interactivity.kt     # Animations & Touch Effects
                ├── screens/
                │   ├── AccountScreen.kt     # User Profile & Theme Switcher
                │   ├── CategoryScreen.kt    # Category Browser
                │   ├── EventDetailScreen.kt  # Event Details
                │   ├── EventListScreen.kt    # Events Feed & Search
                │   ├── ItemDetailScreen.kt   # Item Details
                │   ├── ItemListScreen.kt     # Marketplace Feed & Search
                │   ├── LoginScreen.kt        # Student Sign In
                │   ├── PostEventScreen.kt    # Post Campus Event
                │   ├── PostItemScreen.kt     # Post Marketplace Listing
                │   ├── RegisterScreen.kt     # Student Account Registration
                │   ├── SavingsScreen.kt      # Campus Savings & Budgeting Hub
                │   └── TypeScreen.kt         # Event Type Browser
                └── theme/
                    ├── Color.kt             # Dynamic Material 3 Color Schemes
                    ├── Theme.kt             # Unibuzz App Theme
                    └── Typography.kt        # M3 Typography Styles
```

---

## 🚀 Getting Started

1. **Prerequisites**:
   - Install **Android Studio** (Jellyfish or newer recommended).
   - Android SDK with `compileSdk 34` (Android 14).
2. **Setup**:
   - Open the `Unibuzz` folder in Android Studio.
   - Sync the Gradle project.
3. **Run**:
   - Connect a physical Android device via USB debugging or use an Android Virtual Device (AVD).
   - Click **Run `app`**.
