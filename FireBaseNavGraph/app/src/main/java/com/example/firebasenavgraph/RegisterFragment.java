package com.example.firebasenavgraph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {

    private EditText emailET, passwordET, nameET, telephoneET;
    private Button registerBT;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        emailET = view.findViewById(R.id.emailEditText);
        passwordET = view.findViewById(R.id.passwordEditText);
        nameET = view.findViewById(R.id.nameEditText);
        telephoneET = view.findViewById(R.id.telephoneEditText);
        registerBT = view.findViewById(R.id.registerButton);
        registerBT.setOnClickListener(view12 -> tryRegister());
        return view;
    }

    private void tryRegister() {
        if (isEmpty(emailET) || isEmpty(passwordET) || isEmpty(nameET) || isEmpty(telephoneET)) {
            Toast.makeText(requireContext(), "Must fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireActivity(), "Usered registerd", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        NavHostFragment navHostFragment =
                                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        NavController navController = navHostFragment.getNavController();
                        navController.navigateUp();
                    } else {
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

}