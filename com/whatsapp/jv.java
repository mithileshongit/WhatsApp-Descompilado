package com.whatsapp;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class jv implements OnClickListener {
    final ContactPicker a;

    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.removeDialog(4);
    }

    jv(ContactPicker contactPicker) {
        this.a = contactPicker;
    }
}
