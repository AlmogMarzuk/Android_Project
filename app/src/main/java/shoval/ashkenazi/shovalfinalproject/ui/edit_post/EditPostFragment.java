package shoval.ashkenazi.shovalfinalproject.ui.edit_post;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;
import static shoval.ashkenazi.shovalfinalproject.widgets.MultiSpinner.NOT_CHOSE_CATEGORY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentEditPostBinding;
import shoval.ashkenazi.shovalfinalproject.objects.Post;
import shoval.ashkenazi.shovalfinalproject.widgets.MultiSpinner;

public class EditPostFragment extends Fragment implements View.OnClickListener{
    private FragmentEditPostBinding binding;
    EditPostViewModel editPostViewModel;

    private MultiSpinner spCategory;

    private EditText etTitle, etRecipe, etGradients;
    private ImageView ivImage;

    private final int CAMERA_REQUEST = 0;
    private final int MY_CAMERA_PERMISSION_CODE = 1;
    private final int MY_GALLERY_PERMISSION_CODE = 2;

    private Uri mImageUri;
    Post oriPost;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        editPostViewModel = new ViewModelProvider(this).get(EditPostViewModel.class);

        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        oriPost = EditPostFragmentArgs.fromBundle(getArguments()).getBasicPost();

        setUpView();

        return root;
    }

    @SuppressLint("SetTextI18n")
    public void setUpView() {
        LinearLayout lAddImage = binding.lAddImage;
        lAddImage.setOnClickListener(this);
        LinearLayout lUpload = binding.lUpload;
        lUpload.setOnClickListener(this);

        etTitle = binding.etTitle;
        etTitle.setText(""+oriPost.title);
        etRecipe = binding.etRecipe;
        etRecipe.setText(""+oriPost.recipe);
        etGradients = binding.etGradients;
        etGradients.setText(""+oriPost.gradients);

        ivImage = binding.ivImage;
        Picasso.get().load(oriPost.url).into(ivImage);

        spCategory = binding.spCategory;
        spCategory.setItems(editPostViewModel.getCategoryList(), "" + NOT_CHOSE_CATEGORY, selected -> {});
        spCategory.setSelected(oriPost.category);
    }

    public void addImage() {
        Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.add_image_dialog);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.setCancelable(true);

        d.findViewById(R.id.lGalley).setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, MY_GALLERY_PERMISSION_CODE);
            d.dismiss();
        });

        d.findViewById(R.id.lCamera).setOnClickListener(v -> {
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                d.dismiss();
            }
        });

        d.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        editPostViewModel.setContextFromActivity(requireActivity(),getContext(),requireView(),etTitle.getText().toString().trim(),etRecipe.getText().toString().trim(),etGradients.getText().toString().trim(),spCategory.getSpinnerText(),oriPost);
        switch (v.getId()) {
            case R.id.lUpload:
                editPostViewModel.editPost();
                break;
            case R.id.lAddImage:
                addImage();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_GALLERY_PERMISSION_CODE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            ivImage.setImageURI(mImageUri);
            editPostViewModel.mImageUri = mImageUri;
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageUri = getImageUri(requireContext(), photo);
            ivImage.setImageURI(mImageUri);
            editPostViewModel.mImageUri = mImageUri;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
