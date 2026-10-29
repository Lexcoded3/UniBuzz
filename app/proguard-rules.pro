# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the SDK tools.

# Keep Room entities simple for v1.
-keep class com.unibuzz.campusapp.data.model.** { *; }

# Keep Coil okhttp related classes if needed later.
-keep class coil.** { *; }
