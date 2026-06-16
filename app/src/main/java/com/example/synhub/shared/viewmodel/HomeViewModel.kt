package com.example.synhub.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synhub.shared.application.dto.Leader
import com.example.synhub.shared.model.client.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _leader = MutableStateFlow<Leader?>(null)
    val leader: StateFlow<Leader?> = _leader

    fun fetchLeaderDetails() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.homeWebService.getLeaderDetails()
                if (response.isSuccessful) {
                    _leader.value = response.body()
                } else {
                    _leader.value = null
                }
            } catch (e: Exception) {
                _leader.value = null
            }
        }
    }
}