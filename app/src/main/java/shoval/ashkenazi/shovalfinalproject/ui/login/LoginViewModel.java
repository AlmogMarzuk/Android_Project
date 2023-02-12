package shoval.ashkenazi.shovalfinalproject.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import shoval.ashkenazi.shovalfinalproject.R;

public class LoginViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity requireActivity;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String email;
    private String password;
    @SuppressLint("StaticFieldLeak")
    private View view;

    public void setContextFromActivity(Activity requireActivity, Context context, View view, String email, String password) {
        this.requireActivity = requireActivity;
        this.context = context;
        this.email = email;
        this.password = password;
        this.view = view;
    }

    public LoginViewModel() {
    }

    public boolean isEmptyData() {
        if (email != null && password != null)
            if (email.trim().length() == 0)
                return password.trim().length() == 0;
        return false;
    }

    public void login() {
        if (!isEmptyData()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity, task -> {
                if (task.isSuccessful()) {
                    Navigation.findNavController(view).navigate(R.id.action_navigation_login_to_navigation_dashboard);
                } else
                    Toast.makeText(context, "Incorrect data!", Toast.LENGTH_SHORT).show();

            });
        } else {
            Toast.makeText(context, "Enter all data!", Toast.LENGTH_LONG).show();
        }
    }

    public void forgetPassword() {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.forget_password_dialog);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.setCancelable(true);

        final EditText etEmailForgot = d.findViewById(R.id.etEmailForget);
        final Button btnSendForget = d.findViewById(R.id.btnForget);

        btnSendForget.setOnClickListener(v -> {
            if (etEmailForgot.getText().toString().trim().length() != 0) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(etEmailForgot.getText().toString().trim() + "")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) { // אם המייל תקין
                                Toast.makeText(context, "The mail send to your email", Toast.LENGTH_SHORT).show();
                                d.dismiss();
                            } else {
                                Toast.makeText(context, "Invalid email", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else
                Toast.makeText(context, "Enter your email!", Toast.LENGTH_SHORT).show();
        });

        int width = (int) (requireActivity.getResources().getDisplayMetrics().widthPixels * 0.90); // הרוחב של הדיאלוג יהיה 90 אחוז מאורך המסך
        int height = (ViewGroup.LayoutParams.WRAP_CONTENT); // לעשות שהגובה של הדיאלוג יהיה הכי קטן שאפשר כל עוד הכל נכנס
        d.getWindow().setLayout(width, height); // לשנות את הרוחב והגובה כמו שהגדרנו

        d.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // שהתצוגה תהיה מימין לשמאל
        d.show();
    }
}
