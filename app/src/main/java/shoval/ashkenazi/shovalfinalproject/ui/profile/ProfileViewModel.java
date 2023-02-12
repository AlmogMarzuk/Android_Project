package shoval.ashkenazi.shovalfinalproject.ui.profile;


import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.FirebaseUserData;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class ProfileViewModel extends ViewModel {

    Context context;
    View view;
    Uri mImageUri;
    Activity requireActivity;
    String uid;

    public ProfileViewModel() {
    }

    public void setContextFromActivity(Context context, Activity requireActivity, View view, String uid) {
        this.context = context;
        this.view = view;
        this.requireActivity = requireActivity;
        this.uid = uid;
    }

    public boolean canLogOut() {
        return uid.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    public void changeUserName(String userName) {
        if(!userName.isEmpty()){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
            assert user != null;
            user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Players");
                    root.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").setValue(""+userName);
                    setList(userName);
                    Toast.makeText(context, "updated good", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(context, "updated failed", Toast.LENGTH_LONG).show();
            });
        } else Toast.makeText(context, "Enter name!", Toast.LENGTH_SHORT).show();
    }

    public void setList(String userName) {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference(POST_REF);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post model = dataSnapshot.getValue(Post.class);
                    assert model != null;
                    if(model.userId.equals(uid)){
                        final DatabaseReference tempRoot = FirebaseDatabase.getInstance().getReference(POST_REF);
                        tempRoot.child(model.key).child("userName").setValue("" + userName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void setDataFirebasePlayerAtRealTimeDataBase() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Register ...");
        progressDialog.setCancelable(false); // אי אפשר לסגור בלחיצה מבחוץ
        progressDialog.show();

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Players");
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri,requireActivity));
        fileRef.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(url -> {
            FirebaseUserData f = new FirebaseUserData(""+FirebaseAuth.getInstance().getCurrentUser().getUid(),FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),url.toString());
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