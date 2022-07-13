package com.common.framework;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

public class DialogClick extends SingleClick {
    private final Dialog dialog;
    private final DialogInterface.OnClickListener listener;
    private final int which;

    public DialogClick(Dialog dialog, DialogInterface.OnClickListener listener, int which) {
        this.dialog = dialog;
        this.listener = listener;
        this.which = which;
    }

    @Override
    protected void onDoClick(View v) {
        listener.onClick(dialog, which);
    }
}
