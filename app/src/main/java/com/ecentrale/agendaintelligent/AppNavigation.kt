package com.ecentrale.agendaintelligent

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecentrale.agendaintelligent.ui.ConflictScreen
import com.ecentrale.agendaintelligent.ui.DeleteScreen
import com.ecentrale.agendaintelligent.ui.EditScreen
import com.ecentrale.agendaintelligent.ui.WeekScreen

object Routes {
    const val WEEK = "week"
    const val CONFLICT = "conflict"
    const val EDIT = "edit"
    const val DELETE = "delete"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: AgendaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.WEEK
    ) {
        composable(Routes.WEEK) {
            WeekScreen(
                viewModel = viewModel,
                onConflictClick = {
                    navController.navigate(Routes.CONFLICT)
                }
            )
        }
        composable(Routes.CONFLICT) {
            ConflictScreen(
                viewModel = viewModel,
                onAccept = {
                    viewModel.acceptSuggestion()
                    navController.navigate(Routes.WEEK) {
                        popUpTo(Routes.WEEK) { inclusive = true }
                    }
                },
                onEditManually = {
                    navController.navigate(Routes.EDIT)
                },
                onDelete = {
                    navController.navigate(Routes.DELETE)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.EDIT) {
            EditScreen(
                onSave = { day, dayIndex, start ->
                    viewModel.saveManualEdit(day, dayIndex, start)
                    navController.navigate(Routes.WEEK) {
                        popUpTo(Routes.WEEK) { inclusive = true }
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.DELETE) {
            DeleteScreen(
                onConfirm = {
                    viewModel.deleteRevisionsPhysique()
                    navController.navigate(Routes.WEEK) {
                        popUpTo(Routes.WEEK) { inclusive = true }
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}