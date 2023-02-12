package shoval.ashkenazi.shovalfinalproject.ui.login;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import shoval.ashkenazi.shovalfinalproject.R;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationLoginToNavigationDashboard() {
    return new ActionOnlyNavDirections(R.id.action_navigation_login_to_navigation_dashboard);
  }

  @NonNull
  public static NavDirections actionNavigationLoginToNavigationRegister() {
    return new ActionOnlyNavDirections(R.id.action_navigation_login_to_navigation_register);
  }
}
