package com.whatsapp;

import android.view.View;
import android.view.View.OnClickListener;
import com.whatsapp.messaging.MessageService;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.whispersystems.libaxolotl.ay;

class _v implements OnClickListener {
    private static final String z;
    final Advanced a;

    static {
        char[] toCharArray = "7\u0007:\fU+\u0007:\u0017".toCharArray();
        int length = toCharArray.length;
        char[] cArr = toCharArray;
        for (int i = 0; length > i; i++) {
            int i2;
            char c = cArr[i];
            switch (i % 5) {
                case PBE.MD5 /*0*/:
                    i2 = 69;
                    break;
                case ay.f /*1*/:
                    i2 = 98;
                    break;
                case ay.n /*2*/:
                    i2 = 89;
                    break;
                case ay.p /*3*/:
                    i2 = 99;
                    break;
                default:
                    i2 = 59;
                    break;
            }
            cArr[i] = (char) (i2 ^ c);
        }
        z = new String(cArr).intern();
    }

    public void onClick(View view) {
        App.v(z);
        MessageService.a(this.a, true);
    }

    _v(Advanced advanced) {
        this.a = advanced;
    }
}
