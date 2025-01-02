// Generated by view binder compiler. Do not edit!
package com.main.mp1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.main.mp1.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class MibaRememberpwDialogBinding implements ViewBinding {
  @NonNull
  private final Gallery rootView;

  @NonNull
  public final Gallery mibaRememberpwGallery;

  private MibaRememberpwDialogBinding(@NonNull Gallery rootView,
      @NonNull Gallery mibaRememberpwGallery) {
    this.rootView = rootView;
    this.mibaRememberpwGallery = mibaRememberpwGallery;
  }

  @Override
  @NonNull
  public Gallery getRoot() {
    return rootView;
  }

  @NonNull
  public static MibaRememberpwDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MibaRememberpwDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.miba_rememberpw_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MibaRememberpwDialogBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    Gallery mibaRememberpwGallery = (Gallery) rootView;

    return new MibaRememberpwDialogBinding((Gallery) rootView, mibaRememberpwGallery);
  }
}
