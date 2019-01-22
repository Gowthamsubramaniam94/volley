package com.angler.volley.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angler.volley.R;


public class ProgressDialog extends Dialog {

    private ImageView myImageView;
    private Context myContext;
    private TextView myLoadingTxt;
    private ProgressBar myProgressWheel;

    public ProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        myContext = context;
        try {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setContentView(R.layout.custom_progress_dialog_box);
            this.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            myLoadingTxt = (TextView) this
                    .findViewById(R.id.custom_dialog_box_TXT_loading);
            myProgressWheel = (ProgressBar) this
                    .findViewById(R.id.custom_dialog_box_progressBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
