package shoval.ashkenazi.shovalfinalproject.ui.fid;

import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.POST_REF;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.TITLE_SEARCH;
import static shoval.ashkenazi.shovalfinalproject.datas.ShowPostControl.USER_NAME_SEARCH;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.WeatherActivity;
import shoval.ashkenazi.shovalfinalproject.adapters.FidAdapter;
import shoval.ashkenazi.shovalfinalproject.databinding.FragmentFidBinding;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class FidFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FragmentFidBinding binding;
    private Button btnShowWeather;

    public String modeOfSearch = "";
    public String wordSearch = "";
    private RecyclerView recyclerView;
    private FidAdapter fidAdapter;

    ArrayList<Post> list;
    public EditText etSearch;
    FidViewModel fidViewModel;

    Spinner spTopic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fidViewModel = new ViewModelProvider(this).get(FidViewModel.class);

        binding = FragmentFidBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fidViewModel.setContextFromActivity(getContext());
        setUpView();
        showViewPosts();
        return root;
    }

    public void showViewPosts() {
        list = setFirstViewList();
        fidAdapter = new FidAdapter(getContext(), list, false,true);
        recyclerView.setAdapter(fidAdapter);
    }

    public ArrayList<Post> setFirstViewList() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference(POST_REF);
        ArrayList<Post> newList = new ArrayList<>();
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newList.clear();
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
                        newList.add(model);
                    }
                }
                fidAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return newList;
    }

    public void setUpView() {
        recyclerView = binding.lv;
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(), R.array.POST_SEARCH_TOPIC, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTopic = binding.spTopic;
        spTopic.setAdapter(adapterSpinner);
        spTopic.setOnItemSelectedListener(this);

        etSearch = binding.etSearch;
        etSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.e("TAG Changed to: ", s.toString());
                wordSearch = s.toString();
                updateListView();
            }
        });

        btnShowWeather = binding.btnShowWeather;
        btnShowWeather.setOnClickListener(this);
    }

    public void updateListView() {
        ArrayList<Post> list = fidViewModel.setList(wordSearch, modeOfSearch);
        wordSearch = "";
        fidAdapter = new FidAdapter(getContext(), list, false,true);
        recyclerView.setAdapter(fidAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        modeOfSearch = parent.getItemAtPosition(position).toString();
        updateListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnShowWeather) {
            startActivity(new Intent(getContext(), WeatherActivity.class));
        }
    }
}