package com.example.housefix.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housefix.model.Problem
import com.example.housefix.repository.ProblemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProblemListViewModel: ViewModel() {
    private val problemRepository: ProblemRepository = ProblemRepository.get()

    private val _problems: MutableStateFlow<List<Problem>> = MutableStateFlow(emptyList())
    val problems: StateFlow<List<Problem>> get() = _problems.asStateFlow()

    init {
        viewModelScope.launch {
            problemRepository.getAllData().collect {
                _problems.value = it
            }
        }
    }
}