// Generated by view binder compiler. Do not edit!
package shoval.ashkenazi.shovalfinalproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import shoval.ashkenazi.shovalfinalproject.R;

public final class ActivityWeatherBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final EditText YourCity;

  @NonNull
  public final TextView city;

  @NonNull
  public final TextView country;

  @NonNull
  public final RelativeLayout first;

  @NonNull
  public final TextView forecast;

  @NonNull
  public final TextView humidity;

  @NonNull
  public final ImageButton ibSearch;

  @NonNull
  public final TextView maxTemp;

  @NonNull
  public final TextView minTemp;

  @NonNull
  public final TextView sunrises;

  @NonNull
  public final TextView sunsets;

  @NonNull
  public final TextView temp;

  @NonNull
  public final TextView time;

  private ActivityWeatherBinding(@NonNull NestedScrollView rootView, @NonNull EditText YourCity,
      @NonNull TextView city, @NonNull TextView country, @NonNull RelativeLayout first,
      @NonNull TextView forecast, @NonNull TextView humidity, @NonNull ImageButton ibSearch,
      @NonNull TextView maxTemp, @NonNull TextView minTemp, @NonNull TextView sunrises,
      @NonNull TextView sunsets, @NonNull TextView temp, @NonNull TextView time) {
    this.rootView = rootView;
    this.YourCity = YourCity;
    this.city = city;
    this.country = country;
    this.first = first;
    this.forecast = forecast;
    this.humidity = humidity;
    this.ibSearch = ibSearch;
    this.maxTemp = maxTemp;
    this.minTemp = minTemp;
    this.sunrises = sunrises;
    this.sunsets = sunsets;
    this.temp = temp;
    this.time = time;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWeatherBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWeatherBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_weather, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWeatherBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Your_city;
      EditText YourCity = ViewBindings.findChildViewById(rootView, id);
      if (YourCity == null) {
        break missingId;
      }

      id = R.id.city;
      TextView city = ViewBindings.findChildViewById(rootView, id);
      if (city == null) {
        break missingId;
      }

      id = R.id.country;
      TextView country = ViewBindings.findChildViewById(rootView, id);
      if (country == null) {
        break missingId;
      }

      id = R.id.first;
      RelativeLayout first = ViewBindings.findChildViewById(rootView, id);
      if (first == null) {
        break missingId;
      }

      id = R.id.forecast;
      TextView forecast = ViewBindings.findChildViewById(rootView, id);
      if (forecast == null) {
        break missingId;
      }

      id = R.id.humidity;
      TextView humidity = ViewBindings.findChildViewById(rootView, id);
      if (humidity == null) {
        break missingId;
      }

      id = R.id.ibSearch;
      ImageButton ibSearch = ViewBindings.findChildViewById(rootView, id);
      if (ibSearch == null) {
        break missingId;
      }

      id = R.id.max_temp;
      TextView maxTemp = ViewBindings.findChildViewById(rootView, id);
      if (maxTemp == null) {
        break missingId;
      }

      id = R.id.min_temp;
      TextView minTemp = ViewBindings.findChildViewById(rootView, id);
      if (minTemp == null) {
        break missingId;
      }

      id = R.id.sunrises;
      TextView sunrises = ViewBindings.findChildViewById(rootView, id);
      if (sunrises == null) {
        break missingId;
      }

      id = R.id.sunsets;
      TextView sunsets = ViewBindings.findChildViewById(rootView, id);
      if (sunsets == null) {
        break missingId;
      }

      id = R.id.temp;
      TextView temp = ViewBindings.findChildViewById(rootView, id);
      if (temp == null) {
        break missingId;
      }

      id = R.id.time;
      TextView time = ViewBindings.findChildViewById(rootView, id);
      if (time == null) {
        break missingId;
      }

      return new ActivityWeatherBinding((NestedScrollView) rootView, YourCity, city, country, first,
          forecast, humidity, ibSearch, maxTemp, minTemp, sunrises, sunsets, temp, time);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
