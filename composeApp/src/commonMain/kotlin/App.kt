import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.autorepair.ui.theme.AutoRepairAppTheme
import org.autorepair.ui.SplashScreen

@Composable
fun App() {
    AutoRepairAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Navigator(SplashScreen) {
                SlideTransition(it)
            }
        }
    }
}