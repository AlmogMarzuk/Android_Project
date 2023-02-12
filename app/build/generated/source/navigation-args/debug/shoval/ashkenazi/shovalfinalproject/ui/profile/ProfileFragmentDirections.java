package shoval.ashkenazi.shovalfinalproject.ui.profile;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.io.Serializable;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class ProfileFragmentDirections {
  private ProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationProfileToNavigationLogin() {
    return new ActionOnlyNavDirections(R.id.action_navigation_profile_to_navigation_login);
  }

  @NonNull
  public static ActionNavigationProfileToNavigationEditPost actionNavigationProfileToNavigationEditPost(
      ) {
    return new ActionNavigationProfileToNavigationEditPost();
  }

  public static class ActionNavigationProfileToNavigationEditPost implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationProfileToNavigationEditPost() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationProfileToNavigationEditPost setBasicPost(@Nullable Post basicPost) {
      this.arguments.put("basicPost", basicPost);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_navigation_profile_to_navigation_edit_post;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public Post getBasicPost() {
      return (Post) arguments.get("basicPost");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationProfileToNavigationEditPost that = (ActionNavigationProfileToNavigationEditPost) object;
      if (arguments.containsKey("basicPost") != that.arguments.containsKey("basicPost")) {
        return false;
      }
      if (getBasicPost() != null ? !getBasicPost().equals(that.getBasicPost()) : that.getBasicPost() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getBasicPost() != null ? getBasicPost().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationProfileToNavigationEditPost(actionId=" + getActionId() + "){"
          + "basicPost=" + getBasicPost()
          + "}";
    }
  }
}
