package shoval.ashkenazi.shovalfinalproject.ui.register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import shoval.ashkenazi.shovalfinalproject.FirebaseUserData;
import shoval.ashkenazi.shovalfinalproject.R;

public class RegisterViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Activity requireActivity;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String email;
    private String userName;
    private String password;
    @SuppressLint("StaticFieldLeak")
    private View view;
    private final FirebaseAuth myAuth;
    Uri mImageUri;

    public void setContextFromActivity(Activity requireActivity, Context context, View view, String email, String userName, String password) {
        this.requireActivity = requireActivity;
        this.context = context;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.view = view;
    }

    public RegisterViewModel() {
        myAuth = FirebaseAuth.getInstance();
    }

    public boolean isEmptyData() {
        if (email == null || password == null || userName == null) return false;
        return email.trim().length() == 0 || userName.trim().length() == 0 || mImageUri == null || password.trim().length() == 0;
    }

    // todo find user by uid
    public void register() {
        if (!isEmptyData()) {
            myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = myAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                    // todo change to update image with profileUpdates with uri
                    assert user != null;
                    user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            setDataFirebasePlayerAtRealTimeDataBase();
                            Navigation.findNavController(view).navigate(R.id.action_navigation_register_to_navigation_dashboard);
                        } else
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show();
                    });

                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show();
                }
            });
        } else
            Toast.makeText(context, "Enter all Data", Toast.LENGTH_LONG).show();
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

    public void setDataFirebasePlayerAtRealTimeDataBase() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Register ...");
        progressDialog.setCancelable(false); // אי אפשר לסגור בלחיצה מבחוץ
        progressDialog.show();

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Players");
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri, requireActivity));
        fileRef.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(url -> {
            FirebaseUserData f = new FirebaseUserData("" + FirebaseAuth.getInstance().getCurrentUser().getUid(), userName, url.toString());
            root.child(f.userId).setValue(f.toMap());
            progressDialog.dismiss();
        })).addOnProgressListener(snapshot -> {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }).addOnFailureListener(e -> {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        });
    }

    private String getFileExtension(Uri mUri, Activity requireActivity) {
        ContentResolver cr = requireActivity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}
