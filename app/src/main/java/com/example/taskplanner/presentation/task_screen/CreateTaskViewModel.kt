package com.example.taskplanner.presentation.task_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.repository.task.TaskRepository
import com.example.taskplanner.presentation.authorization.registration_screen.string
import com.example.taskplanner.presentation.base.ProjectBaseViewModel
import com.example.taskplanner.presentation.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    @ApplicationContext appCtx: Context,
    private val taskRepository: TaskRepository
) : ProjectBaseViewModel(appCtx) {

    fun setTask(task: Task) = viewModelScope.launch {
        with(task) {
            when {
                checkIfIsNull(startTime) || checkIfIsNull(endTime) -> {
                    mUploadItemState.emit(
                        ScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_choose_estimate_time
                            )
                        )
                    )
                }
                checkIfIsEmpty(taskTitle!!) || checkIfIsEmpty(taskDescription!!) -> {
                    mUploadItemState.emit(
                        ScreenState(
                            errorText = resourcesProvider.getString(
                                string.please_fill_all_fields
                            )
                        )
                    )
                }
                else -> {
                    handleResponse(
                        taskRepository.setTask(task),
                        mUploadItemState
                    )
                }
            }
        }
    }
}