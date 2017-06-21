package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.model.DetailType;

/**
 * Created by Peter Smorada on 10.09.2016.
 */
public class DetailsTable extends LinearLayout {

    public static final String TAG = DetailsTable.class.getSimpleName();

    public DetailsTable(Context context) {
        super(context);
    }



    public DetailsTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailsTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void add(String paramName, String paramValue, final DetailType type){

        View v = inflate(getContext(), R.layout.details_info_row,  null);
        TextView name = (TextView) v.findViewById(R.id.tv_param_name);
        name.setText(paramName);
        TextView value = (TextView) v.findViewById(R.id.tv_param_value);
        value.setText(paramValue);

        LinearLayout linearLayout;
        if(getChildCount() == 0 || ((LinearLayout)getChildAt(getChildCount() -1)).getChildCount() == 2){
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(HORIZONTAL);
            addView(linearLayout);

        } else {
            linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        v.setLayoutParams(params);

        linearLayout.addView(v);
        Log.v(TAG, "Adding layout " + paramName + " " + paramName);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(type);
            }
        });
    }

}
