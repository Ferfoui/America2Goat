package fr.ferfoui.america2goat.data.store;

import androidx.datastore.preferences.core.Preferences;

/**
 * Interface for data storage operations.
 */
public interface DataStorage {

    /**
     * Set a value in the storage.
     *
     * @param storageKey the key of the value
     * @param value      the value to store
     * @param <T>        the type of the value
     */
    <T> void setData(Preferences.Key<T> storageKey, T value);

    /**
     * Get a value from the storage.
     *
     * @param storageKey the key of the value
     * @param <T>        the type of the value
     * @return the value stored
     */
    <T> T getData(Preferences.Key<T> storageKey);

    /**
     * Check if a value is stored in the storage.
     *
     * @param storageKey the key of the value
     * @return true if the value is stored, false otherwise
     */
    boolean isDataAvailable(Preferences.Key<?> storageKey);

}