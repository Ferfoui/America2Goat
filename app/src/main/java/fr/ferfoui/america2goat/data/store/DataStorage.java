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
     * @return the value stored or null if not found
     */
    <T> T getData(Preferences.Key<T> storageKey);

}