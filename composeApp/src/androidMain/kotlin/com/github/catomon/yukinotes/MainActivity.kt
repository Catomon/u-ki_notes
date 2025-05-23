package com.github.catomon.yukinotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.github.catomon.yukinotes.di.appModule
import com.github.catomon.yukinotes.ui.Colors
import com.github.catomon.yukinotes.ui.YukiApp
import com.github.catomon.yukinotes.ui.YukiTheme
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityContext = this

        startKoin {
            modules(appModule)
        }

        setContent {
            window.statusBarColor = Colors.currentYukiTheme.bars.toArgb()

            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

            YukiTheme {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBar(
                            title = { Text("U-ki Notes") },
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor =  Colors.bars, scrolledContainerColor = Colors.bars)
                        )
                    },
                ) {
                    YukiApp(modifier = Modifier.padding(it))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mainActivityContext = null
    }
}
