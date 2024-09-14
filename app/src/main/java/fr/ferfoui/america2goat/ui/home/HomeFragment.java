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

public class HomeFragment extends Fragment {

    int inputSpinnerPosition;
    int outputSpinnerPosition;
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private UnitType currentUnitType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HomeViewModel.class);
        currentUnitType = viewModel.getCurrentUnitType();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureInputEditText();
        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), binding.outputLengthText::setText);

        configureSpinners();
        configureSwapButton();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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

    private void configureSpinners() {
        Spinner inputUnitSpinner = binding.inputUnitSpinner;
        Spinner outputUnitSpinner = binding.outputUnitSpinner;

        inputSpinnerPosition = viewModel.getInputUnit().ordinal();
        outputSpinnerPosition = viewModel.getOutputUnit().ordinal();

        UnitSpinnersConfiguration.configureUnitSpinners(requireContext(), inputUnitSpinner, outputUnitSpinner,
                inputSpinnerPosition, outputSpinnerPosition, viewModel.getCurrentUnits(), this::setCurrentUnit);

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

    private void configureSwapButton() {
        binding.swapButton.setOnClickListener(v -> viewModel.swapUnitsAndValues());
    }

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

    private void changeUnitText(Unit[] currentUsedUnits) {
        binding.inputUnitText.setText(currentUsedUnits[inputSpinnerPosition].getResourceNameId());
        binding.outputUnitText.setText(currentUsedUnits[outputSpinnerPosition].getResourceNameId());
    }
}