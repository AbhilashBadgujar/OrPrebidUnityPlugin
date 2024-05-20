package com.example.prebidunitysdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.prebidunitysdk.databinding.ActivityDemoBinding;

import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.Host;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.api.data.InitializationStatus;

public class Alert extends Activity {
    // our native method, which will be called from Unity3D


    int returnValue = 0;

    //protected ActivityDemoBinding binding;
    private static final String AD_UNIT_ID = "/21808260008/prebid_demo_app_original_api_banner_300x250_order";
    private static final String CONFIG_ID = "prebid-demo-banner-300-250";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;

    public BannerAdUnit adUnit;
    private ActivityDemoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        //InitPrebid(getApplicationContext());
        //initPrebidExternalUserIds();


    }
    public void PrintString(Context context, final String message) {
        //create / show an android toast, with message and short duration.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
                PrebidMobile.setCustomStatusEndpoint("https://prebid-server-test-j.prebid.org/status");
                PrebidMobile.setPrebidServerHost(
                        Host.createCustomHost(
                                "https://prebid-server-test-j.prebid.org/openrtb2/auction"
                        )
                );
                /*PrebidMobile.initializeSdk(getApplicationContext(), status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                        Toast.makeText(context, "SDK initialized successfully!", Toast.LENGTH_SHORT).show();
                        returnValue = 1;

                        //Log.d("TAG", "SDK initialized successfully!");
                    } else {
                        Toast.makeText(context, "SDK initialization error: " + status.getDescription(), Toast.LENGTH_SHORT).show();
                        returnValue = 2;

                        //Log.e("TAG", "SDK initialization error: " + status.getDescription());
                    }
                });*/
            }
        });
    }

    public void test(Context context) {
        //create / show an android toast, with message and short duration.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
            }
        });
       /* PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
        PrebidMobile.setCustomStatusEndpoint("https://prebid-server-test-j.prebid.org/status");
        PrebidMobile.setPrebidServerHost(
                Host.createCustomHost(
                        "https://prebid-server-test-j.prebid.org/openrtb2/auction"
                )
        );
        PrebidMobile.initializeSdk(getApplicationContext(), status -> {
            if (status == InitializationStatus.SUCCEEDED) {
                Toast.makeText(context, "SDK initialized successfully!", Toast.LENGTH_SHORT).show();
                returnValue = 1;

                //Log.d("TAG", "SDK initialized successfully!");
            } else {
                Toast.makeText(context, "SDK initialization error: " + status.getDescription(), Toast.LENGTH_SHORT).show();
                returnValue = 2;

                //Log.e("TAG", "SDK initialization error: " + status.getDescription());
            }
        });*/

        //PrebidMobile.checkGoogleMobileAdsCompatibility(MobileAds.getVersion().toString());
        //return returnValue;
    }
}