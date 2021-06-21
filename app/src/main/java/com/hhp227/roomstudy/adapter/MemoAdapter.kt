package com.hhp227.roomstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.roomstudy.databinding.ItemMemoBinding
import com.hhp227.roomstudy.model.MemoDto

class MemoAdapter : ListAdapter<MemoDto, MemoAdapter.MemoViewHolder>(MemoDtoDiffCallback()) {
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class MemoViewHolder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemoDto) = with(binding) {
            memo = item

            executePendingBindings()
        }

        init {
            binding.root.setOnClickListener { v -> onItemClickListener.onItemClick(v, adapterPosition) }
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(v: View, p: Int)
    }
}

private class MemoDtoDiffCallback : DiffUtil.ItemCallback<MemoDto>() {
    override fun areItemsTheSame(oldItem: MemoDto, newItem: MemoDto): Boolean {
        return oldItem.id== newItem.id
    }

    override fun areContentsTheSame(oldItem: MemoDto, newItem: MemoDto): Boolean {
        return oldItem == newItem
    }
}