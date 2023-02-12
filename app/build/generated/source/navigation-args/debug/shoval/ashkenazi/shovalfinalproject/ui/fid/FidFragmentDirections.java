package shoval.ashkenazi.shovalfinalproject.ui.fid;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import java.io.Serializable;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import shoval.ashkenazi.shovalfinalproject.R;
import shoval.ashkenazi.shovalfinalproject.objects.Post;

public class FidFragmentDirections {
  private FidFragmentDirections() {
  }

  @NonNull
  public static ActionNavigationDashboardToNavigationProfile actionNavigationDashboardToNavigationProfile(
      ) {
    return new ActionNavigationDashboardToNavigationProfile();
  }

  @NonNull
  public static ActionNavigationFidToNavigationEditPost actionNavigationFidToNavigationEditPost() {
    return new ActionNavigationFidToNavigationEditPost();
  }

  @NonNull
  public static ActionNavigationFidToNavigationUserProfile actionNavigationFidToNavigationUserProfile(
      ) {
    return new ActionNavigationFidToNavigationUserProfile();
  }

  public static class ActionNavigationDashboardToNavigationProfile implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationDashboardToNavigationProfile() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationDashboardToNavigationProfile setUid(@Nullable String uid) {
      this.arguments.put("uid", uid);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("uid")) {
        String uid = (String) arguments.get("uid");
        __result.putString("uid", uid);
      } else {
        __result.putString("uid", null);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_navigation_dashboard_to_navigation_profile;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getUid() {
      return (String) arguments.get("uid");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationDashboardToNavigationProfile that = (ActionNavigationDashboardToNavigationProfile) object;
      if (arguments.containsKey("uid") != that.arguments.containsKey("uid")) {
        return false;
      }
      if (getUid() != null ? !getUid().equals(that.getUid()) : that.getUid() != null) {
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
      result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationDashboardToNavigationProfile(actionId=" + getActionId() + "){"
          + "uid=" + getUid()
          + "}";
    }
  }

  public static class ActionNavigationFidToNavigationEditPost implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationFidToNavigationEditPost() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationFidToNavigationEditPost setBasicPost(@Nullable Post basicPost) {
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
      return R.id.action_navigation_fid_to_navigation_edit_post;
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
      ActionNavigationFidToNavigationEditPost that = (ActionNavigationFidToNavigationEditPost) object;
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
      return "ActionNavigationFidToNavigationEditPost(actionId=" + getActionId() + "){"
          + "basicPost=" + getBasicPost()
          + "}";
    }
  }

  public static class ActionNavigationFidToNavigationUserProfile implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationFidToNavigationUserProfile() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavigationFidToNavigationUserProfile setUid(@Nullable String uid) {
      this.arguments.put("uid", uid);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("uid")) {
        String uid = (String) arguments.get("uid");
        __result.putString("uid", uid);
      } else {
        __result.putString("uid", null);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_navigation_fid_to_navigation_user_profile;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getUid() {
      return (String) arguments.get("uid");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationFidToNavigationUserProfile that = (ActionNavigationFidToNavigationUserProfile) object;
      if (arguments.containsKey("uid") != that.arguments.containsKey("uid")) {
        return false;
      }
      if (getUid() != null ? !getUid().equals(that.getUid()) : that.getUid() != null) {
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
      result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationFidToNavigationUserProfile(actionId=" + getActionId() + "){"
          + "uid=" + getUid()
          + "}";
    }
  }
}
