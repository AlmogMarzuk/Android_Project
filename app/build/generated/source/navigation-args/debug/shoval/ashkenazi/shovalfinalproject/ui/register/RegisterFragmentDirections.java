package shoval.ashkenazi.shovalfinalproject.ui.register;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import shoval.ashkenazi.shovalfinalproject.R;

public class RegisterFragmentDirections {
  private RegisterFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationRegisterToNavigationLogin() {
    return new ActionOnlyNavDirections(R.id.action_navigation_register_to_navigation_login);
  }

  @NonNull
  public static NavDirections actionNavigationRegisterToNavigationDashboard() {
    return new ActionOnlyNavDirections(R.id.action_navigation_register_to_navigation_dashboard);
  }
}
