package fr.ferfoui.america2goat.injection;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import fr.ferfoui.america2goat.data.conversion.Converter;
import fr.ferfoui.america2goat.data.conversion.ConverterRepository;
import fr.ferfoui.america2goat.data.settings.AppSettings;
import fr.ferfoui.america2goat.data.settings.SettingsRepository;
import fr.ferfoui.america2goat.ui.dashboard.DashboardViewModel;
import fr.ferfoui.america2goat.ui.home.HomeViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;
    private final ConverterRepository converterRepository;
    private final SettingsRepository settingsRepository;

    private ViewModelFactory(Context context) {
        Converter converter = new Converter();
        AppSettings appSettings = AppSettings.getInstance(context);

        this.converterRepository = new ConverterRepository(converter);
        this.settingsRepository = new SettingsRepository(appSettings);
    }

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

    @Override
    @NotNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(converterRepository, settingsRepository);
        }
        if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            return (T) new DashboardViewModel(settingsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
