package com.farmwiseai.tnauagricart;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.json.JSONObject;

import java.io.InputStream;

public class UpdateChecker {
    private Context context;
   public String latestVersion,currentVersion;
   public boolean forceUpdate;
    public UpdateChecker(Context context) {
        this.context = context;
    }

    public boolean checkForUpdate() {
        try {
            // Read the JSON file from the assets folder
            String json = loadJSONFromAssets();
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                 latestVersion = jsonObject.getString("latest_version");
                 forceUpdate = jsonObject.getBoolean("force_update");

                //    String currentVersion =  BuildConfig.VERSION_NAME;
                 currentVersion = getCurrentVersion();
                // Check if the current version is outdated
             /*   if (isUpdateRequired(currentVersion, latestVersion)) {
                    if (forceUpdate) {
                        showForceUpdateDialog();
                    } else {
                       // showOptionalUpdateDialog();
                    }
                }else {

                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdateRequired(currentVersion, latestVersion);
    }


    private String getCurrentVersion() {
        String versionName = "";
        try {
            PackageInfo packageInfo = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private String loadJSONFromAssets() {
        String json = null;
        try {
            // Load the JSON file
            InputStream inputStream = context.getAssets().open("version.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private boolean isUpdateRequired(String currentVersion, String latestVersion) {
        return !currentVersion.equals(latestVersion);
    }

    public void showForceUpdateDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Update Required")
                .setMessage("A new version of the app is available. Please update to continue using the app.")
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        redirectToPlayStore();
                    }
                })
                .show();
    }

    public void showOptionalUpdateDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Update Available")
                .setMessage("A new version of the app is available. Would you like to update?")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        redirectToPlayStore();
                    }
                })
                .setNegativeButton("Later", null)
                .show();
    }

    private void redirectToPlayStore() {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}



