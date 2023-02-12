package shoval.ashkenazi.shovalfinalproject.ui.add_post;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentAddPostBinding;
import shoval.ashkenazi.shovalfinalproject.widgets.MultiSpinner;

public class AddPostFragment extends Fragment implements View.OnClickListener {

    private FragmentAddPostBinding binding;

    private MultiSpinner spCategory;

    private EditText etTitle, etRecipe, etGradients;
    private ImageView ivImage;

    private final int CAMERA_REQUEST = 0;
    private final int MY_CAMERA_PERMISSION_CODE = 1;
    private final int MY_GALLERY_PERMISSION_CODE = 2;

    AddPostViewModel addPostViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPostViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);

        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpView();

        return root;
    }

    public void setUpView() {

        LinearLayout lAddImage = binding.lAddImage;
        lAddImage.setOnClickListener(this);
        LinearLayout lUpload = binding.lUpload;
        lUpload.setOnClickListener(this);

        etTitle = binding.etTitle;
        etRecipe = binding.etRecipe;
        etGradients = binding.etGradients;

        ivImage = binding.ivImage;

        spCategory = binding.spCategory;
        spCategory.setItems(addPostViewModel.getCategoryList(), "" + MultiSpinner.NOT_CHOSE_CATEGORY, selected -> {
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lUpload:
                addPostViewModel.setContextFromActivity(getActivity(), getContext(), etTitle.getText().toString(), etRecipe.getText().toString(), etGradients.getText().toString(), spCategory.getSpinnerText());
                if (addPostViewModel.uploadPost()) {
                    cleanUpAfterUpload();
                }
                break;
            case R.id.lAddImage:
                addImage();
                break;
        }
    }

    public void cleanUpAfterUpload() {
        etTitle.setText("");
        etRecipe.setText("");
        etGradients.setText("");
        ivImage.setImageResource(R.drawable.no_picture);
        spCategory.setSelected("");
        addPostViewModel.mImageUri = null;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_GALLERY_PERMISSION_CODE && resultCode == RESULT_OK && data != null) {
            addPostViewModel.mImageUri = data.getData();
            ivImage.setImageURI(addPostViewModel.mImageUri);
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            addPostViewModel.mImageUri = getImageUri(requireContext(), photo);
            ivImage.setImageURI(addPostViewModel.mImageUri);
        }
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