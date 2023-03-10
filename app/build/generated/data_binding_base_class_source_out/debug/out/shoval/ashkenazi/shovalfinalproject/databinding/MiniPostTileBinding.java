// Generated by view binder compiler. Do not edit!
package shoval.ashkenazi.shovalfinalproject.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import shoval.ashkenazi.shovalfinalproject.R;

public final class MiniPostTileBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final FrameLayout FBtnShow;

  @NonNull
  public final LinearLayout Name;

  @NonNull
  public final ImageButton ibDelete;

  @NonNull
  public final ImageButton ibEdit;

  @NonNull
  public final ImageButton ibLike;

  @NonNull
  public final ImageView ivImage;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final TextView tvTitle;

  @NonNull
  public final TextView tvUserName;

  private MiniPostTileBinding(@NonNull FrameLayout rootView, @NonNull FrameLayout FBtnShow,
      @NonNull LinearLayout Name, @NonNull ImageButton ibDelete, @NonNull ImageButton ibEdit,
      @NonNull ImageButton ibLike, @NonNull ImageView ivImage, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout linearLayout2, @NonNull TextView tvTitle,
      @NonNull TextView tvUserName) {
    this.rootView = rootView;
    this.FBtnShow = FBtnShow;
    this.Name = Name;
    this.ibDelete = ibDelete;
    this.ibEdit = ibEdit;
    this.ibLike = ibLike;
    this.ivImage = ivImage;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.tvTitle = tvTitle;
    this.tvUserName = tvUserName;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MiniPostTileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MiniPostTileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.mini_post_tile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MiniPostTileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      FrameLayout FBtnShow = (FrameLayout) rootView;

      id = R.id.Name;
      LinearLayout Name = ViewBindings.findChildViewById(rootView, id);
      if (Name == null) {
        break missingId;
      }

      id = R.id.ibDelete;
      ImageButton ibDelete = ViewBindings.findChildViewById(rootView, id);
      if (ibDelete == null) {
        break missingId;
      }

      id = R.id.ibEdit;
      ImageButton ibEdit = ViewBindings.findChildViewById(rootView, id);
      if (ibEdit == null) {
        break missingId;
      }

      id = R.id.ibLike;
      ImageButton ibLike = ViewBindings.findChildViewById(rootView, id);
      if (ibLike == null) {
        break missingId;
      }

      id = R.id.ivImage;
      ImageView ivImage = ViewBindings.findChildViewById(rootView, id);
      if (ivImage == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.tvTitle;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      id = R.id.tvUserName;
      TextView tvUserName = ViewBindings.findChildViewById(rootView, id);
      if (tvUserName == null) {
        break missingId;
      }

      return new MiniPostTileBinding((FrameLayout) rootView, FBtnShow, Name, ibDelete, ibEdit,
          ibLike, ivImage, linearLayout, linearLayout2, tvTitle, tvUserName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
