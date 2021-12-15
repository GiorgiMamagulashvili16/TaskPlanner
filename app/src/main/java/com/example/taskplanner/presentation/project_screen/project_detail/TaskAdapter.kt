package com.example.taskplanner.presentation.project_screen.project_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.data.model.Task
import com.example.taskplanner.data.util.extension.getStatusByOrdinal
import com.example.taskplanner.data.util.extension.setColorOnText
import com.example.taskplanner.databinding.RowTaskItemBinding

typealias onTaskClick = (taskId: String) -> Unit

class TaskAdapter : ListAdapter<Task, TaskAdapter.VH>(COMPARATOR) {

    lateinit var onTaskClick: onTaskClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RowTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position), onTaskClick)
    }

    class VH(private val binding: RowTaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Task, onTaskClick: onTaskClick) {
            with(binding) {
                val ctx = root.context
                taskTitleTextView.text = task.taskTitle
                statusTextView.text = ctx.getString(task.status.getStatusByOrdinal().title)
                statusTextView.setColorOnText(task.status.getStatusByOrdinal().color)
                root.setOnClickListener {
                    task.taskId?.let { id -> onTaskClick.invoke(id) }
                }
            }
        }
    }

    object COMPARATOR : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}