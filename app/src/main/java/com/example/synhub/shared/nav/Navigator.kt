package com.example.synhub.shared.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.synhub.analytics.views.AnalyticsAndReports
import com.example.synhub.groups.views.CreateGroup
import com.example.synhub.groups.views.EditGroup
import com.example.synhub.groups.views.Group
import com.example.synhub.groups.views.MemberDetails
import com.example.synhub.groups.views.Members
import com.example.synhub.requests.views.GroupRequestList
import com.example.synhub.requests.views.EditRequestTask
import com.example.synhub.requests.views.ValidationView
import com.example.synhub.shared.model.client.RetrofitClient
import com.example.synhub.shared.views.Home
import com.example.synhub.shared.views.Login
import com.example.synhub.shared.views.Register
import com.example.synhub.tasks.views.CreateTask
import com.example.synhub.tasks.views.EditTask
import com.example.synhub.tasks.views.TaskDetail
import com.example.synhub.tasks.views.Tasks
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.example.synhub.invitations.views.Invitations

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigator(
    initialGroupCode: String? = null
){
    val rememberScreen = rememberNavController()

    LaunchedEffect(initialGroupCode) {
        if (initialGroupCode != null) {
            rememberScreen.navigate(
                "Group/$initialGroupCode"
            )
        }
    }

    NavHost(
        navController = rememberScreen,
        startDestination = "Login"
    ) {
        // Autenticación y registro
        composable("Login") { Login(rememberScreen) }
        composable("Register") { Register(rememberScreen) }

        // Pantalla principal
        composable("Home") { Home(rememberScreen) }

        navigation(
            route = "group_graph",
            startDestination = "Group"
        ) {

            composable("Group") {
                Group(rememberScreen)
            }

            composable(
                route = "Group/{groupCode}",
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "synhub://group/{groupCode}"
                    }
                )
            ) { backStackEntry ->

                val groupCode =
                    backStackEntry.arguments
                        ?.getString("groupCode")

                Group(
                    nav = rememberScreen,
                    groupCode = groupCode
                )
            }

            composable("Group/CreateGroup") {
                CreateGroup(rememberScreen)
            }

            composable("Group/Edit") {
                EditGroup(rememberScreen)
            }

            composable("Group/Members") {
                Members(rememberScreen)
            }

            composable("Group/Member/{memberId}") {

                val memberId =
                    it.arguments
                        ?.getString("memberId")

                MemberDetails(
                    rememberScreen,
                    memberId
                )
            }

            composable("Group/Invitations") {
                Invitations(rememberScreen)
            }
        }

        // Solicitudes/Solicitudes de grupo
        composable("GroupRequests") { GroupRequestList(rememberScreen) }
        composable("Validation/{taskId}/{requestId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val requestId = backStackEntry.arguments?.getString("requestId")
            ValidationView(rememberScreen, taskId, requestId) }
        composable("Validation/Edit/{taskId}/{requestId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val requestId = backStackEntry.arguments?.getString("requestId")
            EditRequestTask(rememberScreen, taskId, requestId)
        }

        // Tareas
        composable("Tasks") { Tasks(rememberScreen) }
        composable("Tasks/Create") { CreateTask(rememberScreen) }
        composable("Tasks/Detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            TaskDetail(rememberScreen, taskId)
        }
        composable("Tasks/Edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            EditTask(rememberScreen, taskId)
        }

        // Analítica
        composable("AnalyticsAndReports") {
            AnalyticsAndReports(
                nav = rememberScreen,
                token = RetrofitClient.token ?: ""
            )
        }
    }
}