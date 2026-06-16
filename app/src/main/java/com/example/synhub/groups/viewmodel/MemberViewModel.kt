package com.example.synhub.groups.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.groups.application.dto.MemberResponse
import com.example.synhub.groups.application.dto.TaskResponse
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemberViewModel : ViewModel() {
    private val _members = MutableStateFlow<List<MemberResponse>>(emptyList())
    val members: StateFlow<List<MemberResponse>> = _members

    private val _haveMembers = MutableStateFlow(false)
    val haveMembers: StateFlow<Boolean> = _haveMembers

    private val _nextTaskMap = MutableStateFlow<Map<Long, TaskResponse?>>(emptyMap())
    val nextTaskMap: StateFlow<Map<Long, TaskResponse?>> = _nextTaskMap

    private val _memberDetails = MutableStateFlow<MemberResponse?>(null)
    val member: StateFlow<MemberResponse?> = _memberDetails

    private val _memberTasks = MutableStateFlow<List<TaskResponse>>(emptyList())
    val memberTasks: StateFlow<List<TaskResponse>> = _memberTasks

    //TO-DO: Borrar los logs de producción
    fun fetchGroupMembers() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.membersWebService.getGroupMembers()
                android.util.Log.d("MemberViewModel", "Respuesta miembros: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _members.value = response.body()!!
                    _haveMembers.value = _members.value.isNotEmpty()
                    android.util.Log.d("MemberViewModel", "Miembros obtenidos: ${_members.value.size}")
                } else {
                    _members.value = emptyList()
                    _haveMembers.value = false
                    android.util.Log.d("MemberViewModel", "No se obtuvieron miembros o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _members.value = emptyList()
                _haveMembers.value = false
                android.util.Log.e("MemberViewModel", "Error al obtener miembros", e)
            }
        }
    }

    //TO-DO: Borrar los logs de producción
    fun fetchNextTask(memberId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.membersWebService.getNextTaskForMember(memberId)
                android.util.Log.d("MemberViewModel", "Respuesta nextTask: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _nextTaskMap.value = _nextTaskMap.value.toMutableMap().apply { put(memberId, response.body()!!) }
                    android.util.Log.d("MemberViewModel", "NextTask obtenida: ${response.body()?.title}")
                } else {
                    _nextTaskMap.value = _nextTaskMap.value.toMutableMap().apply { put(memberId, null) }
                    android.util.Log.d("MemberViewModel", "No se obtuvo nextTask o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _nextTaskMap.value = _nextTaskMap.value.toMutableMap().apply { put(memberId, null) }
                android.util.Log.e("MemberViewModel", "Error al obtener nextTask", e)
            }
        }
    }

    //TO-DO: Borrar los logs de producción
    fun fetchMemberDetails(memberId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.membersWebService.getMemberDetails(memberId)
                android.util.Log.d("MemberViewModel", "Respuesta detalles miembro: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    val member = response.body()
                    _memberDetails.value = member
                    android.util.Log.d("MemberViewModel", "Detalles del miembro obtenidos: ${member?.username}")
                } else {
                    _memberDetails.value = null
                    android.util.Log.d("MemberViewModel", "No se obtuvieron detalles del miembro o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _memberDetails.value = null
                android.util.Log.e("MemberViewModel", "Error al obtener detalles del miembro", e)
            }
        }
    }

    fun fetchMemberTasks(memberId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.membersWebService.getMemberTasks(memberId)
                android.util.Log.d("MemberViewModel", "Respuesta tareas miembro: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _memberTasks.value = response.body()!!
                    android.util.Log.d("MemberViewModel", "Tareas del miembro obtenidas: ${response.body()?.size}")
                } else {
                    _memberTasks.value = emptyList()
                    android.util.Log.d("MemberViewModel", "No se obtuvieron tareas del miembro o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _memberTasks.value = emptyList()
                android.util.Log.e("MemberViewModel", "Error al obtener tareas del miembro", e)
            }
        }
    }
}