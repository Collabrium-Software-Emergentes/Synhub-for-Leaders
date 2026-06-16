package com.example.synhub.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.shared.application.dto.SignUpRequest
import com.example.synhub.shared.application.dto.SignUpResponse
import com.example.synhub.shared.model.client.RetrofitClient
import com.example.synhub.shared.model.response.RegisterWebService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel() : ViewModel() {
    private val _signUpResult = MutableStateFlow<Response<SignUpResponse>?>(null)
    val signUpResult: StateFlow<Response<SignUpResponse>?> = _signUpResult

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                android.util.Log.d("RegisterViewModel", "Iniciando registro para usuario: ${request.username}")
                val response = RetrofitClient.registerWebService.signUp(request)
                android.util.Log.d("RegisterViewModel", "Respuesta del servidor: ${response.code()} - ${response.message()}")
                _signUpResult.value = response
            } catch (e: Exception) {
                android.util.Log.e("RegisterViewModel", "Error en el registro", e)
                _signUpResult.value = null
            }
        }
    }
}
