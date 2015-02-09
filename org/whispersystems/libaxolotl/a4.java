package org.whispersystems.libaxolotl;

import com.google.I;
import com.google.M;
import com.google.a1;
import com.google.aC;
import com.google.aJ;
import com.google.ax;
import com.google.b1;
import com.google.bF;
import com.google.cj;
import com.google.d0;
import com.google.eR;
import com.google.eS;
import com.google.eb;
import com.google.er;
import com.google.g2;
import java.io.InputStream;

public final class a4 extends d0 implements aD {
    public static final int ITERATION_FIELD_NUMBER = 1;
    public static eR PARSER = null;
    public static final int SEED_FIELD_NUMBER = 2;
    private static final a4 k;
    private static final long serialVersionUID = 0;
    private int e;
    private g2 f;
    private int g;
    private byte h;
    private final eS i;
    private int j;

    public static final M k() {
        return bD.A();
    }

    public static N i() {
        return N.b();
    }

    public void a(bF bFVar) {
        d();
        if ((this.j & 1) == ITERATION_FIELD_NUMBER) {
            bFVar.i(ITERATION_FIELD_NUMBER, this.g);
        }
        if ((this.j & 2) == SEED_FIELD_NUMBER) {
            bFVar.a((int) SEED_FIELD_NUMBER, this.f);
        }
        b().a(bFVar);
    }

    private a4(ax axVar) {
        super(axVar);
        this.h = (byte) -1;
        this.e = -1;
        this.i = axVar.b();
    }

    protected er b() {
        return bD.s().a(a4.class, N.class);
    }

    public final eS b() {
        return this.i;
    }

    public aJ c() {
        return m();
    }

    protected cj a(b1 b1Var) {
        return a(b1Var);
    }

    public static a4 a(InputStream inputStream) {
        return (a4) PARSER.a(inputStream);
    }

    public aJ a() {
        return e();
    }

    public cj a() {
        return m();
    }

    public static a4 a(I i, aC aCVar) {
        return (a4) PARSER.b(i, aCVar);
    }

    public static a4 a(byte[] bArr) {
        return (a4) PARSER.a(bArr);
    }

    public cj b() {
        return e();
    }

    public int d() {
        int i = this.e;
        if (i != -1) {
            return i;
        }
        i = 0;
        if ((this.j & 1) == ITERATION_FIELD_NUMBER) {
            i = 0 + bF.a((int) ITERATION_FIELD_NUMBER, this.g);
        }
        if ((this.j & 2) == SEED_FIELD_NUMBER) {
            i += bF.b((int) SEED_FIELD_NUMBER, this.f);
        }
        i += b().d();
        this.e = i;
        return i;
    }

    protected N a(b1 b1Var) {
        return new N(b1Var, null);
    }

    static boolean b() {
        return d;
    }

    public static a4 a(g2 g2Var, aC aCVar) {
        return (a4) PARSER.b(g2Var, aCVar);
    }

    public static a4 a(byte[] bArr, aC aCVar) {
        return (a4) PARSER.a(bArr, aCVar);
    }

    public static a4 a(g2 g2Var) {
        return (a4) PARSER.a(g2Var);
    }

    public eb c() {
        return h();
    }

    a4(I i, aC aCVar, bb bbVar) {
        this(i, aCVar);
    }

    public int j() {
        return this.g;
    }

    public boolean f() {
        return (this.j & 2) == SEED_FIELD_NUMBER;
    }

    public a4 h() {
        return k;
    }

    static {
        PARSER = new l();
        k = new a4(true);
        k.d();
    }

