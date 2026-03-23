package com.senac.ex01_layout.model;

import java.time.LocalDateTime

data class Task (
    val title: String = "",
    val status: Status = Status.NotDone,
    val priority: Priority = Priority.Low,
    val dateTime: LocalDateTime = LocalDateTime.now()
)

enum class Status() {
    Done,
    NotDone
}

enum class Priority() {
    Low,
    Medium,
    High
}