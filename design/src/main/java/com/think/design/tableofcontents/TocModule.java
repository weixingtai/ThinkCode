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

package com.think.design.tableofcontents;

import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import com.think.design.adaptive.AdaptiveFragment;
import com.think.design.application.scope.FragmentScope;
import com.think.design.bottomappbar.BottomAppBarFragment;
import com.think.design.bottomnav.BottomNavigationFragment;
import com.think.design.bottomsheet.BottomSheetFragment;
import com.think.design.button.ButtonsFragment;
import com.think.design.card.CardFragment;
import com.think.design.carousel.CarouselFragment;
import com.think.design.checkbox.CheckBoxFragment;
import com.think.design.chip.ChipFragment;
import com.think.design.color.ColorsFragment;
import com.think.design.datepicker.DatePickerDemoLandingFragment;
import com.think.design.dialog.DialogDemoLandingFragment;
import com.think.design.divider.DividerFragment;
import com.think.design.dockedtoolbar.DockedToolbarFragment;
import com.think.design.elevation.ElevationFragment;
import com.think.design.fab.FabFragment;
import com.think.design.floatingtoolbar.FloatingToolbarFragment;
import com.think.design.font.FontFragment;
import com.think.design.imageview.ShapeableImageViewFragment;
import com.think.design.listitem.ListsFragment;
import com.think.design.loadingindicator.LoadingIndicatorFragment;
import com.think.design.materialswitch.SwitchFragment;
import com.think.design.menu.MenuFragment;
import com.think.design.navigationdrawer.NavigationDrawerFragment;
import com.think.design.navigationrail.NavigationRailFragment;
import com.think.design.preferences.CatalogPreferencesDialogFragment;
import com.think.design.progressindicator.ProgressIndicatorFragment;
import com.think.design.radiobutton.RadioButtonFragment;
import com.think.design.search.SearchFragment;
import com.think.design.shapetheming.ShapeThemingFragment;
import com.think.design.sidesheet.SideSheetFragment;
import com.think.design.slider.SliderFragment;
import com.think.design.snackbar.SnackbarFragment;
import com.think.design.tabs.TabsFragment;
import com.think.design.textfield.TextFieldFragment;
import com.think.design.timepicker.TimePickerDemoLandingFragment;
import com.think.design.topappbar.TopAppBarFragment;
import com.think.design.transition.TransitionFragment;

/** The Dagger module for {@link TocFragment} dependencies. */
@dagger.Module(
    includes = {
      AdaptiveFragment.Module.class,
      BottomAppBarFragment.Module.class,
      ButtonsFragment.Module.class,
      BottomNavigationFragment.Module.class,
      BottomSheetFragment.Module.class,
      CardFragment.Module.class,
      CarouselFragment.Module.class,
      CheckBoxFragment.Module.class,
      ChipFragment.Module.class,
      ColorsFragment.Module.class,
      DatePickerDemoLandingFragment.Module.class,
      DialogDemoLandingFragment.Module.class,
      DividerFragment.Module.class,
      DockedToolbarFragment.Module.class,
      ElevationFragment.Module.class,
      FabFragment.Module.class,
      FloatingToolbarFragment.Module.class,
      FontFragment.Module.class,
      LoadingIndicatorFragment.Module.class,
      ListsFragment.Module.class,
      MenuFragment.Module.class,
      NavigationDrawerFragment.Module.class,
      NavigationRailFragment.Module.class,
      ProgressIndicatorFragment.Module.class,
      RadioButtonFragment.Module.class,
      SearchFragment.Module.class,
      ShapeableImageViewFragment.Module.class,
      ShapeThemingFragment.Module.class,
      SideSheetFragment.Module.class,
      SliderFragment.Module.class,
      SnackbarFragment.Module.class,
      SwitchFragment.Module.class,
      TabsFragment.Module.class,
      TextFieldFragment.Module.class,
      TimePickerDemoLandingFragment.Module.class,
      TopAppBarFragment.Module.class,
      TransitionFragment.Module.class
    })
public abstract class TocModule {
  @FragmentScope
  @ContributesAndroidInjector
  abstract TocFragment contributeTocFragment();

  @FragmentScope
  @ContributesAndroidInjector
  abstract CatalogPreferencesDialogFragment contributeCatalogPreferencesDialogFragment();

  @Provides
  static TocResourceProvider provideTocResourceProvider() {
    return new TocResourceProvider();
  }
}
