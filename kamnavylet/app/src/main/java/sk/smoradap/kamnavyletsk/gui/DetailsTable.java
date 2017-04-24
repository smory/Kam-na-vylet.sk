package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sk.smoradap.kamnavyletsk.R;

/**
 * Created by Peter Smorada on 10.09.2016.
 */
public class DetailsTable extends LinearLayout {

    public DetailsTable(Context context) {
        super(context);
    }

    public DetailsTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailsTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addRow(String paramName, String paramValue){
        hideSeparator();

        View v = inflate(getContext(), R.layout.details_info_row,  null);
        TextView name = (TextView) v.findViewById(R.id.tv_param_name);
        name.setText(paramName);
        TextView value = (TextView) v.findViewById(R.id.tv_param_value);
        value.setText(paramValue);
        addView(v);
    }

    private void hideSeparator(){
        if(getChildCount() > 0) {
            View lastView = getChildAt(getChildCount() -1);
            lastView.findViewById(R.id.separator_line).setVisibility(VISIBLE);
        }
    }
}
