package com.example.c2_w3_okhttp_auth;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class RegistrationFragment extends Fragment {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private EditText mName;
    private Button mRegistration;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private final View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputValid()) {
                User user = new User(
                        mEmail.getText().toString()
                        , mName.getText().toString()
                        , mPassword.getText().toString());

                Request request = new Request.Builder()
                        .url(BuildConfig.SERVER_URL.concat("registration"))
                        .post(RequestBody.create(JSON, new Gson().toJson(user)))
                        .build();

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {

                    final Handler handler = new Handler(Objects.requireNonNull(getActivity()).getMainLooper());

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showMessage(R.string.login_register_error);
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    showMessage(R.string.login_register_success);
                                    assert getFragmentManager() != null;
                                    getFragmentManager().popBackStack();
                                } else {
                                    //todo написать детальную обработку ошибок
                                    showMessage(R.string.login_register_error);
                                }
                            }
                        });
                    }
                });
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_registration, container, false);

        mEmail = view.findViewById(R.id.etEmail);
        mPassword = view.findViewById(R.id.etPassword);
        mPasswordAgain = view.findViewById(R.id.tvPasswordAgain);
        mRegistration = view.findViewById(R.id.btnRegistration);
        mName = view.findViewById(R.id.etName);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);

        return view;
    }

    private boolean isInputValid() {
        String email = mEmail.getText().toString();
        if (isEmailValid(email) && isPasswordsValid()) {
            return true;
        }

        return false;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordsValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

}
