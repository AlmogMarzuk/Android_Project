package shoval.ashkenazi.shovalfinalproject.ui.add_post;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import shoval.ashkenazi.shovalfinalproject.objects.Post;
import shoval.ashkenazi.shovalfinalproject.widgets.MultiSpinner;

public class AddPostViewModel extends ViewModel {

    private static final String POST_HEAD_PATH = "Post";
    @SuppressLint("StaticFieldLeak")
    private Activity requireActivity;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String title;
    private String recipe;
    private String gradients;
    private String category;
    Uri mImageUri;

    public void setContextFromActivity(Activity requireActivity,Context context, String title, String recipe, String gradients, String category) {
        this.requireActivity = requireActivity;
        this.context = context;
        this.title = title;
        this.recipe = recipe;
        this.gradients = gradients;
        this.category = category;
    }

    public AddPostViewModel() {}

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

    public boolean isThereTextEmptyData() {
        return title.trim().isEmpty() ||
                recipe.trim().isEmpty() ||
                gradients.trim().isEmpty() ||
                category.contains(MultiSpinner.NOT_CHOSE_CATEGORY) ||
                category.isEmpty() ||
                mImageUri == null;
    }

    public boolean uploadPost() {
        if (mImageUri == null) {
            Toast.makeText(context, "Add Image Post!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (isThereTextEmptyData()) {
            Toast.makeText(context, "Add all data!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return uploadPostToFirebase();
    }

    public String getDateDisplay() {
        return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()).toString();
    }

    public boolean uploadPostToFirebase() {
        AtomicBoolean create = new AtomicBoolean(true);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Upload your post...");
        progressDialog.setCancelable(false); // אי אפשר לסגור בלחיצה מבחוץ
        progressDialog.show();

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference("" + POST_HEAD_PATH);
        final StorageReference reference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri,requireActivity));
        fileRef.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(url -> {
            Post p = new Post("", "" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), "" + getDateDisplay(), "" + category, "" + title, "" + recipe, "" + gradients, "" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), 0, "0", "0", url.toString());
            String modelId = root.push().getKey();
            p.key = modelId; // like set key
            assert modelId != null;
            root.child(modelId).setValue(p);
            progressDialog.dismiss();
            create.set(true);
            Toast.makeText(context, "The post uploaded", Toast.LENGTH_SHORT).show();
        })).addOnProgressListener(snapshot -> {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }).addOnFailureListener(e -> {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "The upload failed", Toast.LENGTH_SHORT).show();
            create.set(false);
        });
        return create.get();
    }

    private String getFileExtension(Uri mUri, Activity requireActivity) {
        ContentResolver cr = requireActivity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}