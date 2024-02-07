import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.autorepair.ui.LoginScreen
import org.autorepair.ui.SplashScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(LoginScreen) {
            SlideTransition(it)
        }
    }
}