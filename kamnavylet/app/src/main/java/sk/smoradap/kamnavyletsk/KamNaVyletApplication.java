package sk.smoradap.kamnavyletsk;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Application na inicializaciu crashlytics a leak canary
 * Created by psmorada on 27.12.2016.
 */
public class KamNaVyletApplication extends Application {


    private static KamNaVyletApplication application;

    @Override
    public void onCreate(){
        super.onCreate();
        application = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }

    public static KamNaVyletApplication getApplication() {
        return application;
    }
}
