package com.whatsapp;

import com.whatsapp.contact.k;

/* synthetic */ class ak {
    static final int[] a;

    static {
        a = new int[k.values().length];
        try {
            a[k.UP_TO_DATE.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[k.IN_PROGRESS.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[k.FAILED.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[k.DELAYED.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            a[k.NETWORK_UNAVAILABLE.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            a[k.USER_IS_EXPIRED.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
    }
}
