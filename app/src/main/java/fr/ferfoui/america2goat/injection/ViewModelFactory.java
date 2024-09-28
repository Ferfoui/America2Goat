package fr.ferfoui.america2goat.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import fr.ferfoui.america2goat.R;
import fr.ferfoui.america2goat.data.conversion.Converter;
import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.data.settings.AppSettings;
import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.ui.dashboard.DashboardViewModel;
import fr.ferfoui.america2goat.ui.home.HomeViewModel;

/**
 * Factory class for creating ViewModel instances.
 * Implements the ViewModelProvider.Factory interface.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    // Singleton instance of ViewModelFactory
    private static volatile ViewModelFactory factory;

    // Repository for handling conversion-related data
    private final ConverterRepository converterRepository;
    // Repository for handling settings-related data
    private final SettingsRepository settingsRepository;
    // Maximum value for the round seek bar in the dashboard
    private final int roundSeekBarMax;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the repositories and other dependencies.
     *
     * @param context The application context.
     */
    private ViewModelFactory(Context context) {
        Converter converter = new Converter();
        AppSettings appSettings = AppSettings.getInstance(context);
        roundSeekBarMax = context.getResources().getInteger(R.integer.round_seek_bar_max);

        this.converterRepository = new ConverterRepository(converter);
        this.settingsRepository = new SettingsRepository(appSettings);
    }

    /**
     * Returns the singleton instance of ViewModelFactory.
     * Uses double-checked locking to ensure thread safety.
     *
     * @param context The application context.
     * @return The singleton instance of ViewModelFactory.
     */
    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @param <T>        The type of the ViewModel.
     * @return A new instance of the specified ViewModel class.
     * @throws IllegalArgumentException if the ViewModel class is unknown.
     */
    @Override
    @NotNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(converterRepository, settingsRepository);
        }
        if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            return (T) new DashboardViewModel(settingsRepository, roundSeekBarMax);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}