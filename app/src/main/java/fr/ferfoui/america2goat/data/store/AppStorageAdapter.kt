package fr.ferfoui.america2goat.data.store

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Adapter class for managing data storage operations.
 *
 * @property appStorage The underlying storage implementation.
 */
class AppStorageAdapter(private val appStorage: AppStorage) : DataStorage {

    /**
     * Sets data in the storage.
     *
     * @param T The type of the data.
     * @param storageKey The key for the data.
     * @param value The value to be stored.
     */
    override fun <T> setData(storageKey: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO).launch { appStorage.setData(storageKey, value) }
    }

    /**
     * Retrieves data from the storage.
     *
     * @param T The type of the data.
     * @param storageKey The key for the data.
     * @return The retrieved data.
     */
    override fun <T> getData(storageKey: Preferences.Key<T>): T {
        return runBlocking {
            return@runBlocking appStorage.getData(storageKey)
        }
    }

    /**
     * Checks if data is available in the storage.
     *
     * @param storageKey The key for the data.
     * @return True if data is available, false otherwise.
     */
    override fun isDataAvailable(storageKey: Preferences.Key<*>): Boolean {
        return runBlocking {
            return@runBlocking appStorage.isDataAvailable(storageKey)
        }
    }
}