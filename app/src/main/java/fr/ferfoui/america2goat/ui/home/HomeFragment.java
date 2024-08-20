package fr.ferfoui.america2goat.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.ferfoui.america2goat.data.conversion.Unit;
import fr.ferfoui.america2goat.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    int inputSpinnerPosition = 1;
    int outputSpinnerPosition = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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

        viewModel.getResult().observe(getViewLifecycleOwner(), result -> binding.outputLengthText.setText(result));

        configureInputEditText();
        configureSpinners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void configureInputEditText() {
        EditText inputEditText = binding.inputLengthEditText;

        addFiltersToInputEditText(inputEditText);

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do here
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    viewModel.convert(0d);
                    return;
                }
                viewModel.convert(Double.parseDouble(s.toString()));
            }
        });
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

    private void configureSpinners() {
        Spinner inputUnitSpinner = binding.inputUnitSpinner;
        Spinner outputUnitSpinner = binding.outputUnitSpinner;

        // Create a list of units abbreviation to display in the spinners using the resource ids
        List<CharSequence> units = Arrays.stream(Unit.values())
                .map(Unit::getResourceAbbreviationId)
                .map(requireContext()::getString)
                .collect(Collectors.toList());

        // The spinners need an ArrayAdapter to display items
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputUnitSpinner.setAdapter(adapter);
        inputUnitSpinner.setSelection(inputSpinnerPosition);
        outputUnitSpinner.setAdapter(adapter);
        outputUnitSpinner.setSelection(outputSpinnerPosition);

        inputUnitSpinner.setOnItemSelectedListener(createUnitSpinnerListener(true));
        outputUnitSpinner.setOnItemSelectedListener(createUnitSpinnerListener(false));
    }

    private AdapterView.OnItemSelectedListener createUnitSpinnerListener(boolean isInput) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Unit[] units = Unit.values();
                int oldPosition;
                try {
                    Unit selectedUnit = units[position];

                    if (isInput) {
                        oldPosition = inputSpinnerPosition;
                        viewModel.setInputUnit(selectedUnit);
                        inputSpinnerPosition = position;
                    } else {
                        oldPosition = outputSpinnerPosition;
                        viewModel.setOutputUnit(selectedUnit);
                        outputSpinnerPosition = position;
                    }

                    changeUnitIfSameSelected((Spinner) parent, oldPosition);

                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IllegalStateException("Unexpected value: " + position, e);
                }

                changeUnitText();
            }

            public void changeUnitIfSameSelected(Spinner currentSpinner, int oldPosition) {

                Spinner otherSpinner = isInput ? binding.outputUnitSpinner : binding.inputUnitSpinner;

                if (currentSpinner.getSelectedItemPosition() == otherSpinner.getSelectedItemPosition()) {
                    otherSpinner.setSelection(oldPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void changeUnitText() {
        Unit[] units = Unit.values();

        binding.inputUnitText.setText(units[inputSpinnerPosition].getResourceNameId());
        binding.outputUnitText.setText(units[outputSpinnerPosition].getResourceNameId());
    }
}