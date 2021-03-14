package com.example.c2_w3_okhttp_auth;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.c2_w3_okhttp_auth.models.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";

    private TextView mEmail;
    private TextView mName;
    private User mUser;

    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mEmail = findViewById(R.id.tvEmail);
        mName = findViewById(R.id.tvName);

        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.get(USER_KEY);
        mEmail.setText(mUser.getEmail());
        mName.setText(mUser.getName());

    }
}
