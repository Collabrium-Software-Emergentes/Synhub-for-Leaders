package com.example.synhub.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.tasks.application.dto.TaskRequest
import com.example.synhub.tasks.application.dto.TaskResponse
import com.example.synhub.shared.model.client.RetrofitClient
import com.example.synhub.tasks.application.dto.EditTaskRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskResponse>>(emptyList())
    val tasks: StateFlow<List<TaskResponse>> = _tasks

    private val _task = MutableStateFlow<TaskResponse?>(null)
    val task: StateFlow<TaskResponse?> = _task

    private val _tasksMap = MutableStateFlow<Map<Long, TaskResponse>>(emptyMap())
    val tasksMap: StateFlow<Map<Long, TaskResponse>> = _tasksMap

    //TO-DO: Borrar los logs de depuración antes de la entrega final
    fun fetchGroupTasks() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.tasksWebService.getGroupTasks()
                android.util.Log.d("TaskViewModel", "Respuesta tareas: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _tasks.value = response.body()!!
                    android.util.Log.d("TaskViewModel", "Tareas obtenidas: ${_tasks.value.size}")
                } else {
                    _tasks.value = emptyList()
                    android.util.Log.d("TaskViewModel", "No se obtuvieron tareas o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _tasks.value = emptyList()
                android.util.Log.e("TaskViewModel", "Error al obtener tareas", e)
            }
        }
    }

    //TO-DO: Borrar los logs de depuración antes de la entrega final
    fun fetchTaskById(taskId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.tasksWebService.getTaskById(taskId)
                android.util.Log.d("TaskViewModel", "Respuesta tarea por ID: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    val task = response.body()!!
                    _task.value = task
                    android.util.Log.d("TaskViewModel", "Tarea obtenida: ${task.title}")
                } else {
                    _task.value = null
                    android.util.Log.d("TaskViewModel", "No se obtuvo tarea o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _task.value = null
                android.util.Log.e("TaskViewModel", "Error al obtener tarea por ID", e)
            }
        }
    }

    fun createTask(
        memberId: Long,
        taskRequest: TaskRequest
    ) {
        viewModelScope.launch {
            try {
                android.util.Log.d("TaskViewModel", "Iniciando creación de tarea para miembro: $memberId")
                val response = RetrofitClient.tasksWebService.createTask(memberId, taskRequest)
                android.util.Log.d("TaskViewModel", "Respuesta creación tarea: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _task.value = response.body()
                    android.util.Log.d("TaskViewModel", "Tarea creada exitosamente: ${response.body()!!.title}")
                } else {
                    android.util.Log.e("TaskViewModel", "Fallo al crear tarea: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("TaskViewModel", "Error al crear tarea", e)
            }
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            try {
                android.util.Log.d("TaskViewModel", "Iniciando eliminación de tarea: $taskId")
                val response = RetrofitClient.tasksWebService.deleteTask(taskId)
                android.util.Log.d("TaskViewModel", "Respuesta eliminación tarea: ${response.code()}")
                if (response.isSuccessful) {
                    fetchGroupTasks()
                    android.util.Log.d("TaskViewModel", "Tarea eliminada exitosamente: $taskId")
                } else {
                    android.util.Log.e("TaskViewModel", "Fallo al eliminar tarea: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("TaskViewModel", "Error al eliminar tarea", e)
            }
        }
    }

    fun updateTask(taskId: Long, editTaskRequest: EditTaskRequest) {
        viewModelScope.launch {
            try {
                android.util.Log.d("TaskViewModel", "Iniciando actualización de tarea: $taskId Request: $editTaskRequest")
                val response = RetrofitClient.tasksWebService.updateTask(taskId, editTaskRequest)
                android.util.Log.d("TaskViewModel", "Respuesta actualización tarea: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _task.value = response.body()
                    android.util.Log.d("TaskViewModel", "Tarea actualizada exitosamente: ${response.body()!!.title}")
                } else {
                    android.util.Log.e("TaskViewModel", "Fallo al actualizar tarea: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("TaskViewModel", "Error al actualizar tarea", e)
            }
        }
    }
    
    fun updateTaskStatus(taskId: Long?, status: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.tasksWebService.updateTaskStatus(taskId, status)
                android.util.Log.d("TaskViewModel", "Respuesta actualización estado tarea: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _task.value = response.body()
                    android.util.Log.d("TaskViewModel", "Estado de tarea actualizado exitosamente: ${response.body()!!.title} a $status")
                } else {
                    android.util.Log.e("TaskViewModel", "Fallo al actualizar estado de tarea: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("TaskViewModel", "Error al actualizar estado de tarea", e)
            }
        }
    }
}