package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sk.smoradap.kamnavyletsk.R;

/**
 * Created by smora on 10.09.2016.
 */
public class DetailsTable extends LinearLayout {

    //LinearLayout linearLayout;

    public DetailsTable(Context context) {
        super(context);
        //createLinearLayout(context);
    }

    public DetailsTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        //createLinearLayout(context);
    }

    public DetailsTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //createLinearLayout(context);
    }

//    private void createLinearLayout(Context context){
//        linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        this.addView(linearLayout);
//
//    }

    public void addRow(String paramName, String paramValue){
        View v = inflate(getContext(), R.layout.details_info_row,  null);
        TextView name = (TextView) v.findViewById(R.id.param_name);
        name.setText(paramName);
        TextView value = (TextView) v.findViewById(R.id.param_detail);
        value.setText(paramValue);
        addView(v);
    }
}
