package fr.ferfoui.america2goat.unit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitSpinnersConfiguration {

    public static void configureSpinners(Context context, Spinner inputSpinner, Spinner outputSpinner,
                                         int inputSpinnerPosition, int outputSpinnerPosition, OnUnitSelectedListener listener) {
        List<CharSequence> unitAbbreviations = getUnitAbbreviations(context);

        // The spinners need an ArrayAdapter to display items
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitAbbreviations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputSpinner.setAdapter(adapter);
        inputSpinner.setSelection(inputSpinnerPosition);
        outputSpinner.setAdapter(adapter);
        outputSpinner.setSelection(outputSpinnerPosition);

        inputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, true));
        outputSpinner.setOnItemSelectedListener(createUnitSpinnerListener(listener, false));
    }

    private static List<CharSequence> getUnitAbbreviations(Context context) {
        return Arrays.stream(Unit.values())
                .map(Unit::getResourceAbbreviationId)
                .map(context::getString)
                .collect(Collectors.toList());
    }

    private static AdapterView.OnItemSelectedListener createUnitSpinnerListener(OnUnitSelectedListener listener, boolean isInput) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Unit.values().length <= position) {
                    throw new IllegalStateException("Unexpected value: " + position);
                }

                listener.onUnitSelected(position, isInput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public interface OnUnitSelectedListener {
        void onUnitSelected(int unitOrdinal, boolean isInput);
    }

}
