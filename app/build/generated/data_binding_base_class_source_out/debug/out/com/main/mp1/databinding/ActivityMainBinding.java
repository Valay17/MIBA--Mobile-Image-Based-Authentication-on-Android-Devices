// Generated by view binder compiler. Do not edit!
package com.main.mp1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.main.mp1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout LinearLayout1;

  @NonNull
  public final TextView footer;

  @NonNull
  public final Button miba;

  @NonNull
  public final TextView paper;

  @NonNull
  public final Button passgo;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView textView2;

  private ActivityMainBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout LinearLayout1, @NonNull TextView footer, @NonNull Button miba,
      @NonNull TextView paper, @NonNull Button passgo, @NonNull TextView textView1,
      @NonNull TextView textView2) {
    this.rootView = rootView;
    this.LinearLayout1 = LinearLayout1;
    this.footer = footer;
    this.miba = miba;
    this.paper = paper;
    this.passgo = passgo;
    this.textView1 = textView1;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout LinearLayout1 = (RelativeLayout) rootView;

      id = R.id.footer;
      TextView footer = ViewBindings.findChildViewById(rootView, id);
      if (footer == null) {
        break missingId;
      }

      id = R.id.miba;
      Button miba = ViewBindings.findChildViewById(rootView, id);
      if (miba == null) {
        break missingId;
      }

      id = R.id.paper;
      TextView paper = ViewBindings.findChildViewById(rootView, id);
      if (paper == null) {
        break missingId;
      }

      id = R.id.passgo;
      Button passgo = ViewBindings.findChildViewById(rootView, id);
      if (passgo == null) {
        break missingId;
      }

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityMainBinding((RelativeLayout) rootView, LinearLayout1, footer, miba, paper,
          passgo, textView1, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
