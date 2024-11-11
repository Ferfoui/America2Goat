package fr.ferfoui.america2goat.data.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// Define keys for storing integer preferences
private val KEY_1 = intPreferencesKey("key1")
private val KEY_2 = intPreferencesKey("key2")
private val KEY_3 = intPreferencesKey("key3")

// Static instance of AppStorage for reuse
private var staticAppStorage: AppStorage? = null

/**
 * Test class for AppStorage.
 */
class AppStorageTest {

    /**
     * Retrieves the AppStorage instance, clearing data if it already exists.
     *
     * @return The AppStorage instance.
     */
    private fun getAppStorage(): AppStorage {
        if (staticAppStorage != null) {
            runBlocking { staticAppStorage!!.clearData() }
            return staticAppStorage!!
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        staticAppStorage = AppStorage(context, "test_storage")
        return staticAppStorage!!
    }

    private fun isDataExists(appStorage: AppStorage, key: Preferences.Key<Int>): Boolean {
        return runBlocking { appStorage.getData(key).first() != null }
    }

    /**
     * Tests the clearData function of AppStorage.
     */
    @Test
    fun clearDataTest() {
        val appStorage = getAppStorage()

        runBlocking {
            val key1 = KEY_1
            val key2 = KEY_2

            appStorage.setData(key1, 42)
            appStorage.setData(key2, 42)

            assertTrue(
                "The value is not stored in ${key1.name} or ${key2.name}",
                isDataExists(appStorage, key1) && isDataExists(appStorage, key2)
            )

            runBlocking { appStorage.clearData() }

            assertFalse(
                "The value is still stored in ${key1.name} or ${key2.name} after clearing the data",
                isDataExists(appStorage, key1) || isDataExists(appStorage, key2)
            )
        }
    }

    /**
     * Tests the isDataAvailable function of AppStorage.
     */
    @Test
    fun isDataAvailableTest() {
        val appStorage = getAppStorage()

        runBlocking {
            val key = KEY_1
            assertFalse(
                "A value seems to be stored in ${key.name} while it should not",
                isDataExists(appStorage, key)
            )

            appStorage.setData(key, 42)
            assertTrue(
                "The value is not available in ${key.name} while it should",
                isDataExists(appStorage, key)
            )

            val anotherKey = KEY_2
            assertFalse(
                "A value seems to be stored in ${anotherKey.name} while it should not",
                isDataExists(appStorage, anotherKey)
            )
        }
    }

    /**
     * Tests setting and getting data in AppStorage with a single key-value pair.
     */
    @Test
    fun storeSinglePairTest() {
        val appStorage = getAppStorage()

        runBlocking {
            val key = KEY_1
            val value = 42

            assertFalse(
                "A value is already assigned to ${key.name}", isDataExists(appStorage, key)
            )

            appStorage.setData(key, value)

            assertTrue(
                "The value $value is not stored in ${key.name}", isDataExists(appStorage, key)
            )

            val storedValue = appStorage.getData(key).first()

            assertTrue(
                "The stored value $storedValue is different from the expected value $value",
                storedValue == value
            )

            val anotherKey = KEY_2
            assertFalse(
                "A value is assigned to ${anotherKey.name} while it should not",
                isDataExists(appStorage, anotherKey)
            )

            assertNull(
                "A null value is not returned when trying to get a value that does not exist",
                appStorage.getData(anotherKey).first()
            )
        }
    }

    /**
     * Tests setting and getting data in AppStorage with multiple key-value pairs.
     */
    @Test
    fun storeMultiplePairsTest() {
        val appStorage = getAppStorage()

        runBlocking {
            val key1 = KEY_1
            val key2 = KEY_2
            val value1 = 42
            val value2 = 43

            assertFalse(
                "A value is already assigned to ${key1.name} or ${key2.name}",
                isDataExists(appStorage, key1) || isDataExists(appStorage, key2)
            )

            appStorage.setData(key1, value1)
            appStorage.setData(key2, value2)

            assertTrue(
                "The value $value1 is not stored in ${key1.name} or $value2 is not stored in ${key2.name}",
                isDataExists(appStorage, key1) && isDataExists(appStorage, key2)
            )

            val storedValue1 = appStorage.getData(key1).first()
            val storedValue2 = appStorage.getData(key2).first()

            assertTrue(
                "The stored value $storedValue1 is different from the expected value $value1",
                storedValue1 == value1
            )
            assertTrue(
                "The stored value $storedValue2 is different from the expected value $value2",
                storedValue2 == value2
            )

            val anotherKey = KEY_3
            assertFalse(
                "A value is assigned to ${anotherKey.name} while it should not",
                isDataExists(appStorage, anotherKey)
            )

            assertNull(
                "A null value is not returned when trying to get a value that does not exist",
                appStorage.getData(anotherKey).first()
            )
        }
    }
}