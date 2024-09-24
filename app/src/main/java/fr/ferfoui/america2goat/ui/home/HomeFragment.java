package fr.ferfoui.america2goat.ui.home;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.ferfoui.america2goat.databinding.FragmentHomeBinding;
import fr.ferfoui.america2goat.injection.ViewModelFactory;
import fr.ferfoui.america2goat.text.TextChangedWatcher;
import fr.ferfoui.america2goat.unit.Unit;
import fr.ferfoui.america2goat.unit.UnitSpinnersConfiguration;
import fr.ferfoui.america2goat.unit.UnitType;

/**
 * Fragment for the Home screen.
 * Manages the UI and interactions for the home screen.
 */
public class HomeFragment extends Fragment {

    // Position of the input unit spinner
    int inputSpinnerPosition;
    // Position of the output unit spinner
    int outputSpinnerPosition;

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private UnitType currentUnitType;

    /**
     * Called when the fragment is created.
     * Initializes the ViewModel and current unit type.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HomeViewModel.class);
        currentUnitType = viewModel.getCurrentUnitType();
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved instance state.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    /**
     * Called immediately after onCreateView has returned.
     * Configures the input EditText, observes LiveData, and sets up the spinners and swap button.
     *
     * @param view               The view returned by onCreateView.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureInputEditText();
        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), binding.outputLengthText::setText);

        configureSpinners();
        configureSwapButton();
    }

    /**
     * Called when the view hierarchy associated with the fragment is being removed.
     * Cleans up the binding.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Configures the input EditText, including setting filters and adding a text change listener.
     */
    private void configureInputEditText() {
        EditText inputEditText = binding.inputEditText;

        addFiltersToInputEditText(inputEditText);
        inputEditText.setHint(viewModel.getCurrentUnitType().getResourceNameId());

        inputEditText.addTextChangedListener((TextChangedWatcher) text -> {
            if (text.toString().isEmpty()) {
                viewModel.convert(0d);
                return;
            }
            viewModel.convert(Double.parseDouble(text.toString()));
        });

        viewModel.getChangedInputValueLiveData().observe(getViewLifecycleOwner(), changedInputValue -> {
            if (viewModel.getCurrentInputValue() == 0d) {
                binding.inputEditText.setText("");
                return;
            }
            binding.inputEditText.setText(String.valueOf(viewModel.getCurrentInputValue()));
            binding.inputEditText.setSelection(binding.inputEditText.getText().length());
        });
    }

    /**
     * Configures the input and output unit spinners, including setting up observers for LiveData.
     */
    private void configureSpinners() {
        Spinner inputUnitSpinner = binding.inputUnitSpinner;
        Spinner outputUnitSpinner = binding.outputUnitSpinner;

        inputSpinnerPosition = viewModel.getInputUnit().ordinal();
        outputSpinnerPosition = viewModel.getOutputUnit().ordinal();

        UnitSpinnersConfiguration.configureUnitSpinners(requireContext(), inputUnitSpinner, outputUnitSpinner,
                inputSpinnerPosition, outputSpinnerPosition, viewModel.getCurrentUnits(), this::setCurrentUnit);

        observeSpinnersLiveData(inputUnitSpinner, outputUnitSpinner);
    }

    /**
     * Configures the swap button to swap the input and output units and values.
     */
    private void configureSwapButton() {
        binding.swapButton.setOnClickListener(v -> viewModel.swapUnitsAndValues());
    }

    /**
     * Adds filters to the input EditText to handle decimal input and limit the length.
     *
     * @param inputEditText The input EditText to add filters to.
     */
    private void addFiltersToInputEditText(EditText inputEditText) {
        InputFilter commaDecimalTextFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);

                if (Character.isDigit(character) || character == '.')
                    continue;

                if (character == ',')
                    return ".";

                return "";
            }
            return null;
        };

        InputFilter lengthFilter = new InputFilter.LengthFilter(10);
        inputEditText.setFilters(new InputFilter[]{commaDecimalTextFilter, lengthFilter});
    }

    /**
     * Observes LiveData for the input and output unit spinners.
     * Updates the spinner selections and handles changes in unit type.
     *
     * @param inputUnitSpinner  The spinner for selecting the input unit.
     * @param outputUnitSpinner The spinner for selecting the output unit.
     */
    private void observeSpinnersLiveData(Spinner inputUnitSpinner, Spinner outputUnitSpinner) {
        viewModel.getInputUnitOrdinalLiveData().observe(getViewLifecycleOwner(), inputUnitOrdinal -> {
            if (inputUnitSpinner.getSelectedItemPosition() != inputUnitOrdinal)
                inputUnitSpinner.setSelection(inputUnitOrdinal);
        });

        viewModel.getOutputUnitOrdinalLiveData().observe(getViewLifecycleOwner(), outputUnitOrdinal -> {
            if (outputUnitSpinner.getSelectedItemPosition() != outputUnitOrdinal)
                outputUnitSpinner.setSelection(outputUnitOrdinal);
        });

        viewModel.getChangedUnitTypeLiveData().observe(getViewLifecycleOwner(), unitType -> {
            if (unitType != currentUnitType) {
                currentUnitType = unitType;

                viewModel.resetInputValue();
                Unit[] currentUsedUnits = unitType.getUnits();

                inputSpinnerPosition = viewModel.getInputUnit().ordinal();
                outputSpinnerPosition = viewModel.getOutputUnit().ordinal();

                binding.inputEditText.setHint(unitType.getResourceNameId());

                changeUnitText(currentUsedUnits);
                UnitSpinnersConfiguration.refreshUnitSpinners(requireContext(), inputUnitSpinner, outputUnitSpinner,
                        inputSpinnerPosition, outputSpinnerPosition, currentUsedUnits, this::setCurrentUnit);
            }
        });
    }

    /**
     * Sets the current unit based on the given ordinal and whether it is the input unit.
     *
     * @param unitOrdinal The ordinal of the unit to set.
     * @param isInput     Whether the unit is the input unit.
     */
    private void setCurrentUnit(int unitOrdinal, boolean isInput) {
        if (isInput) {
            viewModel.setInputUnit(unitOrdinal);
            inputSpinnerPosition = unitOrdinal;
        } else {
            viewModel.setOutputUnit(unitOrdinal);
            outputSpinnerPosition = unitOrdinal;
        }

        changeUnitText(viewModel.getCurrentUnits());
    }

    /**
     * Updates the text of the input and output unit TextViews based on the current units.
     *
     * @param currentUsedUnits The current units to display.
     */
    private void changeUnitText(Unit[] currentUsedUnits) {
        binding.inputUnitText.setText(currentUsedUnits[inputSpinnerPosition].getResourceNameId());
        binding.outputUnitText.setText(currentUsedUnits[outputSpinnerPosition].getResourceNameId());
    }
}