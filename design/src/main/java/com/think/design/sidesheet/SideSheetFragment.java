/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.think.design.sidesheet;

import com.think.design.R;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoSet;
import com.think.design.application.scope.ActivityScope;
import com.think.design.application.scope.FragmentScope;
import com.think.design.feature.Demo;
import com.think.design.feature.DemoLandingFragment;
import com.think.design.feature.FeatureDemo;

/** A landing fragment that links to Side Sheet demos for the Catalog app. */
public class SideSheetFragment extends DemoLandingFragment {

  @Override
  public int getTitleResId() {
    return R.string.cat_sidesheet_title;
  }

  @Override
  public int getDescriptionResId() {
    return R.string.cat_sidesheet_description;
  }

  @NonNull
  @Override
  public Demo getMainDemo() {
    return new Demo() {
      @NonNull
      @Override
      public Fragment createFragment() {
        return new SideSheetMainDemoFragment();
      }
    };
  }

  /** The Dagger module for {@link SideSheetMainDemoFragment} dependencies. */
  @dagger.Module
  public abstract static class Module {

    @FragmentScope
    @ContributesAndroidInjector
    abstract SideSheetFragment contributeInjector();

    @IntoSet
    @Provides
    @ActivityScope
    static FeatureDemo provideFeatureDemo() {
      return new FeatureDemo(R.string.cat_sidesheet_title, R.drawable.ic_side_navigation_24px) {
        @Override
        public Fragment createFragment() {
          return new SideSheetFragment();
        }
      };
    }
  }
}
