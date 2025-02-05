/*package com.example.taskify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskify.viewmodel.TaskViewModel

@Composable
fun Navigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "intro") {
        composable("intro") {
            IntroScreen(navController = navController)
        }
        composable("main") {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "taskForm/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskFormScreen(navController = navController, viewModel = viewModel, taskId = taskId)
        }
        composable("taskForm") {
            TaskFormScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "taskDetail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailScreen(navController = navController, viewModel = viewModel, taskId = taskId)
        }
    }
}

 */
package com.example.taskify.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskify.viewmodel.TaskViewModel

@Composable
fun Navigation(viewModel: TaskViewModel) {
    val navController = rememberNavController() // Initialize the navigation controller

    // Define the NavHost with start destination and navigation graph
    NavHost(navController = navController, startDestination = "intro") {
        // Intro screen navigation
        composable("intro") {
            IntroScreen(navController = navController)
        }

        // Main screen navigation
        composable("main") {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        // Task form screen for creating or editing a task, with taskId as argument
        composable(
            "taskForm/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType }) // Task ID argument
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskFormScreen(navController = navController, viewModel = viewModel, taskId = taskId)
        }

        // Task form screen navigation without taskId (for creating new task)
        composable("taskForm") {
            TaskFormScreen(navController = navController, viewModel = viewModel)
        }

        // Task detail screen navigation with taskId as argument
        composable(
            "taskDetail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType }) // Task ID argument
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailScreen(navController = navController, viewModel = viewModel, taskId = taskId)
        }
    }
}
