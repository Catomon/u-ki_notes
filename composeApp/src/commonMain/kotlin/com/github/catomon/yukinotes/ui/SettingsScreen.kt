package com.github.catomon.yukinotes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.catomon.yukinotes.UserSettings
import org.jetbrains.compose.resources.painterResource
import yukinotes.composeapp.generated.resources.Res
import yukinotes.composeapp.generated.resources.exit
import kotlin.system.exitProcess


@Composable
fun SettingsScreen(
    yukiViewModel: YukiViewModel,
    navBack: () -> Unit
) {
    val state by yukiViewModel.notesScreenState.collectAsState()
    val settings by yukiViewModel.userSettings
    val currentThemeName = settings.theme

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center).fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Theme", color = Color.White)
                Themes.list.forEachIndexed { i, theme ->
                    RadioButton(
                        currentThemeName == theme.name,
                        colors = RadioButtonDefaults.colors(theme.surface, theme.surfaceSecondary),
                        onClick = {
                            yukiViewModel.updateUserSettings(settings.copy(theme = theme.name))
                        })
                }
            }

            SwitchSetting("Always show details", state.alwaysShowDetails, onCheckedChange = {
                yukiViewModel.alwaysShowDetails(it)
            })

            StoreAsTextCheckbox(settings, yukiViewModel)
        }

        OpenSourcesText(Modifier.padding(start = 8.dp).align(Alignment.BottomStart))

        Image(
            painterResource(Res.drawable.exit),
            "Exit App",
            Modifier.padding(8.dp).size(32.dp).clickable(onClick = { exitProcess(0) })
                .align(Alignment.BottomEnd)

        )
    }
}

@Composable
expect fun OpenSourcesText(modifier: Modifier = Modifier)

@Composable
expect fun StoreAsTextCheckbox(
    settings: UserSettings,
    yukiViewModel: YukiViewModel
)

@Composable
fun SwitchSetting(text: String, switchState: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text, color = Color.White)
        Switch(switchState, onCheckedChange = onCheckedChange, modifier = Modifier.scale(0.75f))
    }
}