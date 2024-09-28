package fr.ferfoui.america2goat.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.R;
import fr.ferfoui.america2goat.databinding.FragmentDashboardBinding;
import fr.ferfoui.america2goat.injection.ViewModelFactory;
import fr.ferfoui.america2goat.unit.UnitSpinnersConfiguration;

/**
 * Fragment representing the dashboard screen.
 */
public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    /**
     * Called to do initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(DashboardViewModel.class);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view.
     *
     * @param view               The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureSpinners();
        configureSeekBar();
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Configures the spinners in the dashboard.
     */
    private void configureSpinners() {
        Spinner unitTypeSpinner = binding.unitTypeSpinner;
        Spinner inputUnitSpinner = binding.inputUnitPreferenceSpinner;
        Spinner outputUnitSpinner = binding.outputUnitPreferenceSpinner;

        UnitSpinnersConfiguration.configureUnitTypeSpinner(requireContext(), unitTypeSpinner,
                viewModel.getUnitTypePreference(), this::changeUnitType);

        UnitSpinnersConfiguration.configureUnitSpinners(requireContext(), inputUnitSpinner, outputUnitSpinner,
                viewModel.getInputUnitPreference(), viewModel.getOutputUnitPreference(),
                viewModel.getCurrentUnits(), createOnUnitSelectedListener());

        viewModel.getInputUnitLiveData().observe(getViewLifecycleOwner(), inputUnitOrdinal -> {
            if (binding.inputUnitPreferenceSpinner.getSelectedItemPosition() != inputUnitOrdinal)
                binding.inputUnitPreferenceSpinner.setSelection(inputUnitOrdinal);
        });

        viewModel.getOutputUnitLiveData().observe(getViewLifecycleOwner(), outputUnitOrdinal -> {
            if (binding.outputUnitPreferenceSpinner.getSelectedItemPosition() != outputUnitOrdinal)
                binding.outputUnitPreferenceSpinner.setSelection(outputUnitOrdinal);
        });
    }

    /**
     * Configures the seek bar in the dashboard.
     */
    private void configureSeekBar() {
        SeekBar roundPreferenceSeekBar = binding.roundPreferenceSeekBar;

        int barPosition = viewModel.getSeekBarPosition();

        roundPreferenceSeekBar.setProgress(barPosition);
        roundPreferenceSeekBar.setOnSeekBarChangeListener(createSeekBarListener());
        setRoundPreferenceText(barPosition);
    }

    /**
     * Changes the unit type preference.
     *
     * @param unitType The new unit type.
     */
    private void changeUnitType(String unitType) {
        viewModel.setUnitTypePreference(unitType);

        UnitSpinnersConfiguration.refreshUnitSpinners(requireContext(), binding.inputUnitPreferenceSpinner,
                binding.outputUnitPreferenceSpinner, viewModel.getInputUnitPreference(), viewModel.getOutputUnitPreference(),
                viewModel.getCurrentUnits(), createOnUnitSelectedListener());
    }

    /**
     * Creates a listener for unit selection changes.
     *
     * @return The listener for unit selection changes.
     */
    private UnitSpinnersConfiguration.OnUnitSelectedListener createOnUnitSelectedListener() {
        return (unitOrdinal, isInput) -> {
            if (isInput) {
                viewModel.setInputUnitPreference(unitOrdinal);
            } else {
                viewModel.setOutputUnitPreference(unitOrdinal);
            }
        };
    }

    /**
     * Creates a listener for seek bar changes.
     *
     * @return The listener for seek bar changes.
     */
    private SeekBar.OnSeekBarChangeListener createSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setRoundPreference(progress);

                setRoundPreferenceText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        };
    }

    /**
     * Sets the text for the round preference based on the seek bar progress.
     *
     * @param progress The current progress of the seek bar.
     */
    private void setRoundPreferenceText(int progress) {
        String roundPreferenceText;
        if (progress >= getResources().getInteger(R.integer.round_seek_bar_max)) {
            roundPreferenceText = getString(R.string.no_rounding);
        } else {
            roundPreferenceText = String.valueOf(progress);
        }

        binding.selectedRoundPreferenceText.setText(roundPreferenceText);
    }
}