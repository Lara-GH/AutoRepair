import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.autorepair.kmp.ui.theme.AutoRepairAppTheme
import org.autorepair.ui.SplashScreen

@Composable
fun App() {
    AutoRepairAppTheme {
        Navigator(SplashScreen) {
            SlideTransition(it)
        }
    }
}