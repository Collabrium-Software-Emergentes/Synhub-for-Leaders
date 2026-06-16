package com.example.synhub.groups.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.groups.application.dto.GroupMember
import com.example.synhub.groups.application.dto.GroupRequest
import com.example.synhub.groups.application.dto.GroupResponse
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupViewModel : ViewModel() {
    private val _group = MutableStateFlow<GroupResponse?>(null)
    val group: StateFlow<GroupResponse?> = _group

    private val _haveGroup = MutableStateFlow(false)
    val haveGroup: StateFlow<Boolean> = _haveGroup

    private val _members = MutableStateFlow<List<GroupMember>>(emptyList())
    val members: StateFlow<List<GroupMember>> = _members

    fun fetchLeaderGroup() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.groupWebService.getLeaderGroups()
                android.util.Log.d("GroupViewModel", "Respuesta del endpoint: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    val group = response.body()!!
                    _group.value = group
                    _haveGroup.value = true
                    android.util.Log.d("GroupViewModel", "Grupo encontrado: ${group.name}")
                } else if (response.code() == 404) {
                    _group.value = null
                    _haveGroup.value = false
                    android.util.Log.d("GroupViewModel", "No se encontró grupo (404)")
                } else {
                    _group.value = null
                    _haveGroup.value = false
                    android.util.Log.d("GroupViewModel", "Respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _group.value = null
                _haveGroup.value = false
                android.util.Log.e("GroupViewModel", "Error al obtener grupo", e)
            }
        }
    }

    fun fetchGroupMembers() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.groupWebService.getGroupMembers()
                android.util.Log.d("GroupViewModel", "Respuesta miembros: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _members.value = response.body()!!
                    android.util.Log.d("GroupViewModel", "Miembros obtenidos: ${_members.value.size}")
                } else {
                    _members.value = emptyList()
                    android.util.Log.d("GroupViewModel", "No se obtuvieron miembros o respuesta inesperada: ${response.code()}")
                }
            } catch (e: Exception) {
                _members.value = emptyList()
                android.util.Log.e("GroupViewModel", "Error al obtener miembros", e)
            }
        }
    }

    fun createGroup(groupRequest: GroupRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                android.util.Log.d("GroupViewModel", "Intentando crear grupo: ${groupRequest.name}")
                val response = RetrofitClient.groupWebService.createGroup(groupRequest)
                android.util.Log.d("GroupViewModel", "Respuesta creación grupo: ${response.code()} - body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _group.value = response.body()
                    _haveGroup.value = true
                    android.util.Log.d("GroupViewModel", "Grupo creado exitosamente: ${response.body()!!.name}")
                    onResult(true)
                } else {
                    android.util.Log.d("GroupViewModel", "Fallo al crear grupo: ${response.code()} - ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                android.util.Log.e("GroupViewModel", "Error al crear grupo", e)
                onResult(false)
            }
        }
    }

    fun deleteGroupMember(memberId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.groupWebService.deleteGroupMember(memberId)
                if (response.isSuccessful) {
                    android.util.Log.d("GroupViewModel", "Miembro eliminado correctamente")
                    fetchGroupMembers() // Actualiza la lista de miembros
                } else {
                    android.util.Log.e("GroupViewModel", "Error al eliminar miembro: ${response.code()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("GroupViewModel", "Excepción al eliminar miembro", e)
            }
        }
    }

}
