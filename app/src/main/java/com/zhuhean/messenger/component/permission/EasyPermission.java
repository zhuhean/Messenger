package com.zhuhean.messenger.component.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class EasyPermission {

    private static final String TAG = EasyPermission.class.getSimpleName();

    public static final int SYSTEM_ALERT_WINDOW_PERMISSION_REQ_CODE = 1971;
    public static final int WRITE_SETTINGS_PERMISSION_REQ_CODE = 1970;

    private static ArrayList<PermissionRequest> permissionRequests = new ArrayList<PermissionRequest>();

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     */
    public static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the Activity has access to given permissions.
     */
    public static boolean hasPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Returns true if the Activity has access to a all given permission.
     */
    public static boolean hasPermission(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /*
     * If we override other methods, lets do it as well, and keep name same as it is already weird enough.
     * Returns true if we should show explanation why we need this permission.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity,
                                                               String permissions) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions);
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment,
                                                               String permissions) {
        return fragment.shouldShowRequestPermissionRationale(permissions);
    }

    public static boolean shouldShowRequestPermissionRationale(android.app.Fragment fragment,
                                                               String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return fragment.shouldShowRequestPermissionRationale(permissions);
        }
        return false;
    }

    public static void askFor(Activity activity, String permission,
                              PermissionCallback permissionCallback) {
        askFor(activity, new String[]{permission}, permissionCallback);
    }

    public static void askFor(Activity activity, PermissionCallback permissionCallback, String... permissions) {
        askFor(activity, permissions, permissionCallback);
    }

    public static void askFor(Activity activity, String[] permissions,
                              PermissionCallback permissionCallback) {
        if (permissionCallback == null) {
            return;
        }
        if (hasPermission(activity, permissions)) {
            permissionCallback.permissionGranted();
            return;
        }
        PermissionRequest permissionRequest =
                new PermissionRequest(new ArrayList<String>(Arrays.asList(permissions)),
                        permissionCallback);
        permissionRequests.add(permissionRequest);

        ActivityCompat.requestPermissions(activity, permissions, permissionRequest.getRequestCode());
    }

    /**
     * There are a couple of permissions that don't behave like normal and dangerous permissions.
     * SYSTEM_ALERT_WINDOW and WRITE_SETTINGS are particularly sensitive, so most apps should not use
     * them.
     * If an app needs one of these permissions, it must declare the permission in the manifest, and
     * send an intent requesting the user's authorization.
     * The system responds to the intent by showing a detailed management screen to the user.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("ValidFragment")
    public static void askForSpecialPermission(final Activity activity, String permission,
                                               final PermissionCallback permissionCallback) {
        android.app.Fragment fragment;
        android.app.FragmentTransaction fragmentTransaction;

        if (permissionCallback == null) {
            return;
        }

        switch (permission) {
            case Manifest.permission.SYSTEM_ALERT_WINDOW:
                if (Settings.canDrawOverlays(activity)) {
                    permissionCallback.permissionGranted();
                    return;
                }
                fragment = new android.app.Fragment() {
                    @Override
                    public void onAttach(Context context) {
                        super.onAttach(context);
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION_REQ_CODE);
                    }

                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (requestCode == SYSTEM_ALERT_WINDOW_PERMISSION_REQ_CODE) {
                            if (Settings.canDrawOverlays(activity)) {
                                permissionCallback.permissionGranted();
                                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                            } else {
                                permissionCallback.permissionRefused();
                                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                            }
                        }
                        super.onActivityResult(requestCode, resultCode, data);
                    }
                };
                fragmentTransaction = activity.getFragmentManager().beginTransaction();
                fragmentTransaction.add(fragment, "getpermission");
                fragmentTransaction.commit();
                break;
            case Manifest.permission.WRITE_SETTINGS:
                if (Settings.System.canWrite(activity)) {
                    permissionCallback.permissionGranted();
                    return;
                }
                fragment = new android.app.Fragment() {
                    @Override
                    public void onAttach(Context context) {
                        super.onAttach(context);
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + context.getPackageName()));
                        startActivityForResult(intent, WRITE_SETTINGS_PERMISSION_REQ_CODE);
                    }

                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (requestCode == WRITE_SETTINGS_PERMISSION_REQ_CODE) {
                            if (Settings.System.canWrite(activity)) {
                                permissionCallback.permissionGranted();
                                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                            } else {
                                permissionCallback.permissionRefused();
                                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                            }
                        }
                        super.onActivityResult(requestCode, resultCode, data);
                    }
                };
                fragmentTransaction = activity.getFragmentManager().beginTransaction();
                fragmentTransaction.add(fragment, "getpermission");
                fragmentTransaction.commit();
                break;
        }
    }

    public static void askFor(Fragment fragment, String permission,
                              PermissionCallback permissionCallback) {
        askFor(fragment, new String[]{permission}, permissionCallback);
    }

    public static void askFor(android.app.Fragment fragment, String permission,
                              PermissionCallback permissionCallback) {
        askFor(fragment, new String[]{permission}, permissionCallback);
    }

    private static void askFor(Fragment fragment, String[] permissions,
                               PermissionCallback permissionCallback) {
        if (permissionCallback == null) {
            return;
        }
        if (hasPermission(fragment.getActivity(), permissions)) {
            permissionCallback.permissionGranted();
            return;
        }
        PermissionRequest permissionRequest =
                new PermissionRequest(new ArrayList<String>(Arrays.asList(permissions)),
                        permissionCallback);
        permissionRequests.add(permissionRequest);

        fragment.requestPermissions(permissions, permissionRequest.getRequestCode());
    }

    private static void askFor(android.app.Fragment fragment, String[] permissions,
                               PermissionCallback permissionCallback) {
        if (permissionCallback == null) {
            return;
        }
        if (hasPermission(fragment.getActivity(), permissions)) {
            permissionCallback.permissionGranted();
            return;
        }
        PermissionRequest permissionRequest =
                new PermissionRequest(new ArrayList<String>(Arrays.asList(permissions)),
                        permissionCallback);
        permissionRequests.add(permissionRequest);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.requestPermissions(permissions, permissionRequest.getRequestCode());
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                  int[] grantResults) {
        PermissionRequest requestResult = new PermissionRequest(requestCode);
        if (permissionRequests.contains(requestResult)) {
            PermissionRequest permissionRequest =
                    permissionRequests.get(permissionRequests.indexOf(requestResult));
            if (verifyPermissions(grantResults)) {
                //Permission has been granted
                permissionRequest.getPermissionCallback().permissionGranted();
            } else {
                permissionRequest.getPermissionCallback().permissionRefused();
            }
            permissionRequests.remove(requestResult);
        }
    }

}
