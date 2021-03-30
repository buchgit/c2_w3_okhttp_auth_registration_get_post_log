package com.example.c2_w3_okhttp_auth;

import androidx.fragment.app.Fragment;

/*
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.9.0'
implementation 'io.reactivex.rxjava2:rxjava:2.2.9'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

in ApiUtils -> getRetrofitClient()
.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

in ServerApi -> Completable registration()


 */

public class AuthActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AuthFragment.newInstance();
    }
}
