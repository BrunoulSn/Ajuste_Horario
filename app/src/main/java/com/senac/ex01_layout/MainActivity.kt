package com.senac.ex01_layout

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senac.ex01_layout.model.Priority
import com.senac.ex01_layout.model.Status
import com.senac.ex01_layout.ui.theme.Ex01LayoutTheme
import com.senac.ex01_layout.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex01LayoutTheme {
                Layout01()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout01() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { UILabBottomBar() },
        topBar = { UILabTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Fields()
        }
    }
}

@Composable
private fun Fields() {

    val taskViewModel: TaskViewModel = viewModel()
    val state = taskViewModel.uiState.collectAsState()

    val context = LocalContext.current
    val dateTime = state.value.dateTime

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Text(text = stringResource(R.string.title))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value.title,
        onValueChange = { taskViewModel.updateTitle(it) }
    )

    Text(text = stringResource(R.string.status))

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = state.value.status == Status.Done,
            onClick = { taskViewModel.updateStatus(Status.Done) }
        )
        Text(text = stringResource(R.string.done))

        RadioButton(
            selected = state.value.status == Status.NotDone,
            onClick = { taskViewModel.updateStatus(Status.NotDone) }
        )
        Text(text = stringResource(R.string.not_done))
    }

    Text(text = "Priority")

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = state.value.priority == Priority.Low,
            onClick = { taskViewModel.updatePriority(Priority.Low) }
        )
        Text(text = stringResource(R.string.low))

        RadioButton(
            selected = state.value.priority == Priority.Medium,
            onClick = { taskViewModel.updatePriority(Priority.Medium) }
        )
        Text(text = stringResource(R.string.medium))

        RadioButton(
            selected = state.value.priority == Priority.High,
            onClick = { taskViewModel.updatePriority(Priority.High) }
        )
        Text(text = stringResource(R.string.high))
    }

    Text(text = stringResource(R.string.time_and_date))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = dateTime.format(dateFormatter))
        Text(text = dateTime.format(timeFormatter))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // DATE PICKER
        Button(onClick = {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    taskViewModel.updateDate(
                        LocalDate.of(year, month + 1, day)
                    )
                },
                dateTime.year,
                dateTime.monthValue - 1,
                dateTime.dayOfMonth
            ).show()
        }) {
            Text(text = stringResource(R.string.choose_date))
        }

        // TIME PICKER
        Button(onClick = {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    taskViewModel.updateTime(
                        LocalTime.of(hour, minute)
                    )
                },
                dateTime.hour,
                dateTime.minute,
                true
            ).show()
        }) {
            Text(text = stringResource(R.string.choose_time))
        }
    }
}

@Composable
private fun UILabBottomBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = {}) {
            Text(text = stringResource(R.string.cancel))
        }
        Button(onClick = {}) {
            Text(text = stringResource(R.string.reset))
        }
        Button(onClick = {}) {
            Text(text = stringResource(R.string.submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UILabTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_title)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    Ex01LayoutTheme {
        Layout01()
    }
}