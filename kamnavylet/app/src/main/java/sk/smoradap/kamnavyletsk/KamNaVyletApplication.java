package sk.smoradap.kamnavyletsk;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by smora on 27.12.2016.
 */
public class KamNaVyletApplication extends Application {


    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
