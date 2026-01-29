/*
 * Copyright 2017 The Android Open Source Project
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

package com.think.design.materialswitch;

import com.think.design.R;

import androidx.fragment.app.Fragment;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoSet;
import com.think.design.application.scope.ActivityScope;
import com.think.design.application.scope.FragmentScope;
import com.think.design.feature.Demo;
import com.think.design.feature.DemoLandingFragment;
import com.think.design.feature.FeatureDemo;

/** A fragment that displays switch demos for the Catalog app. */
public class SwitchFragment extends DemoLandingFragment {

  @Override
  public int getTitleResId() {
    return R.string.cat_switch_title;
  }

  @Override
  public int getDescriptionResId() {
    return R.string.cat_switch_description;
  }

  @Override
  public Demo getMainDemo() {
    return new Demo() {
      @Override
      public Fragment createFragment() {
        return new SwitchMainDemoFragment();
      }
    };
  }

  /** The Dagger module for {@link SwitchFragment} dependencies. */
  @dagger.Module
  public abstract static class Module {

    @FragmentScope
    @ContributesAndroidInjector
    abstract SwitchFragment contributeInjector();

    @IntoSet
    @Provides
    @ActivityScope
    static FeatureDemo provideFeatureDemo() {
      return new FeatureDemo(R.string.cat_switch_title, R.drawable.ic_switch) {
        @Override
        public Fragment createFragment() {
          return new SwitchFragment();
        }
      };
    }
  }
}
