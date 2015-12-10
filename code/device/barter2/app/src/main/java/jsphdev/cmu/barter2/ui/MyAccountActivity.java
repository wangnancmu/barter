package jsphdev.cmu.barter2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import jsphdev.cmu.barter2.R;
import jsphdev.cmu.barter2.entities.User;

public class MyAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Button myPostButton = (Button) findViewById(R.id.myPost);
        myPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent submit = new Intent(view.getContext(), MyPostActivity.class);
                startActivity(submit);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.myAccount);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCurrentUser();
                finish();
            }
        });

        User user = getCurrentUser();
        if (user == null) {
            Intent submit = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(submit);
            finish();
            return;
        }

        TextView nameTextView = (TextView) findViewById(R.id.textViewNameInMyAccount);
        nameTextView.setText(user.getName());

        TextView emailTextView = (TextView) findViewById(R.id.textViewEmailInMyAccount);
        emailTextView.setText(user.getEmail());

        TextView phoneTextView = (TextView) findViewById(R.id.textViewPhoneInMyAccount);
        phoneTextView.setText(user.getPhone());
    }

    private User getCurrentUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userJson = preferences.getString("CurrentUser", "");
        if (userJson.isEmpty() || userJson.equals("")) {
            return null;
        }

        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        return user;
    }

    private void clearCurrentUser() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userJson = preferences.getString("CurrentUser", "");
        preferences.edit().clear().commit();
    }
}
