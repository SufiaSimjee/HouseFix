package com.example.housefix.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.housefix.databinding.ListFragmentBinding
import com.example.housefix.recycleview.ProblemListAdapter
import com.example.housefix.viewModel.ProblemListViewModel
import kotlinx.coroutines.launch

class ProblemListFragment: Fragment() {

    private val problemListModel: ProblemListViewModel by viewModels()
    private var _binding: ListFragmentBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding because it is null"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding
            .inflate(inflater , container , false)

        binding.problemRecycleView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                problemListModel.problems.collect { problems ->
                    binding.problemRecycleView.adapter = ProblemListAdapter(problems) { problemId ->
                        findNavController().navigate(ProblemListFragmentDirections.showDetailFragment(problemId))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}