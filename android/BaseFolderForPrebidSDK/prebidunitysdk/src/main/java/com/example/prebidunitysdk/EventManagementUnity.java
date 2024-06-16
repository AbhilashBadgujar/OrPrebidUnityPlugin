package com.example.prebidunitysdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;

import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.BannerParameters;
import org.prebid.mobile.ExternalUserId;
import org.prebid.mobile.Host;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.Signals;
import org.prebid.mobile.addendum.AdViewUtils;
import org.prebid.mobile.addendum.PbFindSizeError;
import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.api.rendering.BannerView;


import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import com.example.prebidunitysdk.databinding.ActivityDemoBinding;

public class EventManagementUnity extends Activity {

    private static final String AD_UNIT_ID = "/21808260008/prebid_demo_app_original_api_banner";
    private static final String CONFIG_ID = "prebid-demo-banner-320-50";
    private static final int WIDTH = 320;
    private static final int HEIGHT = 50;

    public static  BannerAdUnit adUnit;
    public static  ActivityDemoBinding binding;
    public static  FrameLayout frameLayout;
    public static  AdManagerAdView gamView;
    private static Context baseContext;
    public static boolean showAds;
    private Context adContext;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        frameLayout = binding.frameAdWrapper;


    }


    public void PrintString(Context context, final String message) {
        baseContext = context;
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            InitPrebid(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.activity_demo, null);
            layout.setVisibility(View.VISIBLE);

            frameLayout = layout.findViewById(R.id.frameAdWrapper);

            frameLayout.setVisibility(View.VISIBLE);

            if (context instanceof Activity) {
                activity = (Activity) context;
                activity.addContentView(layout, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                Log.e("EventManagementUnity", "Context is not an instance of Activity");
            }
        });
    }

    public void InitPrebid(Context context) {
        PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
        PrebidMobile.setCustomStatusEndpoint("https://prebid-server-test-j.prebid.org/status");
        PrebidMobile.setPrebidServerHost(
                Host.createCustomHost("https://prebid-server-test-j.prebid.org/openrtb2/auction")
        );
        PrebidMobile.initializeSdk(context, status -> {
            if (status == InitializationStatus.SUCCEEDED) {
                Toast.makeText(context, "SDK initialized successfully!", Toast.LENGTH_SHORT).show();
                initPrebidExternalUserIds(context);
            } else {
                Toast.makeText(context, "SDK initialization error: " + status.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initPrebidExternalUserIds(Context context) {
        ArrayList<ExternalUserId> externalUserIdArray = new ArrayList<>();
        externalUserIdArray.add(new ExternalUserId("adserver.org", "111111111111", null, new HashMap<String, Object>() {{
            put("rtiPartner", "TDID");
        }}));
        externalUserIdArray.add(new ExternalUserId("netid.de", "999888777", null, null));
        externalUserIdArray.add(new ExternalUserId("criteo.com", "_fl7bV96WjZsbiUyQnJlQ3g4ckh5a1N", null, null));
        externalUserIdArray.add(new ExternalUserId("liveramp.com", "AjfowMv4ZHZQJFM8TpiUnYEyA81Vdgg", null, null));
        externalUserIdArray.add(new ExternalUserId("sharedid.org", "111111111111", 1, new HashMap<String, Object>() {{
            put("third", "01ERJWE5FS4RAZKG6SKQ3ZYSKV");
        }}));
        PrebidMobile.setExternalUserIds(externalUserIdArray);



    }

    public void createAd(Context context) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (context != null) {
                adUnit = new BannerAdUnit(CONFIG_ID, WIDTH, HEIGHT);
                adContext = context;
                BannerParameters parameters = new BannerParameters();
                parameters.setApi(Collections.singletonList(org.prebid.mobile.Signals.Api.MRAID_2));
                adUnit.setBannerParameters(parameters);

                gamView = new AdManagerAdView(context);
                gamView.setAdUnitId(AD_UNIT_ID);
                gamView.setAdSizes(new AdSize(WIDTH, HEIGHT));
                gamView.setTag("gamView");
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.activity_demo, null);
                layout.setVisibility(View.VISIBLE);

                frameLayout = layout.findViewById(R.id.frameAdWrapper);
                frameLayout.addView(gamView);
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.addContentView(layout, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    Log.e("EventManagementUnity", "Context is not an instance of Activity");
                }
                gamView.setAdListener(createListener(gamView));

                final AdManagerAdRequest.Builder builder = new AdManagerAdRequest.Builder();
                adUnit.setAutoRefreshInterval(30);

                adUnit.fetchDemand(builder, resultCode -> {
                    Log.e("TAG", "Ad demand fetched with result code: " + resultCode);
                    AdManagerAdRequest request = builder.build();
                    gamView.loadAd(request);
                });
            } else {
                Log.e("TAG", "Context is null, cannot create ad");
            }
        });
    }

    public void HideAdView(Context context) {

        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (frameLayout != null) {
                    frameLayout.removeAllViews(); // Remove all views within frameLayout
                    frameLayout.setVisibility(View.GONE); // Hide the frameLayout
                }
                if (gamView != null) {
                    gamView.setVisibility(View.GONE); // Properly dispose of the ad view
                }
                Toast.makeText(context, "Ad hidden", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(context, "Ad hidden", Toast.LENGTH_SHORT).show();

    }

    public void ShowAdView(Context context) {
        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (frameLayout != null) {
                    frameLayout.removeAllViews(); // Remove all views within frameLayout
                    frameLayout.setVisibility(View.VISIBLE); // Hide the frameLayout
                }
                if (gamView != null) {
                    //gamView.setVisibility(View.VISIBLE); // Properly dispose of the ad view
                    createAd(context);
                    Toast.makeText(context, "Ad showen", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public AdManagerAdView getGamView(){
        return gamView;
    }

    public AdListener createListener(final AdManagerAdView gamView) {
        return new AdListener() {
            @Override
            public void onAdLoaded() {
                AdViewUtils.findPrebidCreativeSize(gamView, new AdViewUtils.PbFindSizeListener() {
                    @Override
                    public void success(
                            int width,
                            int height
                    ) {
                        gamView.setAdSizes(new AdSize(width, height));
                    }

                    @Override
                    public void failure(@NonNull PbFindSizeError error) {
                    }
                });
                gamView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("Prebid", "Ad failed to load: " + adError.getMessage());
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
                gamView.setVisibility(View.GONE);
            }
        };
    }
}
