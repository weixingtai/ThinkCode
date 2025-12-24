package com.think.code

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.think.base.BaseActivity
import com.think.base.logger.Logger
import com.think.base.logger.env.HOME_MAIN
import com.think.code.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.drawable.toDrawable

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2025/12/12 16:33
 * desc   : 应用主页
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(HOME_MAIN, "$SUB_TAG onCreate")

//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(0xffFBFCF5.toInt().toDrawable())
        val title = ImageView(this)
        title.setImageResource(R.drawable.ic_launcher_foreground)
        val actionbarLayout = LinearLayout(this)
        supportActionBar?.setCustomView(actionbarLayout,ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT))
        if (actionbarLayout.layoutParams != null) {
            val lp = actionbarLayout.layoutParams as ActionBar.LayoutParams
            lp.gravity = lp.gravity and Gravity.HORIZONTAL_GRAVITY_MASK.inv() or Gravity.CENTER_HORIZONTAL
            supportActionBar?.setCustomView(actionbarLayout,lp)
        }
        actionbarLayout.addView(title)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_follow, R.id.navigation_message, R.id.navigation_user))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null
    }

    companion object {
        private const val SUB_TAG = "MainActivity-> "
    }

}