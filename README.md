# cordova-plugin-accessibility-security

[![npm version](https://shields.io)](https://npmjs.com)
[![Platform](https://shields.io)](https://apache.org)
[![License](https://shields.io)](LICENSE)

A high-security Apache Cordova plugin designed to protect mobile hybrid applications against malicious background accessibility scrapers and screen-overlay overlay hazards (tapjacking) on Android devices. 

This plugin leverages the native Android 16 (`API 36+`) feature `accessibilityDataSensitive` alongside foundational `setFilterTouchesWhenObscured` tracking parameters to safeguard screens handling financial records, login credentials, or user profiles.

---

## ✨ Features

* **Android 16+ Malicious Tool Blocking**: Prevents non-legitimate or uncertified background accessibility services from reading, scraping, or injecting interactions into your app's web container.
* **Legitimate Accessibility Preservation**: Fully compatible with verified native assistive technologies like **Google TalkBack** and certified password managers (`isAccessibilityTool=true`).
* **Tapjacking Protection**: Integrates overlay-detection filters (`setFilterTouchesWhenObscured`) to mitigate click-jacking attempts by malicious transparent background applications.
* **Graceful Backward Compatibility**: Safely executes on legacy Android platforms (down to Android 9 / API 28) using automated runtime feature-gating to eliminate app crashes.

---

## 📦 Installation

To add this plugin to your Cordova project directly from npm or a local path, execute the respective command in your terminal:

```bash
# Install via npm (Once published)
cordova plugin add cordova-plugin-accessibility-security

# Install via repo
cordova plugin add https://github.com/mandeepk12/cordova-plugin-accessibility-security.git
```

---

## ⚙️ Requirements & Android Configuration

To ensure your application compiles successfully with the new Android 16 variables, your workspace must target Android API Level 36 or higher. Update your main project's `config.xml` profile to include:

```xml
<widget ...>
    <preference name="android-targetSdkVersion" value="36" />
</widget>
```

---

## 🚀 API Usage & Code Examples

The plugin exposes a unified, asynchronous method available globally at `window.plugins.AccessibilitySecurity`.

### 1. Basic API Invocation
Toggle security enforcement dynamically by passing a boolean flag:

```javascript
// Enable data sensitivity protection
window.plugins.AccessibilitySecurity.setScreenSensitivity(true, 
    function(success) { console.log("WebView secured: " + success); },
    function(error) { console.error("Security failure: " + error); }
);

// Disable protection to restore default rendering parameters
window.plugins.AccessibilitySecurity.setScreenSensitivity(false, 
    function(success) { console.log("WebView protection cleared: " + success); },
    function(error) { console.error("Clearing failure: " + error); }
);
```

### 2. Form Input Monitoring (jQuery/JavaScript)
Secure specific data capture areas (e.g., Password, OTP, or CVV input fields) by attaching simple event hooks to focus loops:

```javascript
$(document).ready(function() {
    // Select security targets across your web layout
    var secureInputs = 'input[type="password"], .sensitive-financial-input, #otp-field';

    // Apply strict isolation constraints when user focuses on a sensitive element
    $(document).on('focus', secureInputs, function() {
        window.plugins.AccessibilitySecurity.setScreenSensitivity(true,
            function() { console.log("WebView marked as highly sensitive."); },
            function(err) { console.error("Failed to secure view container: ", err); }
        );
    });

    // Remove isolation constraints once focus drops away
    $(document).on('blur', secureInputs, function() {
        setTimeout(function() {
            // Confirm the user did not move focus to another adjacent sensitive field
            if (!$(document.activeElement).is(secureInputs)) {
                window.plugins.AccessibilitySecurity.setScreenSensitivity(false,
                    function() { console.log("WebView sensitivity tracking cleared."); },
                    function(err) { console.error("Failed to release view focus: ", err); }
                );
            }
        }, 50); // Minor timeout accommodates rapid focus shifts across consecutive layout fields
    });
});
```

### 3. Single Page App (SPA) Router Integration
If you are managing navigation states via an application router (e.g., custom state engines or route wrappers), trigger protection based on specific path definitions:

```javascript
function onRouteViewChanged(targetRouteId) {
    // Definitive catalog of secure paths within app scope
    var protectedRoutes = ['/login', '/payment-gateway', '/user-profile/billing'];

    var isProtected = protectedRoutes.indexOf(targetRouteId) !== -1;

    // Dynamically update the overall WebView security parameters
    window.plugins.AccessibilitySecurity.setScreenSensitivity(isProtected,
        function() { console.log("Route security state updated to: " + isProtected); },
        function(err) { console.error("Route update execution error: ", err); }
    );
}
```

---

## 🔍 Validation Checklist

Verify implementation security using the following validation pipeline:

1. **Malicious Interception Emulation (Android 16)**: Configure an uncertified background accessibility inspector or UI logging agent. Verify that screen readings or element nodes return blank payloads when browsing protected views.
2. **System Accessibility Compliance**: Turn on **Google TalkBack** on a physical test device. Ensure screen narration runs smoothly without unexpected element blocking.
3. **Regression & Baseline Verification**: Deploy the built package to a device running an older Android baseline (e.g., Android 14). Confirm application workflows operate smoothly without system crashes.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
