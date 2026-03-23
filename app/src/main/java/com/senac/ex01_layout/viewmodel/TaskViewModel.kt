package com.senac.ex01_layout.viewmodel

import androidx.lifecycle.ViewModel
import com.senac.ex01_layout.model.Priority
import com.senac.ex01_layout.model.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class TaskUiState(
    val title: String = "",
    val status: Status = Status.NotDone,
    val priority: Priority = Priority.Low,
    val dateTime: LocalDateTime = LocalDateTime.now()
)

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateStatus(status: Status) {
        _uiState.value = _uiState.value.copy(status = status)
    }

    fun updatePriority(priority: Priority) {
        _uiState.value = _uiState.value.copy(priority = priority)
    }

    fun updateDate(date: LocalDate) {
        val current = _uiState.value.dateTime
        _uiState.value = _uiState.value.copy(
            dateTime = LocalDateTime.of(date, current.toLocalTime())
        )
    }

    fun updateTime(time: LocalTime) {
        val current = _uiState.value.dateTime
        _uiState.value = _uiState.value.copy(
            dateTime = LocalDateTime.of(current.toLocalDate(), time)
        )
    }
}