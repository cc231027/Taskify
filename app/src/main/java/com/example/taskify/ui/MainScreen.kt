package com.example.taskify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskify.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentSortOption by remember { mutableStateOf(SortOption.NONE) }
    var searchQuery by remember { mutableStateOf("") }

    val appColor = Color(0xFF6650a4) // Define the app's main color

    // Modal drawer to display sort options
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Title of the drawer
                Text(
                    "Sort Tasks",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = appColor
                )
                Divider()
                // Drawer items for different sort options
                DrawerItem(
                    label = "Completed",
                    selected = currentSortOption == SortOption.COMPLETED,
                    onClick = {
                        currentSortOption = SortOption.COMPLETED
                        scope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    label = "Time Added",
                    selected = currentSortOption == SortOption.TIME_ADDED,
                    onClick = {
                        currentSortOption = SortOption.TIME_ADDED
                        scope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    label = "Date Created",
                    selected = currentSortOption == SortOption.DATE_CREATED,
                    onClick = {
                        currentSortOption = SortOption.DATE_CREATED
                        scope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    label = "Due Date",
                    selected = currentSortOption == SortOption.DUE_DATE,
                    onClick = {
                        currentSortOption = SortOption.DUE_DATE
                        scope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    label = "Alphabetical",
                    selected = currentSortOption == SortOption.ALPHABETICAL,
                    onClick = {
                        currentSortOption = SortOption.ALPHABETICAL
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = appColor) // Header color
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TaskifyApp",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                        // Open the drawer when the menu icon is clicked
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, "Menu", tint = Color.White)
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("taskForm") },
                    containerColor = appColor // FAB color
                ) {
                    Text(
                        text = "+",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                // Search bar with icon
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search Tasks") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                val tasks by viewModel.tasks.collectAsState()
                val filteredTasks = tasks.filter { task ->
                    task.title.contains(searchQuery, ignoreCase = true) ||
                            task.description.contains(searchQuery, ignoreCase = true)
                }
                val sortedTasks = when (currentSortOption) {
                    SortOption.COMPLETED -> filteredTasks.sortedBy { it.isCompleted }
                    SortOption.TIME_ADDED -> filteredTasks.sortedBy { it.createdDate }
                    SortOption.DATE_CREATED -> filteredTasks.sortedBy { it.createdDate }
                    SortOption.DUE_DATE -> filteredTasks.sortedBy { it.dueDate }
                    SortOption.ALPHABETICAL -> filteredTasks.sortedBy { it.title }
                    else -> filteredTasks
                }

                // If no tasks are found
                if (sortedTasks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No tasks yet. Add one!")
                    }
                } else {
                    // Display tasks in a list
                    LazyColumn {
                        items(sortedTasks.size) { index ->
                            TaskItem(
                                task = sortedTasks[index],
                                onDelete = { viewModel.deleteTask(it) },
                                navController = navController,
                                onTaskCompletedChange = { viewModel.updateTaskCompleted(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(label: String, selected: Boolean, onClick: () -> Unit) {
    // A single item inside the navigation drawer
    NavigationDrawerItem(
        label = { Text(label) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
