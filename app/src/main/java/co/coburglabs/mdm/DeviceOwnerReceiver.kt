package co.coburglabs.mdm

// https://github.com/googlearchive/android-DeviceOwner/blob/master/Application/src/main/java/com/example/android/deviceowner/DeviceOwnerReceiver.java

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService

/*
class DeviceOwnerReceiver : DeviceAdminReceiver() {

    private val TAG = "DeviceOwnerReceiver"

    @Override
    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        val manager = context.getSystemService(ComponentActivity.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(context.applicationContext, DeviceOwnerReceiver::class.java)

        manager.setProfileName(componentName, context.getString(R.string.profile_name))

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @Override
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // TODO update composable UI message to "App is device owner"
        Log.d(TAG, "App is device owner")
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence? {
        return super.onDisableRequested(context, intent)
        // TODO - this doesn't seem to be getting called when a seemingly successful
        // `adb shell dpm remove-active-admin co.coburglabs.mdm/.DeviceOwnerReceiver`
        // command is issued. See logcat. Could be a "testOnly" flag issue?
    }
}*/

class DeviceOwnerReceiver : DeviceAdminReceiver() {

    private val TAG = "DeviceOwnerReceiver"

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)

        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                as DevicePolicyManager

        val admin = ComponentName(context, DeviceOwnerReceiver::class.java)

        // Optional: set device name shown in system UI
        dpm.setProfileName(admin, context.getString(R.string.profile_name))

        // Launch main app after provisioning
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(launchIntent)

        Log.d(TAG, "Device Owner provisioning completed")
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence? {
        // Device Owner cannot be removed by the user
        return "This app is the device owner and cannot be removed."
    }
}