    public static N a(a4 a4Var) {
        return i().a(a4Var);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private a4(com.google.I r6, com.google.aC r7) {
        /*
        r5_this = this;
        r1 = 1;
        r0 = -1;
        r2 = org.whispersystems.libaxolotl.a6.B;
        r5.<init>();
        r5.h = r0;
        r5.e = r0;
        r5.d();
        r3 = com.google.eS.b();
        r0 = 0;
    L_0x0013:
        if (r0 != 0) goto L_0x0041;
    L_0x0015:
        r4 = r6.o();	 Catch:{ gc -> 0x0051, IOException -> 0x0066 }
        switch(r4) {
            case 0: goto L_0x004b;
            case 8: goto L_0x0025;
            case 18: goto L_0x0033;
            default: goto L_0x001c;
        };
    L_0x001c:
        r4 = r5.a(r6, r3, r7, r4);	 Catch:{ gc -> 0x004f, IOException -> 0x0066 }
        if (r4 != 0) goto L_0x003f;
    L_0x0022:
        if (r2 == 0) goto L_0x0075;
    L_0x0024:
        r0 = r1;
    L_0x0025:
        r4 = r5.j;	 Catch:{ gc -> 0x0062, IOException -> 0x0066 }
        r4 = r4 | 1;
        r5.j = r4;	 Catch:{ gc -> 0x0062, IOException -> 0x0066 }
        r4 = r6.e();	 Catch:{ gc -> 0x0062, IOException -> 0x0066 }
        r5.g = r4;	 Catch:{ gc -> 0x0062, IOException -> 0x0066 }
        if (r2 == 0) goto L_0x003f;
    L_0x0033:
        r4 = r5.j;	 Catch:{ gc -> 0x0064, IOException -> 0x0066 }
        r4 = r4 | 2;
        r5.j = r4;	 Catch:{ gc -> 0x0064, IOException -> 0x0066 }
        r4 = r6.z();	 Catch:{ gc -> 0x0064, IOException -> 0x0066 }
        r5.f = r4;	 Catch:{ gc -> 0x0064, IOException -> 0x0066 }
    L_0x003f:
        if (r2 == 0) goto L_0x0013;
    L_0x0041:
        r0 = r3.c();
        r5.i = r0;
        r5.c();
        return;
    L_0x004b:
        if (r2 == 0) goto L_0x0075;
    L_0x004d:
        r0 = r1;
        goto L_0x001c;
    L_0x004f:
        r0 = move-exception;
        throw r0;	 Catch:{ gc -> 0x0051, IOException -> 0x0066 }
    L_0x0051:
        r0 = move-exception;
        r0 = r0.a(r5);	 Catch:{ all -> 0x0057 }
        throw r0;	 Catch:{ all -> 0x0057 }
    L_0x0057:
        r0 = move-exception;
        r1 = r3.c();
        r5.i = r1;
        r5.c();
        throw r0;
    L_0x0062:
        r0 = move-exception;
        throw r0;	 Catch:{ gc -> 0x0064, IOException -> 0x0066 }
    L_0x0064:
        r0 = move-exception;
        throw r0;	 Catch:{ gc -> 0x0051, IOException -> 0x0066 }
    L_0x0066:
        r0 = move-exception;
        r1 = new com.google.gc;	 Catch:{ all -> 0x0057 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0057 }
        r1.<init>(r0);	 Catch:{ all -> 0x0057 }
        r0 = r1.a(r5);	 Catch:{ all -> 0x0057 }
        throw r0;	 Catch:{ all -> 0x0057 }
    L_0x0075:
        r0 = r1;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.whispersystems.libaxolotl.a4.<init>(com.google.I, com.google.aC):void");
    }

    public final boolean a() {
        byte b = this.h;
        if (b != -1) {
            return b == (byte) 1;
        } else {
            this.h = (byte) 1;
            return true;
        }
    }

    public eR c() {
        return PARSER;
    }

    public static a4 b(InputStream inputStream, aC aCVar) {
        return (a4) PARSER.b(inputStream, aCVar);
    }

    private a4(boolean z) {
        this.h = (byte) -1;
        this.e = -1;
        this.i = eS.e();
    }

    public a1 c() {
        return h();
    }

    static int b(a4 a4Var, int i) {
        a4Var.g = i;
        return i;
    }

    public static a4 l() {
        return k;
    }

    public N m() {
        return i();
    }

    public boolean g() {
        return (this.j & 1) == ITERATION_FIELD_NUMBER;
    }

    private void d() {
        this.g = 0;
        this.f = g2.a;
    }

    public static a4 a(InputStream inputStream, aC aCVar) {
        return (a4) PARSER.a(inputStream, aCVar);
    }

    public g2 a() {
        return this.f;
    }

    public N e() {
        return a(this);
    }

    static int a(a4 a4Var, int i) {
        a4Var.j = i;
        return i;
    }

    protected Object writeReplace() {
        return super.writeReplace();
    }

    public static a4 a(I i) {
        return (a4) PARSER.a(i);
    }

    a4(ax axVar, bb bbVar) {
        this(axVar);
    }

    static g2 a(a4 a4Var, g2 g2Var) {
        a4Var.f = g2Var;
        return g2Var;
    }

    public static a4 b(InputStream inputStream) {
        return (a4) PARSER.b(inputStream);
    }
}
