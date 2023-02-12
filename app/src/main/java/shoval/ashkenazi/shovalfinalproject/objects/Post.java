package shoval.ashkenazi.shovalfinalproject.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post implements Parcelable {
    public String key; // auto
    public String userId; // auto
    public String date; // auto
    public String category;
    public String title;
    public String recipe;
    public String gradients;
    public String userName; // auto
    public int likes; // 0
    public String comments; // 0
    public String likesUserId; /// uid ||| uid ||| uid
    public String url; // for delete it

    public Post() {
        // for firebase Post.class!
    }

    public Post(String key, String userId, String date, String category, String title, String recipe, String gradients, String userName, int likes, String comments, String likesUserId, String url) {
        this.key = key;
        this.userId = userId;
        this.date = date;
        this.category = category;
        this.title = title;
        this.recipe = recipe;
        this.gradients = gradients;
        this.userName = userName;
        this.likes = likes;
        this.comments = comments;
        this.likesUserId = likesUserId;
        this.url = url;
    }

    protected Post(Parcel in) {
        key = in.readString();
        userId = in.readString();
        date = in.readString();
        category = in.readString();
        title = in.readString();
        recipe = in.readString();
        gradients = in.readString();
        userName = in.readString();
        likes = in.readInt();
        comments = in.readString();
        likesUserId = in.readString();
        url = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(userId);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(recipe);
        dest.writeString(gradients);
        dest.writeString(userName);
        dest.writeInt(likes);
        dest.writeString(comments);
        dest.writeString(likesUserId);
        dest.writeString(url);
    }
}
