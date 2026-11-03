package com.example.housefix.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.housefix.databinding.DetailFragmentBinding
import com.example.housefix.model.Problem
import com.example.housefix.viewModel.ProblemDetailViewModel
import com.example.housefix.viewModel.ProblemDetailViewModelFactory
import kotlinx.coroutines.launch

class ProblemDetailFragment: Fragment() {

    private val args: ProblemDetailFragmentArgs by navArgs()

    private val problemDetailViewModel: ProblemDetailViewModel by viewModels {
        ProblemDetailViewModelFactory(args.problemId)
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etTitle.doOnTextChanged { text, _, _, _ ->
                problemDetailViewModel.updateData { oldData ->
                    oldData.copy(title = text.toString())
                }
            }

            etDescription.doOnTextChanged { text, _, _, _ ->
                problemDetailViewModel.updateData { oldData ->
                    oldData.copy(description = text.toString())
                }
            }

            switchSolved.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    problemDetailViewModel.updateData { oldData -> oldData.copy(isSolved = true) }
                } else {
                    problemDetailViewModel.updateData { oldData -> oldData.copy(isSolved = false) }
                }
            }

            btnSubmit.setOnClickListener {
                problemDetailViewModel.saveProblem()
                findNavController().popBackStack()
            }

            imgBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                problemDetailViewModel.problem.collect { problem ->
                    problem?.let { updateUi(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(problem: Problem) {
        binding.apply {
            if (etTitle.text.toString() != problem.title) {
                etTitle.setText(problem.title)
            }

            if (etDescription.text.toString() != problem.description) {
                etDescription.setText(problem.description)
            }

            if (switchSolved.isChecked != problem.isSolved) {
                switchSolved.isChecked = problem.isSolved
            }

            tvDate.text = problem.date.toString()
        }
    }
}