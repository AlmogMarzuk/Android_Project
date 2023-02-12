package shoval.ashkenazi.shovalfinalproject.ui.fid;

import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.TITLE_SEARCH;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.USER_NAME_SEARCH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class FidViewModel extends ViewModel {

    DatabaseReference root;
    Context context;

    public void setContextFromActivity(Context context) {
        this.context = context;
        root = FirebaseDatabase.getInstance().getReference(POST_REF);
    }

    public ArrayList<Post> setList(String wordSearch, String modeOfSearch) {
        ArrayList<Post> list = new ArrayList<>();
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post model = dataSnapshot.getValue(Post.class);
                    boolean create = true;
                    assert model != null;

                    if (modeOfSearch.equals(TITLE_SEARCH)) {
                        if (!model.title.contains(wordSearch.trim())) create = false;
                    } else if (modeOfSearch.equals(USER_NAME_SEARCH)) {
                        if (!model.userName.contains(wordSearch.trim())) create = false;
                    }
                    if (create) {
                        Log.e("Add", model.title);
                        list.add(model);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.e("Start Add", "");
        for (int i = 0; i < list.size(); i++)
            Log.e("Add", list.get(i).title);

        return list;
    }
}