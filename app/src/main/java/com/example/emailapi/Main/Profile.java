package com.example.emailapi.Main;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emailapi.DAO.UserDAO;
import com.example.emailapi.Entity.User;


import com.example.emailapi.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name;
    Button button1;

    Button SignOut1, infoButton, submit1, deleteB, searchAct;
    EditText inputName, inputAge, inputFT, inputLS;
    TextView viewEmail;
    ImageView animalExplore, Profile1, Filter1, Find1, HomeMain1;

    public String email1;
    public String userId;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    DatabaseReference dr;

    boolean isEditTextVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        SignOut1 = findViewById(R.id.SignOut);
        infoButton = findViewById(R.id.addInfoButton);
        submit1 = findViewById(R.id.submitButton);
        deleteB = findViewById(R.id.deleteUser);
        searchAct = findViewById(R.id.Search);

        animalExplore = findViewById(R.id.ExploreAnimal);
        Profile1 = findViewById(R.id.Profile);
        Filter1 = findViewById(R.id.Filter);
        Find1 = findViewById(R.id.Find);
        HomeMain1 = findViewById(R.id.HomeMain);

        inputName = findViewById(R.id.NameEdit);
        inputAge = findViewById(R.id.AgeEdit);
        inputFT = findViewById(R.id.freeTimeEdit);
        inputLS = findViewById(R.id.lifestyleEdit);

        if (currentUser != null) {
            email1 = currentUser.getEmail();
            userId = currentUser.getUid();
        }

        sideNavMenu();

        searchAct.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Filtering.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });

        SignOut1.setOnClickListener(view -> {
            Toast.makeText(Profile.this, "User signed out", Toast.LENGTH_SHORT).show();
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, LogIn.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });

        infoButton.setOnClickListener(view -> {
            isItOrg(userId);
        });

        deleteB.setOnClickListener(view -> {
            confirmDialog();
        });
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAnimal();
                        deleteUser();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteAnimal() {
        DatabaseReference animalRef = FirebaseDatabase.getInstance().getReference("Animal");
        Query animalQuery = animalRef.orderByChild("usid").equalTo(userId);

        animalQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot animalSnapshot : snapshot.getChildren()) {
                    String animalKey = animalSnapshot.getKey();
                    assert animalKey != null; // Might not be necessary
                    animalRef.child(animalKey).removeValue();
                }
                Toast.makeText(Profile.this, "Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Failed to delete animals", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sideNavMenu() {
        animalExplore.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Create.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        Filter1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Filtering.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        Find1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, FindActivity.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        HomeMain1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Home.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }

    private void isItOrg(String userId) {
        dr = FirebaseDatabase.getInstance().getReference("User");

        dr.child(userId).child("organisation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean x = Boolean.TRUE.equals(snapshot.getValue(boolean.class));
                updateUser(x);
                submitUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Email Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitUser() {
        submit1.setOnClickListener(view -> {
            User person1 = new User(inputName.getText().toString(), inputAge.getText().toString(), inputLS.getText().toString(), inputFT.getText().toString(), email1, userId, true);
            UserDAO uDAO = new UserDAO(person1, userId);
            Toast.makeText(Profile.this, "Updated Data", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUser(boolean x) {
        if (!isEditTextVisible && !x) {
            inputName.setVisibility(View.VISIBLE);
            inputAge.setVisibility(View.VISIBLE);
            inputFT.setVisibility(View.VISIBLE);
            inputLS.setVisibility(View.VISIBLE);
            submit1.setVisibility(View.VISIBLE);
            isEditTextVisible = true;
        } else if (!isEditTextVisible && x) {
            inputName.setVisibility(View.VISIBLE);
            inputAge.setVisibility(View.INVISIBLE); // Important
            inputFT.setVisibility(View.VISIBLE);
            inputLS.setVisibility(View.VISIBLE);
            submit1.setVisibility(View.VISIBLE);
            isEditTextVisible = true;
        } else if (isEditTextVisible) {
            inputName.setVisibility(View.INVISIBLE);
            inputAge.setVisibility(View.INVISIBLE);
            inputFT.setVisibility(View.INVISIBLE);
            inputLS.setVisibility(View.INVISIBLE);
            submit1.setVisibility(View.INVISIBLE);
            isEditTextVisible = false;
        }
    }

    private void deleteUser() {
        if (currentUser != null) {
            // Delete the user from Firebase Authentication
            currentUser.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("myActivity", "User account deleted.");
                        } else {
                            Log.d("myActivity", "Failed to delete user account.");
                        }
                    });

            dr = FirebaseDatabase.getInstance().getReference("User");
            dr.child(currentUser.getUid()).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("myActivity", "User data deleted.");
                        } else {
                            Log.d("myActivity", "Failed to delete user data.");
                        }
                    });
        }
        Toast.makeText(Profile.this, "User Data has been deleted", Toast.LENGTH_SHORT).show();
        Toast.makeText(Profile.this, "Back to Login Page", Toast.LENGTH_SHORT).show();
        Bundle bundle1 = new Bundle();
        Intent intent1 = new Intent(Profile.this, LogIn.class);
        intent1.putExtras(bundle1);
        startActivity(intent1);
    }
}