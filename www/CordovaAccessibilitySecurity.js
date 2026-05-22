var exec = require('cordova/exec');

var AccessibilitySecurity = {
    setScreenSensitivity: function(isSensitive, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'AccessibilitySecurityPlugin', 'setScreenSensitivity', [isSensitive]);
    }
};

module.exports = AccessibilitySecurity;
