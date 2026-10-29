package com.unibuzz.campusapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ConfirmationNumber
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.LocalOffer
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NewReleases
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.unibuzz.campusapp.R
import com.unibuzz.campusapp.data.local.AppDatabase
import com.unibuzz.campusapp.data.local.AuthManager
import com.unibuzz.campusapp.data.local.ThemeManager
import com.unibuzz.campusapp.data.local.ThemeMode
import com.unibuzz.campusapp.ui.components.bounceOnClick
import com.unibuzz.campusapp.ui.screens.*
import com.unibuzz.campusapp.ui.theme.ElectricViolet
import com.unibuzz.campusapp.ui.theme.GlassBorderGradient
import com.unibuzz.campusapp.ui.theme.NetflixRed
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String) {
    data object Items : Screen("items", "Items")
    data object PostItem : Screen("post_item", "Post Item")
    data object ItemDetail : Screen("item/{itemId}", "Item") {
        fun createRoute(itemId: Long) = "item/$itemId"
    }
    data object Events : Screen("events", "Events")
    data object PostEvent : Screen("post_event", "Post Event")
    data object EventDetail : Screen("event/{eventId}", "Event") {
        fun createRoute(eventId: Long) = "event/$eventId"
    }
    data object Categories : Screen("categories", "Categories")
    data object Type : Screen("type", "Types")
    data object Savings : Screen("savings", "Savings")
    data object Account : Screen("account", "Account")
    data object Login : Screen("login", "Sign In")
    data object Register : Screen("register", "Register")
    data object About : Screen("about", "About & Developer")
    data object DevReleases : Screen("dev_releases", "Dev Releases")
    data object Leaderboard : Screen("leaderboard", "Leaderboard")
    data object Faqs : Screen("faqs", "FAQs")
    data object Settings : Screen("settings", "Settings")
    data object Wishlist : Screen("wishlist", "My Wishlist")
}

@Composable
private fun NavTabIcon(
    selected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "navTabScale",
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.scale(scale),
    ) {
        Icon(
            imageVector = if (selected) selectedIcon else unselectedIcon,
            contentDescription = null,
        )
    }
}

