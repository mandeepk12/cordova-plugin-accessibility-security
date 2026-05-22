package com.betasoft.cordova.plugin.accessibilitysecurityplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Build;
import android.view.View;

public class AccessibilitySecurityPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("setScreenSensitivity")) {
            boolean isSensitive = args.getBoolean(0);
            this.toggleSensitivity(isSensitive, callbackContext);
            return true;
        }
        return false;
    }

    private void toggleSensitivity(final boolean isSensitive, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    View webViewEngineView = webView.getView();

                    // Tapjacking protection for older platforms (Android 9+)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        webViewEngineView.setFilterTouchesWhenObscured(isSensitive);
                    }

                    // Android 16+ Accessibility Data Protection (API level 36)
                    if (Build.VERSION.SDK_INT >= 36) { 
                        webViewEngineView.setAccessibilityDataSensitive(
                            isSensitive ? View.ACCESSIBILITY_DATA_SENSITIVE_YES : View.ACCESSIBILITY_DATA_SENSITIVE_NO
                        );
                    }

                    callbackContext.success("Sensitivity set to: " + isSensitive);
                } catch (Exception e) {
                    callbackContext.error("Error setting safety flags: " + e.getMessage());
                }
            }
        });
    }
}
