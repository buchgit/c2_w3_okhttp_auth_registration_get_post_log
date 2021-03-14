package com.example.c2_w3_okhttp_auth;

import android.content.Intent;
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
import com.example.c2_w3_okhttp_auth.api.ApiUtils;
import com.example.c2_w3_okhttp_auth.models.User;
import com.example.c2_w3_okhttp_auth.models.UserDTO;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Objects;

public class AuthFragment extends Fragment {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mName;
    private Button mEnter;
    private Button mRegister;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {

                OkHttpClient client = ApiUtils.getBasicAuthClient(
                        mEmail.getText().toString()
                        , mPassword.getText().toString()
                        , true);

                Retrofit.Builder builder = new Retrofit.Builder();
                Retrofit retrofit = builder
                        .baseUrl(BuildConfig.SERVER_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiUtils.setRetrofitClient(retrofit);

                ApiUtils.getApi().basicAuth().enqueue(

                        new Callback<UserDTO>() {
                            final Handler handler = new Handler(Objects.requireNonNull(getActivity()).getMainLooper());

                            @Override
                            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (response.isSuccessful()) {

                                            UserDTO userDTO = response.body();
                                            User user = new User(userDTO);

                                            showMessage(R.string.auth_success);
                                            Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                                            startActivity(startProfileIntent);

                                            getActivity().finish();

                                        } else {
                                            //todo написать детальную обработку ошибок
                                            showMessage(R.string.auth_error);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<UserDTO> call, Throwable t) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage(R.string.auth_error);
                                    }
                                });
                            }
                        }
                );
            }
        }
    };

    private final View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override

        public void onClick(View view) {
            assert getFragmentManager() != null;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mEmail.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mEmail = v.findViewById(R.id.etEmail);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);
        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mName = v.findViewById(R.id.etName);

        return v;
    }
}
