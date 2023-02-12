package shoval.ashkenazi.shovalfinalproject.ui.edit_post;


import static android.content.ContentValues.TAG;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.objects.Post;
import shoval.ashkenazi.shovalfinalproject.widgets.MultiSpinner;

public class EditPostViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private Activity requireActivity;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private View view;
    private String title;
    private String recipe;
    private String gradients;
    private String category;
    Uri mImageUri;
    Post oriPost;

    public void setContextFromActivity(Activity requireActivity, Context context, View view, String title, String recipe, String gradients, String category, Post oriPost) {
        this.requireActivity = requireActivity;
        this.context = context;
        this.view = view;
        this.title = title;
        this.recipe = recipe;
        this.gradients = gradients;
        this.category = category;

        if(this.oriPost == null) this.oriPost = oriPost;
    }

    public EditPostViewModel() {
    }


    public boolean isThereTextEmptyData() {
        return title.trim().isEmpty() ||
                recipe.trim().isEmpty() ||
                gradients.trim().isEmpty() ||
                category.contains(MultiSpinner.NOT_CHOSE_CATEGORY) ||
                category.isEmpty();
    }


    private void setDataPostWithOutImage(Post post) // work great
    {
        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("/"+POST_REF +"/" + post.key); //+ b.getKey());
        bookRef.setValue(post);
        Toast.makeText(context, "The post uploaded", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(view).popBackStack();
    }

    public void editPost() {
        if (isThereTextEmptyData())
            Toast.makeText(context, "Add all data!", Toast.LENGTH_SHORT).show();
        else if(mImageUri == null) {
            oriPost.title = title;
            oriPost.recipe = recipe;
            oriPost.gradients = gradients;
            oriPost.category = category;
            oriPost.date = getDateDisplay();
            setDataPostWithOutImage(oriPost);
        }
        else
            uploadPostWithImage();
    }

    public String getDateDisplay() {
        return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()).toString();
    }

    public void deletePost(String key, boolean beforeUpload) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child(POST_REF).orderByChild("key").equalTo(key);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oriPost.url);
        storageReference.delete().addOnSuccessListener(aVoid -> {
            if(beforeUpload) {
                Toast.makeText(context, "deleted file", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context,"connection problem - did not delete file",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadPostWithImage() {
        deletePost(oriPost.key,false);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Upload your post...");
        progressDialog.setCancelable(false); // אי אפשר לסגור בלחיצה מבחוץ
        progressDialog.show();

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference("" + POST_REF);
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
        fileRef.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(url -> {
            Post p = new Post("", "" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), "" + getDateDisplay(), "" + category, "" + title, "" + recipe, "" + gradients, "" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), 0, "0", "0", url.toString());
            String modelId = root.push().getKey();
            p.key = modelId; // like set key
            assert modelId != null;
            root.child(modelId).setValue(p);
            progressDialog.cancel();

            Toast.makeText(context, "The post uploaded", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).popBackStack();
            // todo Intent intent = new Intent(this, FidActivity.class);
        })).addOnProgressListener(snapshot -> {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }).addOnFailureListener(e -> {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "The upload failed", Toast.LENGTH_SHORT).show();
        });
    }

    public ArrayList<String> getCategoryList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Cakes");
        list.add("Dairy");
        list.add("Meat");
        list.add("Seafood");
        list.add("Vegetables");
        list.add("Fruits");
        list.add("Grains");
        list.add("Baked goods");
        list.add("Sweets");
        list.add("Snacks");
        list.add("Beverages");
        return list;
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = requireActivity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}
