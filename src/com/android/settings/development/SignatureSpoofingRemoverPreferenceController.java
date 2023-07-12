package com.android.settings.development;

import android.content.Context;

import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionInfo;

import android.os.UserHandle;
import android.os.Process;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.List;

public class SignatureSpoofingRemoverPreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String SPOOFING_PERMISSION_REMOVER_KEY = "signature_spoofing_permission_remover";
    private static final String FAKE_PACKAGE_SIGNATURE_PERMISSION = "android.permission.FAKE_PACKAGE_SIGNATURE";

    public SignatureSpoofingRemoverPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return SPOOFING_PERMISSION_REMOVER_KEY;
    }

    @Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean isEnabled = (Boolean) newValue;
        if(isEnabled) {
            revokeGetPackageSizePermission();
        }
		return true;
	}
	
	private void revokeGetPackageSizePermission() {
    PackageManager pm = mContext.getPackageManager();
    List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
    UserHandle userHandle = UserHandle.of(Process.myUserHandle().getIdentifier());
    for (PackageInfo packageInfo : packages) {
        if (packageInfo.requestedPermissions != null) {
            for (String requestedPermission : packageInfo.requestedPermissions) {
                if (requestedPermission.equals(FAKE_PACKAGE_SIGNATURE_PERMISSION)) {
                    pm.revokeRuntimePermission(packageInfo.packageName, FAKE_PACKAGE_SIGNATURE_PERMISSION, userHandle);
                    }
                }      
            }
        }
    }
}

