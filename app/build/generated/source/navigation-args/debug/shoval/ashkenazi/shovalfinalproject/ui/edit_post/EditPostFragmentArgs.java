package shoval.ashkenazi.shovalfinalproject.ui.edit_post;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.io.Serializable;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class EditPostFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EditPostFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private EditPostFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static EditPostFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EditPostFragmentArgs __result = new EditPostFragmentArgs();
    bundle.setClassLoader(EditPostFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("basicPost")) {
      Post basicPost;
      if (Parcelable.class.isAssignableFrom(Post.class) || Serializable.class.isAssignableFrom(Post.class)) {
        basicPost = (Post) bundle.get("basicPost");
      } else {
        throw new UnsupportedOperationException(Post.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      __result.arguments.put("basicPost", basicPost);
    } else {
      __result.arguments.put("basicPost", null);
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EditPostFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    EditPostFragmentArgs __result = new EditPostFragmentArgs();
    if (savedStateHandle.contains("basicPost")) {
      Post basicPost;
      basicPost = savedStateHandle.get("basicPost");
      __result.arguments.put("basicPost", basicPost);
    } else {
      __result.arguments.put("basicPost", null);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public Post getBasicPost() {
    return (Post) arguments.get("basicPost");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("basicPost")) {
      Post basicPost = (Post) arguments.get("basicPost");
      if (Parcelable.class.isAssignableFrom(Post.class) || basicPost == null) {
        __result.putParcelable("basicPost", Parcelable.class.cast(basicPost));
      } else if (Serializable.class.isAssignableFrom(Post.class)) {
        __result.putSerializable("basicPost", Serializable.class.cast(basicPost));
      } else {
        throw new UnsupportedOperationException(Post.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    } else {
      __result.putSerializable("basicPost", null);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("basicPost")) {
      Post basicPost = (Post) arguments.get("basicPost");
      if (Parcelable.class.isAssignableFrom(Post.class) || basicPost == null) {
        __result.set("basicPost", Parcelable.class.cast(basicPost));
      } else if (Serializable.class.isAssignableFrom(Post.class)) {
        __result.set("basicPost", Serializable.class.cast(basicPost));
      } else {
        throw new UnsupportedOperationException(Post.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    } else {
      __result.set("basicPost", null);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EditPostFragmentArgs that = (EditPostFragmentArgs) object;
    if (arguments.containsKey("basicPost") != that.arguments.containsKey("basicPost")) {
      return false;
    }
    if (getBasicPost() != null ? !getBasicPost().equals(that.getBasicPost()) : that.getBasicPost() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getBasicPost() != null ? getBasicPost().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EditPostFragmentArgs{"
        + "basicPost=" + getBasicPost()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull EditPostFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public EditPostFragmentArgs build() {
      EditPostFragmentArgs result = new EditPostFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setBasicPost(@Nullable Post basicPost) {
      this.arguments.put("basicPost", basicPost);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public Post getBasicPost() {
      return (Post) arguments.get("basicPost");
    }
  }
}
