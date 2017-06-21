package sk.smoradap.kamnavyletsk.utils;

import sk.smoradap.kamnavyletsk.KamNaVyletApplication;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.model.DetailType;

/**
 * Created by Peter Smorada on 19.6.2017.
 */

public class StringResolver {

    public static String getDetailDescription(DetailType type) {
        int resourceId = -1;
        switch (type) {
            case TELEPHONE:
                resourceId = R.string.detail_phone;
                break;
            case EMAIL:
                resourceId = R.string.detail_email;
                break;
            case WEB_SITE:
                resourceId = R.string.detail_web_site;
                break;
            case ADDRESS:
                resourceId = R.string.detail_address;
                break;
            case GPS:
                resourceId = R.string.detail_gps;
                break;
            case AREA:
                resourceId = R.string.detail_area;
                break;
            case DISTRICT:
                resourceId = R.string.detail_district;
                break;
            case TOWN:
                resourceId = R.string.detail_town;
                break;
            case REGION:
                resourceId = R.string.detail_region;
                break;
            case NAME:
                resourceId = R.string.detail_name;
                break;
            case CATEGORY:
                resourceId = R.string.detail_category;
                break;
        }

        if(resourceId != -1) {
            return KamNaVyletApplication.getApplication().getString(resourceId);
        }
        return null;
    }
}