@Composable
fun App(database: AppDatabase) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authManager = remember(context) { AuthManager.getInstance(context) }
    val themeManager = remember(context) { ThemeManager.getInstance(context) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        authManager.initialize(database)
    }

    val currentUser by authManager.currentUser.collectAsState()
    val themeMode by themeManager.themeMode.collectAsState()
    var currentTab by remember { mutableStateOf<Screen>(Screen.Items) }
    var isSearchActive by remember { mutableStateOf(false) }
    var globalSearchQuery by remember { mutableStateOf("") }

    val showBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in listOf(
        Screen.Items.route,
        Screen.Events.route,
        Screen.Categories.route,
        Screen.Type.route,
        Screen.Savings.route,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Drawer Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(24.dp),
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape),
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "UNIBUZZ",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 2.sp,
                                    ),
                                    color = NetflixRed,
                                )
                                Text(
                                    text = "Campus Community",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
                    label = { Text("Home", fontWeight = FontWeight.Bold) },
                    selected = currentTab == Screen.Items,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        currentTab = Screen.Items
                        navController.navigate(Screen.Items.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Favorite, contentDescription = null, tint = NetflixRed) },
                    label = { Text("My Wishlist", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Wishlist.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.NewReleases, contentDescription = null) },
                    label = { Text("Dev Releases", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.DevReleases.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Leaderboard, contentDescription = null) },
                    label = { Text("Leaderboard", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Leaderboard.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                    label = { Text("App Settings", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Settings.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Quiz, contentDescription = null) },
                    label = { Text("FAQs", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.Faqs.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Info, contentDescription = null) },
                    label = { Text("About & Developer", fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate(Screen.About.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))

                if (currentUser != null) {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Rounded.Logout, contentDescription = null, tint = NetflixRed) },
                        label = { Text("Sign Out", fontWeight = FontWeight.Bold, color = NetflixRed) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            authManager.logout()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                } else {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Rounded.Login, contentDescription = null, tint = ElectricViolet) },
                        label = { Text("Sign In", fontWeight = FontWeight.Bold, color = ElectricViolet) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Screen.Login.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                if (showBottomBar) {
                    Surface(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            if (isSearchActive) {
                                OutlinedTextField(
                                    value = globalSearchQuery,
                                    onValueChange = { globalSearchQuery = it },
                                    placeholder = { Text("Search listings & events...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null, tint = NetflixRed) },
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            globalSearchQuery = ""
                                            isSearchActive = false
                                        }) {
                                            Icon(Icons.Rounded.Close, contentDescription = "Close Search", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    },
                                    singleLine = true,
                                    shape = CircleShape,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        focusedBorderColor = NetflixRed,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp),
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    // Hamburger Menu Button
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch { drawerState.open() }
                                        },
                                        modifier = Modifier.size(36.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Menu,
                                            contentDescription = "Open Navigation Menu",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(24.dp),
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                                        contentDescription = "Unibuzz Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape),
                                    )

                                    Text(
                                        text = "UNIBUZZ",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 2.sp,
                                        ),
                                        color = NetflixRed,
                                    )
                                }
                            }

                            if (!isSearchActive) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    // Search Button
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .bounceOnClick { isSearchActive = true },
                                    ) {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            Icon(
                                                imageVector = Icons.Rounded.Search,
                                                contentDescription = "Search",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        }
                                    }

                                    // Theme Toggle Button
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .bounceOnClick { themeManager.toggleTheme() },
                                    ) {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            Icon(
                                                imageVector = if (themeMode == ThemeMode.LIGHT) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                                                contentDescription = "Toggle Theme",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        }
                                    }

                                    // About Info Button
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .bounceOnClick { navController.navigate(Screen.About.route) },
                                    ) {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            Icon(
                                                imageVector = Icons.Rounded.Info,
                                                contentDescription = "About App",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        }
                                    }

                                    // Profile / Auth Avatar Button
                                    Surface(
                                        shape = CircleShape,
                                        color = if (currentUser != null) ElectricViolet else MaterialTheme.colorScheme.surfaceVariant,
                                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .bounceOnClick { navController.navigate(Screen.Account.route) },
                                    ) {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            if (currentUser != null) {
                                                val initial = currentUser!!.fullName.firstOrNull()?.uppercase() ?: "U"
                                                Text(
                                                    text = initial,
                                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                                    color = Color.White,
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Rounded.AccountCircle,
                                                    contentDescription = "Profile",
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    modifier = Modifier.size(24.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        border = BorderStroke(1.dp, GlassBorderGradient),
                        tonalElevation = 12.dp,
                        shadowElevation = 16.dp,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        NavigationBar(
                            containerColor = Color.Transparent,
                            windowInsets = WindowInsets.navigationBars,
                        ) {
                            val navColors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            )

                            val itemsSelect = currentTab == Screen.Items
                            NavigationBarItem(
                                icon = {
                                    NavTabIcon(
                                        selected = itemsSelect,
                                        selectedIcon = Icons.Rounded.Storefront,
                                        unselectedIcon = Icons.Outlined.Storefront,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Market",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (itemsSelect) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                    )
                                },
                                selected = itemsSelect,
                                colors = navColors,
                                onClick = {
                                    currentTab = Screen.Items
                                    navController.navigate(Screen.Items.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            )

                            val eventsSelect = currentTab == Screen.Events
                            NavigationBarItem(
                                icon = {
                                    NavTabIcon(
                                        selected = eventsSelect,
                                        selectedIcon = Icons.Rounded.ConfirmationNumber,
                                        unselectedIcon = Icons.Outlined.ConfirmationNumber,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Events",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (eventsSelect) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                    )
                                },
                                selected = eventsSelect,
                                colors = navColors,
                                onClick = {
                                    currentTab = Screen.Events
                                    navController.navigate(Screen.Events.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            )

                            val catSelect = currentTab == Screen.Categories
                            NavigationBarItem(
                                icon = {
                                    NavTabIcon(
                                        selected = catSelect,
                                        selectedIcon = Icons.Rounded.Category,
                                        unselectedIcon = Icons.Outlined.Category,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Categories",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (catSelect) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                    )
                                },
                                selected = catSelect,
                                colors = navColors,
                                onClick = {
                                    currentTab = Screen.Categories
                                    navController.navigate(Screen.Categories.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            )

                            val typeSelect = currentTab == Screen.Type
                            NavigationBarItem(
                                icon = {
                                    NavTabIcon(
                                        selected = typeSelect,
                                        selectedIcon = Icons.Rounded.Explore,
                                        unselectedIcon = Icons.Outlined.Explore,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Explore",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (typeSelect) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                    )
                                },
                                selected = typeSelect,
                                colors = navColors,
                                onClick = {
                                    currentTab = Screen.Type
                                    navController.navigate(Screen.Type.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            )

                            val savingsSelect = currentTab == Screen.Savings
                            NavigationBarItem(
                                icon = {
                                    NavTabIcon(
                                        selected = savingsSelect,
                                        selectedIcon = Icons.Rounded.Savings,
                                        unselectedIcon = Icons.Outlined.Savings,
                                    )
                                },
                                label = {
                                    Text(
                                        text = "Savings",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (savingsSelect) FontWeight.Bold else FontWeight.Medium,
                                        ),
                                    )
                                },
                                selected = savingsSelect,
                                colors = navColors,
                                onClick = {
                                    currentTab = Screen.Savings
                                    navController.navigate(Screen.Savings.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Items.route,
                    enterTransition = {
                        fadeIn(animationSpec = tween(300)) + slideInHorizontally(initialOffsetX = { 120 }, animationSpec = tween(300))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { -120 }, animationSpec = tween(300))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(300)) + slideInHorizontally(initialOffsetX = { -120 }, animationSpec = tween(300))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { 120 }, animationSpec = tween(300))
                    },
                ) {
                    composable(Screen.Items.route) {
                        ItemListScreen(
                            onItemClick = { id -> navController.navigate(Screen.ItemDetail.createRoute(id)) },
                            onPostClick = { navController.navigate(Screen.PostItem.route) },
                            database = database,
                            externalSearchQuery = globalSearchQuery,
                        )
                    }
                    composable(Screen.PostItem.route) {
                        PostItemScreen(
                            onBack = { navController.popBackStack() },
                            database = database,
                        )
                    }
                    composable(
                        route = Screen.ItemDetail.route,
                        arguments = listOf(navArgument("itemId") { type = NavType.LongType }),
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getLong("itemId") ?: return@composable
                        ItemDetailScreen(
                            itemId = itemId,
                            onBack = { navController.popBackStack() },
                            onContact = { },
                            database = database,
                        )
                    }

                    composable(Screen.Events.route) {
                        EventListScreen(
                            onEventClick = { id -> navController.navigate(Screen.EventDetail.createRoute(id)) },
                            onPostClick = { navController.navigate(Screen.PostEvent.route) },
                            database = database,
                            externalSearchQuery = globalSearchQuery,
                        )
                    }
                    composable(Screen.PostEvent.route) {
                        PostEventScreen(
                            onBack = { navController.popBackStack() },
                            database = database,
                        )
                    }
                    composable(
                        route = Screen.EventDetail.route,
                        arguments = listOf(navArgument("eventId") { type = NavType.LongType }),
                    ) { backStackEntry ->
                        val eventId = backStackEntry.arguments?.getLong("eventId") ?: return@composable
                        EventDetailScreen(
                            eventId = eventId,
                            onBack = { navController.popBackStack() },
                            database = database,
                        )
                    }

                    composable(Screen.Categories.route) {
                        CategoryScreen(
                            onCategoryClick = {
                                navController.navigate(Screen.Items.route)
                            },
                        )
                    }

                    composable(Screen.Type.route) {
                        TypeScreen(
                            onTypeClick = {
                                navController.navigate(Screen.Events.route)
                            },
                        )
                    }

                    composable(Screen.Savings.route) {
                        SavingsScreen()
                    }

                    composable(Screen.Account.route) {
                        AccountScreen(
                            onBack = { navController.popBackStack() },
                            onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                            onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                            onNavigateToSavings = { navController.navigate(Screen.Savings.route) },
                        )
                    }

                    composable(Screen.About.route) {
                        AboutScreen(
                            onBack = { navController.popBackStack() },
                        )
                    }

                    composable(Screen.Wishlist.route) {
                        WishlistScreen(
                            onBack = { navController.popBackStack() },
                            onItemClick = { id -> navController.navigate(Screen.ItemDetail.createRoute(id)) },
                            database = database,
                        )
                    }

                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            onBack = { navController.popBackStack() },
                            onNavigateToAbout = { navController.navigate(Screen.About.route) },
                        )
                    }

                    composable(Screen.DevReleases.route) {
                        DevReleasesScreen(
                            onBack = { navController.popBackStack() },
                        )
                    }

                    composable(Screen.Leaderboard.route) {
                        LeaderboardScreen(
                            onBack = { navController.popBackStack() },
                        )
                    }

                    composable(Screen.Faqs.route) {
                        FaqsScreen(
                            onBack = { navController.popBackStack() },
                        )
                    }

                    composable(Screen.Login.route) {
                        LoginScreen(
                            onBack = { navController.popBackStack() },
                            onLoginSuccess = { navController.popBackStack() },
                            onNavigateToRegister = {
                                navController.popBackStack()
                                navController.navigate(Screen.Register.route)
                            },
                            database = database,
                        )
                    }

                    composable(Screen.Register.route) {
                        RegisterScreen(
                            onBack = { navController.popBackStack() },
                            onRegisterSuccess = { navController.popBackStack() },
                            onNavigateToLogin = {
                                navController.popBackStack()
                                navController.navigate(Screen.Register.route)
                            },
                            database = database,
                        )
                    }
                }
            }
        }
    }
}
