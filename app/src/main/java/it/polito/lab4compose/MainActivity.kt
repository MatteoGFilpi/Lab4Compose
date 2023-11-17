package it.polito.lab4compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.polito.lab4compose.ui.theme.Lab4ComposeTheme

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        //val currentUser = auth.currentUser

        /* updateUI(currentUser)
        auth.signInAnonymously()
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInAnonymously:success")
                val user = auth.currentUser
               // updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInAnonymously:failure", task.exception)
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
               // updateUI(null)
            }
        }*/

        setContent {
            Lab4ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticationScreen(auth)
                    //Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@Composable
fun AuthenticationScreen(auth: FirebaseAuth) {
    var isSignedIn by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        // Attempt anonymous sign-in when the screen is launched
        isSignedIn = signInAnonymously(auth)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSignedIn) {
            // User is signed in, show authenticated content
            Text("User is signed in anonymously!")
        } else {
            // User is not signed in, you can show a loading indicator or a login button
            Text("Signing in...")
        }
    }
}

// Function to perform anonymous sign-in
private suspend fun signInAnonymously(auth: FirebaseAuth): Boolean {
    return try {
        auth.signInAnonymously().await()
        true
    } catch (e: Exception) {
        // Handle sign-in failure
        false
    }
}
