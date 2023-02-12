package shoval.ashkenazi.shovalfinalproject.ui.user_profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class UserProfileFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private UserProfileFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private UserProfileFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static UserProfileFragmentArgs fromBundle(@NonNull Bundle bundle) {
    UserProfileFragmentArgs __result = new UserProfileFragmentArgs();
    bundle.setClassLoader(UserProfileFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("uid")) {
      String uid;
      uid = bundle.getString("uid");
      __result.arguments.put("uid", uid);
    } else {
      __result.arguments.put("uid", null);
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static UserProfileFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    UserProfileFragmentArgs __result = new UserProfileFragmentArgs();
    if (savedStateHandle.contains("uid")) {
      String uid;
      uid = savedStateHandle.get("uid");
      __result.arguments.put("uid", uid);
    } else {
      __result.arguments.put("uid", null);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public String getUid() {
    return (String) arguments.get("uid");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("uid")) {
      String uid = (String) arguments.get("uid");
      __result.putString("uid", uid);
    } else {
      __result.putString("uid", null);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("uid")) {
      String uid = (String) arguments.get("uid");
      __result.set("uid", uid);
    } else {
      __result.set("uid", null);
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
    UserProfileFragmentArgs that = (UserProfileFragmentArgs) object;
    if (arguments.containsKey("uid") != that.arguments.containsKey("uid")) {
      return false;
    }
    if (getUid() != null ? !getUid().equals(that.getUid()) : that.getUid() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UserProfileFragmentArgs{"
        + "uid=" + getUid()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull UserProfileFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public UserProfileFragmentArgs build() {
      UserProfileFragmentArgs result = new UserProfileFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setUid(@Nullable String uid) {
      this.arguments.put("uid", uid);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public String getUid() {
      return (String) arguments.get("uid");
    }
  }
}
