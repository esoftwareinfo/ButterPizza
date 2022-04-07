package butter.maxican.pizza;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;


public class AppOpenManager_Exclude implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private static boolean isShowingAd = false;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final Application myApplication;

    String AC1 = "", AC2 = "", AC3 = "", AC4 = "", AC5 = "";

    
    public AppOpenManager_Exclude(Application myApplication, String Ac1, String Ac2, String Ac3, String Ac4, String Ac5) {
        this.myApplication = myApplication;

        this.AC1 = Ac1;
        this.AC2 = Ac2;
        this.AC3 = Ac3;
        this.AC4 = Ac4;
        this.AC5 = Ac5;

        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);


    }

    
    public void showAdIfAvailable() {
        
        
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            
                            AppOpenManager_Exclude.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.show(currentActivity, fullScreenContentCallback);

        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }


    
    public void fetchAd() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Pizza.Server_Yes_No == 1 || Pizza.Server_Yes_No == 0) {

                    if (Pizza.Exit_Menu_Decided==100){

                        return;
                    }

                    
                    if (isAdAvailable()) {
                        return;
                    }

                    loadCallback =
                            new AppOpenAd.AppOpenAdLoadCallback() {
                                
                                @Override
                                public void onAppOpenAdLoaded(AppOpenAd ad) {
                                    AppOpenManager_Exclude.this.appOpenAd = ad;
                                }

                                
                                @Override
                                public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                                    
                                }

                            };
                    AdRequest request = getAdRequest();
                    AppOpenAd.load(
                            myApplication, Butter.getapp_open(myApplication), request,
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

                } else {

                    handler.postDelayed(this, 1000);

                }

            }

        }, 1000);


    }


    
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    
    @OnLifecycleEvent(ON_START)
    public void onStart() {

        if ((currentActivity.getLocalClassName()).equals(AC1) ||
                (currentActivity.getLocalClassName()).equals(AC2) ||
                (currentActivity.getLocalClassName()).equals(AC3) ||
                (currentActivity.getLocalClassName()).equals(AC4) ||
                (currentActivity.getLocalClassName()).equals(AC5)) {


        } else {
            showAdIfAvailable();
        }



    }


    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

}

