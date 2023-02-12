package shoval.ashkenazi.shovalfinalproject.adapters;

import static android.content.ContentValues.TAG;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Objects;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.objects.Post;
import shoval.ashkenazi.shovalfinalproject.ui.fid.FidFragmentDirections;
import shoval.ashkenazi.shovalfinalproject.ui.profile.ProfileFragmentDirections;

public class FidAdapter extends RecyclerView.Adapter<FidAdapter.MiniPostHolder> implements RecyclerView.OnItemTouchListener {

    public ArrayList<Post> mList;
    private final Context context;
    private final boolean canDelete;
    private final boolean canShowProfile;


    public FidAdapter(Context context, ArrayList<Post> mList, boolean canDelete, boolean canShowProfile) {
        this.context = context;
        this.mList = mList;
        this.canDelete = canDelete;
        this.canShowProfile = canShowProfile;
    }

    @NonNull
    @Override
    public MiniPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mini_post_tile, parent, false);
        return new MiniPostHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MiniPostHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(mList.get(position).url).into(holder.ivImage);
        holder.tvTitle.setText(mList.get(position).title);

        holder.tvUserName.setText(mList.get(position).userName);

        if (isUserLikedIt(position))
            holder.ibLike.setBackground(context.getDrawable(R.drawable.fill_heart));
        else
            holder.ibLike.setBackground(context.getDrawable(R.drawable.heart));

        if (canDelete) {
            holder.ibDelete.setOnClickListener(v -> deletePost(mList.get(position).key, position));
            holder.ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileFragmentDirections.ActionNavigationProfileToNavigationEditPost action =
                            ProfileFragmentDirections.actionNavigationProfileToNavigationEditPost();
                    action.setBasicPost(mList.get(position));
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }
        else {
            final String[] userNameProfile = {""};
            holder.ibDelete.setVisibility(View.GONE);
            holder.ibEdit.setVisibility(View.GONE);

            if( canShowProfile)
            holder.tvUserName.setOnClickListener(v -> {
                if(userNameProfile[0].equals(mList.get(position).userName))
                    userNameProfile[0] ="";
                else userNameProfile[0] = mList.get(position).userName; // todo its not work

                FidFragmentDirections.ActionNavigationFidToNavigationUserProfile action =
                        FidFragmentDirections.actionNavigationFidToNavigationUserProfile();
                action.setUid(""+mList.get(position).userId);
                Navigation.findNavController(v).navigate(action);
            });
        }
        holder.ibLike.setOnClickListener(v -> {
            if (isUserLikedIt(position)) {
                setPostLike(position, false);
                holder.ibLike.setBackground(context.getDrawable(R.drawable.heart));
            } else {
                setPostLike(position, true);
                holder.ibLike.setBackground(context.getDrawable(R.drawable.fill_heart));
            }
        });

        holder.FBtnShow.setOnClickListener(view -> {
            showPost(position);
        });
    }

    public void deletePost(String key, int position) {
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

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(mList.get(position).url);
        storageReference.delete().addOnSuccessListener(aVoid -> {
            // File deleted successfully
            Log.e("firebasestorage", "onSuccess: deleted file");
            Toast.makeText(context, "deleted file", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Toast.makeText(context, "connection problem - did not delete file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLongTextDialog(String text) {
        Dialog d = new Dialog(context);
        d.setContentView(R.layout.show_long_text_dialog);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tvShow = d.findViewById(R.id.tvShow);
        tvShow.setText(text);
        Button btnClose = d.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }

    @SuppressLint("SetTextI18n")
    public void showPost(int position) {
        Dialog d = new Dialog(context);
        d.setContentView(R.layout.show_post_dialog);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tvTitle = d.findViewById(R.id.tvTitle);
        TextView tvUserName = d.findViewById(R.id.tvUserName);
        TextView tvHour = d.findViewById(R.id.tvHour);
        TextView tvLikes = d.findViewById(R.id.tvLikes);
        Button btnClose = d.findViewById(R.id.btnClose);
        ImageView ivImage = d.findViewById(R.id.ivImage);

        d.findViewById(R.id.lGradients).setOnClickListener(v -> showLongTextDialog(mList.get(position).gradients));

        d.findViewById(R.id.lRecipe).setOnClickListener(v -> showLongTextDialog(mList.get(position).recipe));


        tvTitle.setText("" + mList.get(position).title);
        tvUserName.setText("" + mList.get(position).userName);
        tvHour.setText("" + mList.get(position).date);
        tvLikes.setText("" + mList.get(position).likes);

        btnClose.setOnClickListener(view12 -> d.dismiss());

        Picasso.get().load(mList.get(position).url).into(ivImage);

        d.show();
    }

    public boolean isUserLikedIt(int position) {
        if (mList.get(position).likesUserId.contains(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())))
            return true;
        else
            return false;

    }

    public void setPostLike(int position, boolean isLiked) // work great
    {
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("/Post/" + mList.get(position).key);
        if (isLiked) {
            mList.get(position).likes++;
            mList.get(position).likesUserId += " ||| " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        } else {
            mList.get(position).likes--;
            mList.get(position).likesUserId = mList.get(position).likesUserId.replace(" ||| " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), "");
        }
        postRef.setValue(mList.get(position));
//        if (isLiked)
//            Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context, "Unliked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MiniPostHolder extends RecyclerView.ViewHolder {

        FrameLayout FBtnShow;
        TextView tvTitle, tvUserName;
        ImageView ivImage;
        ImageButton ibLike, ibDelete, ibEdit;

        public MiniPostHolder(@NonNull View itemView) {
            super(itemView);
            FBtnShow = itemView.findViewById(R.id.FBtnShow);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivImage = itemView.findViewById(R.id.ivImage);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibDelete = itemView.findViewById(R.id.ibDelete);
            ibEdit = itemView.findViewById(R.id.ibEdit);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}
