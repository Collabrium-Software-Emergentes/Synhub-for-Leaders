package com.example.synhub.invitations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.invitations.application.dto.InvitationResponse
import com.example.synhub.invitations.model.response.InvitationsWebService
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InvitationViewModel : ViewModel() {
    private val _groupInvitations = MutableStateFlow<List<InvitationResponse>>(emptyList())
    val groupInvitations: StateFlow<List<InvitationResponse>> = _groupInvitations

    fun fetchGroupInvitations() {
        viewModelScope.launch {
            try {
                android.util.Log.d("InvitationViewModel", "Iniciando petición getGroupInvitations")
                val response = RetrofitClient.invitationsWebService.getGroupInvitations()
                android.util.Log.d("InvitationViewModel", "Respuesta recibida: $response")
                _groupInvitations.value = response
                android.util.Log.d("InvitationViewModel", "Invitaciones almacenadas en StateFlow: ${_groupInvitations.value.size}")
            } catch (e: Exception) {
                android.util.Log.e("InvitationViewModel", "Error al obtener invitaciones de grupo", e)
                _groupInvitations.value = emptyList()
            }
        }
    }

    fun processInvitation(invitationId: Long, accept: Boolean) {
        viewModelScope.launch {
            try {
                android.util.Log.d("InvitationViewModel", "Procesando invitación $invitationId con accept=$accept")
                val response = RetrofitClient.invitationsWebService.processInvitation(invitationId, accept)
                if (response.isSuccessful) {
                    android.util.Log.d("InvitationViewModel", "Invitación procesada correctamente")
                } else {
                    android.util.Log.e("InvitationViewModel", "Error al procesar invitación: ${response.code()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("InvitationViewModel", "Excepción al procesar invitación", e)
            }
        }
    }
}