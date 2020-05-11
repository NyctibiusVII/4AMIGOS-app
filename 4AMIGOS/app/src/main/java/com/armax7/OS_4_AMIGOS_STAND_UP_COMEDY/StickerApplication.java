/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.app.Application;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

public class StickerApplication extends Application {
    private AppInterface appInterface;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        String
          f = getString(R.string.nd1), b = getString(R.string.nd2oibb), g = getString(R.string.nifelsed3), d = getString(R.string.nd4)
        , e = getString(R.string.nd5), a = getString(R.string.nd6), c = getString(R.string.a7hggc ), h = getString(R.string.b8jnbc )
        , i = getString(R.string.esta ), q = getString(R.string.tentando), m = getString(R.string.fazer), l = getString(R.string.alguma)
        , k = getString(R.string.sequencia_kk), n = getString(R.string.h14), o = getString(R.string.i15xama), p = getString(R.string.j16)
        , j = getString(R.string.k17null), z = getString(R.string.l18), nd3 = getString(R.string.m19), t = getString(R.string.n20)
        , u = getString(R.string.o21), v = getString(R.string.p22), y = getString(R.string.q23), nd2 = getString(R.string.r24)
        , w = getString(R.string.s25kkk), r = getString(R.string.t26)
                , nd6 = getString(R.string.u27), nd5 = getString(R.string.v285388)
                , nd4 = getString(R.string.w29), s = getString(R.string.x30kjgdf)
                , x = getString(R.string.pra_q), nd1 = getString(R.string.z32);

        String instaBUG =
               f.concat(b).concat(g).concat(d)
                .concat(e).concat(a).concat(c)
                .concat(h).concat(i).concat(q)
                .concat(m).concat(l).concat(k)
                .concat(nd3).concat(o).concat(p)
                .concat(j).concat(z).concat(s)
                .concat(t).concat(u).concat(v)
                .concat(y).concat(nd2).concat(w)
                .concat(r)
                .concat(nd6).concat(nd5)
                .concat(nd4).concat(s)
                .concat(x).concat(nd1);

        //variável instaBug só serve para saber onde fica meu armazenamento para o envio de bugs
        Instabug.Builder builder = new Instabug.Builder(this, instaBUG);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.setInvocationEvents(
                        InstabugInvocationEvent.FLOATING_BUTTON
                        /*,InstabugInvocationEvent.SCREENSHOT*/);
                builder.build();
                Instabug.setPrimaryColor(getColor(R.color.azulDark4A));
            }
        }, 4000);//aparece ao mesmo tempo que acaba de carregar o splash screen.


    }
    public void something(AppInterface appInterface) {
        this.appInterface = appInterface;

        //para passar dados, use este método como...
        appInterface.result();
    }
}
