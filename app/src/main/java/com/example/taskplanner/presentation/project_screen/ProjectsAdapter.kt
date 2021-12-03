package com.example.taskplanner.presentation.project_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.util.extension.getStatusColorByTitle
import com.example.taskplanner.databinding.RowProjectItemBinding

class ProjectsAdapter : ListAdapter<Project, ProjectsAdapter.VH>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RowProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }


    class VH(private val binding: RowProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(project: Project) {
            with(binding) {
                nameTextView.text = project.projectTitle
                progressTextView.text = project.projectStatus
                progressTextView.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        project.projectStatus.getStatusColorByTitle()
                    )
                )
            }
        }
    }

    object COMPARATOR : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.projectId == newItem.projectId
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

    }

}