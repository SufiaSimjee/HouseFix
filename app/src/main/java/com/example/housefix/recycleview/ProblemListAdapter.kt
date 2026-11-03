package com.example.housefix.recycleview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.housefix.databinding.ListItemBinding
import com.example.housefix.model.Problem


class ProblemHolder(val binding: ListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(problem: Problem , onDataClicked: (problemId: Int) -> Unit) {

        binding.tvTitle.text = problem.title
        binding.tvDate.text = problem.date.toString()

        if (problem.isSolved) {
            binding.tvStatus.text = "Solved"
            binding.tvStatus.setTextColor(Color.parseColor("#15803D"))
            binding.tvStatus.setBackgroundColor(Color.parseColor("#DCFCE7"))
        } else {
            binding.tvStatus.text = "Unsolved"
            binding.tvStatus.setTextColor(Color.parseColor("#D97706"))
            binding.tvStatus.setBackgroundColor(Color.parseColor("#FEF3C7"))
        }

        binding.root.setOnClickListener {
            onDataClicked(problem.id)
        }
    }
    }

class ProblemListAdapter(private val problems: List<Problem>,
                         private val onDataClicked: (problemId: Int) -> Unit)
    : RecyclerView.Adapter<ProblemHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProblemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater , parent , false)
        return ProblemHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProblemHolder,
        position: Int
    ) {
       val problem = problems[position]
        holder.bind(problem , onDataClicked)
    }

    override fun getItemCount(): Int = problems.size
}