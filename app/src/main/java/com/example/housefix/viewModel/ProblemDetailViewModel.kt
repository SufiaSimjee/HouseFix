package com.example.housefix.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.housefix.model.Problem
import com.example.housefix.repository.ProblemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProblemDetailViewModel(problemId:Int): ViewModel() {
    private val problemRepository = ProblemRepository.get()
    private val _problem: MutableStateFlow<Problem?> = MutableStateFlow(null)
    val problem : StateFlow<Problem?> = _problem.asStateFlow()

    init {
        viewModelScope.launch {
            _problem.value = problemRepository.getData(problemId)
        }
    }

    fun updateData(onUpdate: (Problem) -> Problem) {
        _problem.update { oldProblem ->
            oldProblem?.let { onUpdate(it) }
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        problem.value?.let { problemRepository.updateData(it) }
//    }

    fun saveProblem() {
        _problem.value?.let { problemRepository.updateData(it) }
    }

}

class ProblemDetailViewModelFactory(private val problemId:Int)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProblemDetailViewModel(problemId) as T
    }
}