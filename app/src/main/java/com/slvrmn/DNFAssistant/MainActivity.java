package com.slvrmn.DNFAssistant;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;

import com.slvrmn.DNFAssistant.Service.FloatingViewService;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.ScreenCaptureUtilByMediaPro;
import com.slvrmn.DNFAssistant.Tools.TessactOcr;
import com.slvrmn.DNFAssistant.Tools.Utility;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1050;
    private final String TAG = "Service";
    private MainApplication mainApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApplication = (MainApplication) getApplication();

        if (MainApplication.screenWidth == 0 || MainApplication.screenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            MainApplication.screenHeight = Math.min(dm.heightPixels, dm.widthPixels);
            MainApplication.screenWidth = Math.max(dm.heightPixels, dm.widthPixels);
            MainApplication.dpi = dm.densityDpi;
        }


        ScreenCaptureUtilByMediaPro.mProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        // init
        startActivityForResult(ScreenCaptureUtilByMediaPro.mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);

        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                //  ??????sdcard??????????????? ?????????tessactocr
                if (!TessactOcr.checkInit()) {
                    TessactOcr.Init();
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);


        // ?????????opencv
        if (!OpenCVLoader.initDebug()) {
            MLog.info("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            MLog.info("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && REQUEST_CODE == requestCode) {
            ScreenCaptureUtilByMediaPro.data = data;
            ScreenCaptureUtilByMediaPro.resultCode = resultCode;
        }
    }


    public void openDialog(View view) {
        if (!TessactOcr.checkInit()) {
            Utility.show("???????????????Please Wait!");
            return;
        }
        if(!mainApplication.checkAccessibilityService()){
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }
        // ??????????????????
        if(!ScreenCaptureUtilByMediaPro.initialized){
            ScreenCaptureUtilByMediaPro.init();
        }
        if(!FloatingViewService.isStarted){
            if (!Settings.canDrawOverlays(this)) {
                android.widget.Toast.makeText(this, "???????????????????????????", android.widget.Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1);
            } else {
                startService(new Intent(MainActivity.this, FloatingViewService.class));
            }
        }else {
            Utility.show("??????????????????");
        }
        finish();

    }


    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }


    public void openLog(View view) {


        String filePath = Environment.getExternalStorageDirectory().toString() + "/RobotHelper.log";
        File file = new File(filePath);
        Uri fileURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileURI, "text/plain");
            startActivity(intent);
            Intent.createChooser(intent, "??????????????????????????????????????????");
        } catch (ActivityNotFoundException e) {

        }
    }


    private final BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    MLog.info("OpenCV", "OpenCV loaded successfully");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };


    private void updateStatus() {
        TextView asStatus = findViewById(R.id.accessibility_status);

        asStatus.setText(mainApplication.checkAccessibilityService() ? "???????????????????????????" : "???????????????????????????");
    }


}
