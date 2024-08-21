package fr.ferfoui.america2goat.unit;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitSpinnersConfiguration {

    public static void configureSpinners(Context context, Spinner inputSpinner, Spinner outputSpinner,
                                         int inputSpinnerPosition, int outputSpinnerPosition) {
        List<CharSequence> unitAbbreviations = getUnitAbbreviations(context);

        // The spinners need an ArrayAdapter to display items
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitAbbreviations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputSpinner.setAdapter(adapter);
        inputSpinner.setSelection(inputSpinnerPosition);
        outputSpinner.setAdapter(adapter);
        outputSpinner.setSelection(outputSpinnerPosition);
    }

    private static List<CharSequence> getUnitAbbreviations(Context context) {
        return Arrays.stream(Unit.values())
                .map(Unit::getResourceAbbreviationId)
                .map(context::getString)
                .collect(Collectors.toList());
    }

}
