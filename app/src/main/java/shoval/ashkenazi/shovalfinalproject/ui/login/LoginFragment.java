package shoval.ashkenazi.shovalfinalproject.ui.login;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;

    EditText etEmail, etPassword;

    TextView tvForgetPassword, tvRegister;

    Button btnLogin;

    FirebaseAuth myAuth;

    LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpView();

        myAuth = FirebaseAuth.getInstance();

        if (myAuth.getCurrentUser() != null)
            Navigation.findNavController(Objects.requireNonNull(container)).navigate(R.id.action_navigation_login_to_navigation_dashboard);

        return root;
    }

    public void setUpView() {
        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(this);

        etEmail = binding.etEmail;
        etPassword = binding.etPassword;

        tvForgetPassword = binding.tvForgetPassword;
        tvForgetPassword.setOnClickListener(this);
        tvRegister = binding.tvRegister;
        tvRegister.setOnClickListener(this);
        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        loginViewModel.setContextFromActivity(requireActivity(), getContext(), v, etEmail.getText().toString(), etPassword.getText().toString());
        switch (v.getId()) {
            case R.id.btnLogin:
                loginViewModel.login();
                break;
            case R.id.tvForgetPassword:
                loginViewModel.forgetPassword();
                break;

            case R.id.tvRegister:
                Navigation.findNavController(v).navigate(R.id.action_navigation_login_to_navigation_register);
                break;
        }
    }
}
