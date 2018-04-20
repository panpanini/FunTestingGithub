package com.mercari.annotations

/**
 * Annotation for indicating that a class should be mockable, and therefore open to the Kotlin
 * compiler. Any classes annotated @OpenForTesting will be automatically opened by the allOpen
 * plugin.
 */
annotation class OpenForTesting