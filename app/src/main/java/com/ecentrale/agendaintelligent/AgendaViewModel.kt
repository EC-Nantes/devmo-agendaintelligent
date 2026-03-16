package com.ecentrale.agendaintelligent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Event(
    val id: Int,
    val title: String,
    val day: String,
    val dayIndex: Int,
    val startHour: Int,
    val endHour: Int,
    val color: EventColor,
    val source: String,
    val isDisplaced: Boolean = false
)

enum class EventColor { BLUE, YELLOW, RED, GREEN }

enum class ConflictResolution { NONE, ACCEPTED, MANUAL, DELETED }

data class AgendaUiState(
    val events: List<Event> = initialEvents(),
    val conflictResolved: Boolean = false,
    val conflictResolution: ConflictResolution = ConflictResolution.NONE
)

fun initialEvents(): List<Event> = listOf(
    Event(1, "Cours UML", "LUN 27", 0, 9, 10, EventColor.BLUE, "ÉCOLE"),
    Event(2, "Révisions Physique", "MER 29", 2, 14, 16, EventColor.YELLOW, "ÉCOLE"),
    Event(3, "Réunion projet", "MER 29", 2, 10, 11, EventColor.BLUE, "ÉCOLE"),
    Event(4, "RDV Doctolib", "MER 29", 2, 14, 16, EventColor.RED, "MÉDICAL"),
    Event(5, "Examen Maths", "JEU 30", 3, 9, 11, EventColor.RED, "ÉCOLE"),
    Event(6, "Sport", "VEN 31", 4, 10, 11, EventColor.GREEN, "PERSONNEL"),
    Event(7, "TP Android", "LUN 27", 0, 14, 16, EventColor.BLUE, "ÉCOLE"),
    Event(8, "TP Réseau", "JEU 30", 3, 14, 16, EventColor.BLUE, "ÉCOLE")
)

class AgendaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AgendaUiState())
    val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

    // Suggestion : déplace Révisions → MAR 28 · 10h-12h
    fun acceptSuggestion() {
        val currentEvents = _uiState.value.events.toMutableList()
        val idx = currentEvents.indexOfFirst { it.id == 2 }
        if (idx != -1) {
            currentEvents[idx] = currentEvents[idx].copy(
                day = "MAR 28",
                dayIndex = 1,
                startHour = 10,
                endHour = 12,
                isDisplaced = true
            )
        }
        _uiState.value = _uiState.value.copy(
            events = currentEvents,
            conflictResolved = true,
            conflictResolution = ConflictResolution.ACCEPTED
        )
    }

    // Manuel : déplace Révisions selon choix user
    // newStart = 12 → 12h-14h, newStart = 16 → 16h-18h
    fun saveManualEdit(newDay: String, newDayIndex: Int, newStart: Int) {
        val newEnd = newStart + 2
        val currentEvents = _uiState.value.events.toMutableList()
        val idx = currentEvents.indexOfFirst { it.id == 2 }
        if (idx != -1) {
            currentEvents[idx] = currentEvents[idx].copy(
                day = newDay,
                dayIndex = newDayIndex,
                startHour = newStart,
                endHour = newEnd,
                isDisplaced = true
            )
        }
        _uiState.value = _uiState.value.copy(
            events = currentEvents,
            conflictResolved = true,
            conflictResolution = ConflictResolution.MANUAL
        )
    }

    // Supprimer Révisions Physique
    fun deleteRevisionsPhysique() {
        val currentEvents = _uiState.value.events.filter { it.id != 2 }
        _uiState.value = _uiState.value.copy(
            events = currentEvents,
            conflictResolved = true,
            conflictResolution = ConflictResolution.DELETED
        )
    }

    fun reset() {
        _uiState.value = AgendaUiState()
    }
}