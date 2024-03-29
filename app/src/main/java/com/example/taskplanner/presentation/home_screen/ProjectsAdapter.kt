package com.example.taskplanner.presentation.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.data.model.Project
import com.example.taskplanner.data.util.extension.getStatusByOrdinal
import com.example.taskplanner.data.util.extension.setColorOnText
import com.example.taskplanner.databinding.RowProjectItemBinding

typealias onProjectClick = (projectId: String) -> Unit
class ProjectsAdapter : ListAdapter<Project, ProjectsAdapter.VH>(COMPARATOR) {

    lateinit var onProjectClick: onProjectClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = VH(
            RowProjectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        with(vh){
            binding.openButton.setOnClickListener {
                val project = getItem(adapterPosition)
                project.projectId?.let{ id -> onProjectClick.invoke(id)}
            }
        }

        return  vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }


    class VH( val binding: RowProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(project: Project) {
            with(binding) {
                val ctx = root.context
                nameTextView.text = project.projectTitle
                progressTextView.text =
                    ctx.getString(project.projectStatus.getStatusByOrdinal().title)
                progressTextView.setColorOnText(project.projectStatus.getStatusByOrdinal().color)
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