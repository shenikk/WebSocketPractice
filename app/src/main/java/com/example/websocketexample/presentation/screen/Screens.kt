package com.example.websocketexample.presentation.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.websocketexample.R

@Composable
fun HomeScreen() {
    Text(
        text = "TEST",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun EmptyScreen(navController: NavController, context: Context) {
    Scaffold(floatingActionButton = { NoteFloatingActionButton(navController, context) } ) {

    }
//    Scaffold(floatingActionButton = { NoteFloatingActionButton(navController, context) })
//    {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth()
//        ) {
//            Text(
//                context.getString(R.string.add_first_note),
//                Modifier.padding(16.dp),
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.h5,
//            )
//        }
//    }
}

@Composable
fun NoteFloatingActionButton(navController: NavController, context: Context) {
    FloatingActionButton(onClick = { navController.navigate(Screen.Chat.route) }) {
        Icon(Icons.Filled.Add, context.getString(R.string.add_chat))
    }
}

@Composable
fun ChatScreen() {

}

@Composable
fun Navigation() {
    val nav = rememberNavController()
    val navGraph = nav.createGraph(startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
    }

//    val navGraph by remember(nav) {
//        nav.createGraph(startDestination = Screen.Home.route) {
//            composable(Screen.Home.route) { HomeScreen() }
//            composable(Screen.Chat.route) { ChatScreen() }
//        }
//    }

    NavHost(
        navController = nav,
        graph = navGraph
    )
}


/**
 * List of screens for [ChatApp]
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Chat : Screen("chat")
}
