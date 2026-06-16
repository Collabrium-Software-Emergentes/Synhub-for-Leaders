package com.example.synhub.shared.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.shared.application.dto.SignInRequest
import com.example.synhub.shared.application.dto.SignInResponse
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    var signInResponse: SignInResponse? = null
        private set

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    fun signIn(username: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("LogInViewModel", "Iniciando login para usuario: $username")
                val response = RetrofitClient.logInWebService.signIn(SignInRequest(username, password))
                Log.d("LogInViewModel", "Respuesta del servidor: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        signInResponse = it
                        RetrofitClient.updateToken(it.token)
                        Log.d("LogInViewModel", "Login exitoso. Token actualizado.")
                        _loginSuccess.value = true
                        return@launch
                    }
                }
                Log.d("LogInViewModel", "Login fallido. Código: ${response.code()}")
                _loginSuccess.value = false
            } catch (e: Exception) {
                Log.e("LogInViewModel", "Exception: ${e.message}", e)
                _loginSuccess.value = false
            }
        }
    }

    suspend fun getLeaderDetails(): Boolean {
        return try {
            val response = RetrofitClient.homeWebService.getLeaderDetails()
            response.isSuccessful && response.body() != null
        } catch (e: Exception) {
            false
        }
    }

    // Nueva función para resetear el estado de loginSuccess
    fun resetLoginState() {
        _loginSuccess.value = null
    }
}