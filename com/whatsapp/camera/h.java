package com.whatsapp.camera;

import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.whispersystems.libaxolotl.ay;

class h implements OnEditorActionListener {
    private static final String z;
    final CameraActivity a;

    static {
        char[] toCharArray = "\u0006vxJ|0umK`\u0000|".toCharArray();
        int length = toCharArray.length;
        char[] cArr = toCharArray;
        for (int i = 0; length > i; i++) {
            int i2;
            char c = cArr[i];
            switch (i % 5) {
                case PBE.MD5 /*0*/:
                    i2 = 111;
                    break;
                case ay.f /*1*/:
                    i2 = 24;
                    break;
                case ay.n /*2*/:
                    i2 = 8;
                    break;
                case ay.p /*3*/:
                    i2 = 63;
                    break;
                default:
                    i2 = 8;
                    break;
            }
            cArr[i] = (char) (i2 ^ c);
        }
        z = new String(cArr).intern();
    }

    h(CameraActivity cameraActivity) {
        this.a = cameraActivity;
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent == null || keyEvent.getKeyCode() != 66) {
            return false;
        }
        ((InputMethodManager) this.a.getSystemService(z)).hideSoftInputFromWindow(CameraActivity.a(this.a).getWindowToken(), 0);
        return true;
    }
}
