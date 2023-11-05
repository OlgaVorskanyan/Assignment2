import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.ArrowBack
import androidx.compose.material3.icons.filled.Info
import androidx.compose.material3.icons.filled.Search
import androidx.compose.material3.material3
import androidx.compose.material3.rememberAppBarConfiguration
import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.featherandroidtasks.ui.theme.FeatherAndroidTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeatherAndroidTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()

                    NavHost(
                        navController = navController,
                        startDestination = "welcome screen"
                    ) {
                        composable("welcome screen") {
                            WelcomeScreen(navController = navController)
                        }
                        composable("list screen") {
                            ListScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the City Explorer App!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("list screen") }) {
            Text(text = "Explore Cities")
        }
    }
}

@Composable
fun ListScreen(navController: NavController) {
    val cities = listOf("Yerevan", "Prague", "Barcelona")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                navController.navigateUp()
            },
            content = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(cities) { city ->
                ListItem(city = city, onClick = {
                    navController.navigate("city detail screen/$city")
                })
            }
        }
    }
}

@Composable
fun ListItem(city: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = city, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}

@Composable
fun CityDetailScreen(city: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to $city!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Description $city goes here.", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(16.dp))
        val imageUrl = "https://example.com/$city.jpg"
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}
