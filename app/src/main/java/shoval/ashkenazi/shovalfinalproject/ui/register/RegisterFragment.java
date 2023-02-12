package shoval.ashkenazi.shovalfinalproject.ui.register;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.io.ByteArrayOutputStream;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FragmentRegisterBinding binding;

    private EditText etEmail, etUserName, etPassword;
    private RegisterViewModel registerViewModel;
    private ImageView ivImage;

    private final int CAMERA_REQUEST = 0;
    private final int MY_CAMERA_PERMISSION_CODE = 1;
    private final int MY_GALLERY_PERMISSION_CODE = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setUpView();
        return root;
    }


    public void setUpView() {
        Button btnRegister = binding.btnRegister, btnAddImage = binding.btnAddImage;
        btnRegister.setOnClickListener(this);
        btnAddImage.setOnClickListener(this);

        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        etUserName = binding.etUserName;
        ivImage = binding.ivImage;

        TextView tvForgetPassword = binding.tvForgetPassword;
        tvForgetPassword.setOnClickListener(this);
        TextView tvLogin = binding.tvLogin;
        tvLogin.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        registerViewModel.setContextFromActivity(requireActivity(), getContext(), v, etEmail.getText().toString(), etUserName.getText().toString(), etPassword.getText().toString());
        switch (v.getId()) {
            case R.id.btnRegister:
                registerViewModel.register();
                break;
            case R.id.tvForgetPassword:
                registerViewModel.forgetPassword();
                break;

            case R.id.tvLogin:
                Navigation.findNavController(v).navigate(R.id.action_navigation_register_to_navigation_login);
                break;

            case R.id.btnAddImage:
                addImage();
                break;
        }
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

        LinearLayout lCamera, lGallery;
        lCamera = d.findViewById(R.id.lCamera);
        lGallery = d.findViewById(R.id.lGalley);

        lGallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, MY_GALLERY_PERMISSION_CODE);
            d.dismiss();
        });

        lCamera.setOnClickListener(v -> {
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

            registerViewModel.mImageUri = data.getData();
            ivImage.setImageURI(registerViewModel.mImageUri);
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            registerViewModel.mImageUri = getImageUri(requireContext(), photo);
            ivImage.setImageURI(registerViewModel.mImageUri);
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
