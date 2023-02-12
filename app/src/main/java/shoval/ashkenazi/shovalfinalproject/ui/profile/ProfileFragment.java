package shoval.ashkenazi.shovalfinalproject.ui.profile;

import static android.app.Activity.RESULT_OK;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.FirebaseUserData;
import shoval.ashkenazi.shovalfinalproject.MainActivity;
import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.adapters.FidAdapter;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentProfileBinding;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    String uid;
    EditText etUserName;
    Button btnLogOut, btnUserName;

    RecyclerView lv;
    ArrayList<Post> list;
    FidAdapter fidAdapter;

    ImageView ivImage;

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference(POST_REF);

    private FragmentProfileBinding binding;

    ProfileViewModel profileViewModel;

    private final int CAMERA_REQUEST = 0;
    private final int MY_CAMERA_PERMISSION_CODE = 1;
    private final int MY_GALLERY_PERMISSION_CODE = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        uid = ProfileFragmentArgs.fromBundle(getArguments()).getUid();
        if (uid == null)
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            MainActivity.hideBottomNav();

        profileViewModel.setContextFromActivity(getContext(), getActivity(), getView(), uid);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpView();
        showPosts();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setUpView() {
        btnLogOut = binding.btnLogOut;
        if (profileViewModel.canLogOut()) {
            btnLogOut.setVisibility(View.VISIBLE);
            btnLogOut.setOnClickListener(this);
        } else btnLogOut.setVisibility(View.GONE);

        btnUserName = binding.btnUserName;
        btnUserName.setOnClickListener(this);

        showUserName();

        ivImage = binding.ivImage;
        getUserImage();

        lv = binding.lv;
        lv.setHasFixedSize(false);
        lv.setLayoutManager(new LinearLayoutManager(getContext()));

        if(!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            etUserName.setEnabled(false);
            btnUserName.setVisibility(View.GONE);
        } else
            ivImage.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void showUserName() {
        etUserName = binding.etUserName;
        etUserName.setText("loading data...");
        if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            etUserName.setText("" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Players");
            myRef.child("" + uid).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        etUserName.setText(String.valueOf(task.getResult().getValue()));
                    }
                }
            });
        }
    }

    public void getUserImage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Players");
        myRef.child("" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getValue() != null) {
                        FirebaseUserData f = new FirebaseUserData((HashMap<String, Object>) task.getResult().getValue());
                        Picasso.get().load(f.url).into(ivImage);
                    } else {
                        Log.e("firebase - null", task.getResult().toString());
                    }
                } else {
                    Log.e("firebase - false", task.getResult().toString());
                }
            }
        });
    }

    public void showPosts() {
        list = new ArrayList<>();
        if (uid.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
            fidAdapter = new FidAdapter(getContext(), list, true,false);
        else fidAdapter = new FidAdapter(getContext(), list, false,false);
        lv.setAdapter(fidAdapter);

        setFirstViewList();
    }

    public void setFirstViewList() {
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post model = dataSnapshot.getValue(Post.class);
                    assert model != null;
                    if (model.userId.equals(uid))
                        list.add(model);
                }
                fidAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_login);
                break;
            case R.id.ivImage:
                addImage();
                break;
            case R.id.btnUserName:
                profileViewModel.changeUserName(etUserName.getText().toString().trim());
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
            profileViewModel.mImageUri = data.getData();
            ivImage.setImageURI(profileViewModel.mImageUri);
            profileViewModel.setDataFirebasePlayerAtRealTimeDataBase();
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profileViewModel.mImageUri = getImageUri(requireContext(), photo);
            ivImage.setImageURI(profileViewModel.mImageUri);
            profileViewModel.setDataFirebasePlayerAtRealTimeDataBase();
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