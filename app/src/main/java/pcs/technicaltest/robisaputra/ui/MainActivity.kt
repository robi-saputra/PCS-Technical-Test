package pcs.technicaltest.robisaputra.ui

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import pcs.technicaltest.robisaputra.R
import pcs.technicaltest.robisaputra.databinding.ActivityMainBinding
import rmtz.lib.baseapplication.BaseActivity

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {
    private lateinit var navController : NavController
    private lateinit var graph: NavGraph
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }
        navigation()
    }

    private fun navigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.listUserFragment)
        navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}