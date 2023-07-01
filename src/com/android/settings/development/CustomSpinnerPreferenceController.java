package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class CustomSpinnerPreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String CUSTOM_SPINNER_KEY = "custom_spinner";

    public CustomSpinnerPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return CUSTOM_SPINNER_KEY;
    }

    @Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String value = (String) newValue;
		updateSummary(preference, value);
		// Handle any other logic for the spinner here
		return true;
	}

	private void updateSummary(Preference preference, String value) {
		String staticDescription = "This is a custom spinner. ";
		String dynamicDescription = "";

		if ("0".equals(value)) {
		    dynamicDescription = "You selected 0. This means ..."; // Add a specific description for value 0
		} else if ("1".equals(value)) {
		    dynamicDescription = "You selected 1. This means ..."; // Add a specific description for value 1
		} else if ("2".equals(value)) {
		    dynamicDescription = "You selected 2. This means ..."; // Add a specific description for value 2
		} else {
		    dynamicDescription = "Current value is: " + value;
		}

		preference.setSummary(staticDescription + dynamicDescription);
	}

	// You should also set the initial state of the summary when the preference is displayed
	@Override
	public void updateState(Preference preference) {
		ListPreference listPreference = (ListPreference) preference;
		String value = listPreference.getValue();
		if (value == null) {
		    value = "0"; // This is the default value
		    listPreference.setValue(value);
		}
		updateSummary(preference, value);
	}

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((ListPreference) mPreference).setValue(null);
    }
}

