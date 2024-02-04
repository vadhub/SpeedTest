
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.abg.speedtest.ui.theme.DarkColor2
import com.abg.speedtest.ui.theme.LightColor2
import com.abg.speedtest.ui.theme.Shapes
import com.abg.speedtest.ui.theme.Teal200

private val ColorPalette = darkColorScheme(
    primary = Color.White,
    secondary = Teal200,
    background = DarkColor2,
    surface = DarkColor2,
    onSurface = LightColor2,
    onBackground = LightColor2
)

@Composable
fun SpeedTestTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = ColorPalette,
        typography = Typography(),
        shapes = Shapes,
        content = content
    )
}