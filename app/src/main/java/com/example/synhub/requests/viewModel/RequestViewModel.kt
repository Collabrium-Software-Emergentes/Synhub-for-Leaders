package com.example.synhub.requests.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.requests.application.dto.RequestResponse
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RequestViewModel: ViewModel() {
    private val _request = MutableStateFlow<RequestResponse?>(null)
    val request: StateFlow<RequestResponse?> = _request

    private val _requests = MutableStateFlow<List<RequestResponse>>(emptyList())
    val requests: StateFlow<List<RequestResponse>> = _requests

    fun fetchRequestById(taskId: Long, requestId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.requestsWebService.getRequestById(taskId, requestId)
                if (response.isSuccessful && response.body() != null) {
                    _request.value = response.body()
                } else {
                    _request.value = null
                }
            } catch (e: Exception) {
                _request.value = null
            }
        }
    }

    fun updateRequestStatus(taskId: Long?, requestId: Long, status: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.requestsWebService.updateRequestStatus(taskId, requestId, status)
                if (response.isSuccessful && response.body() != null) {
                    _request.value = response.body()
                } else {
                    _request.value = null
                }
            } catch (e: Exception) {
                _request.value = null
            }
        }
    }

    // Not tested yet
    fun deleteRequest(taskId: Long, requestId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.requestsWebService.deleteRequest(taskId, requestId)
                if (response.isSuccessful) {
                    _request.value = null
                } else {
                    _request.value = null
                }
            } catch (e: Exception) {
                _request.value = null
            }
        }
    }

    fun fetchGroupRequests() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.requestsWebService.getGroupRequests()
                if (response.isSuccessful && response.body() != null) {
                    _requests.value = response.body()!!
                } else {
                    _requests.value = emptyList()
                }
            } catch (e: Exception) {
                _requests.value = emptyList()
            }
        }
    }
}