package com.example.task_sqb.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_sqb.R
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.databinding.ItemContactBinding

class ContactAdapter : ListAdapter<ContactEntity, ContactAdapter.ContactViewHolder>(MyDiffUtil) {
    private lateinit var deleteClickListener: ((ContactEntity) -> Unit)
    private lateinit var editeClickListener: ((ContactEntity) -> Unit)
    fun setDeleteClickListener(listener: (ContactEntity) -> Unit) {
        deleteClickListener = listener
    }

    fun setEditeClickListener(listener: (ContactEntity) -> Unit) {
        editeClickListener = listener
    }

    object MyDiffUtil : DiffUtil.ItemCallback<ContactEntity>() {
        override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity) =
            oldItem == newItem
    }

    inner class ContactViewHolder(binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name = binding.tvName
        private val phone = binding.tvNumberContact
        private val btMore = binding.btMore

        init {
            btMore.setOnClickListener { showPopUpMenu(btMore, currentList[bindingAdapterPosition]) }
        }

        fun bind(position: Int) {
            name.text = ("${currentList[position].firstName} ${currentList[position].lastName}")
            phone.text = currentList[position].phone
        }
    }

    private fun showPopUpMenu(view: View, currentItem: ContactEntity) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.menu_more)
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.bt_delete) {
                deleteClickListener.invoke(currentItem)
            } else {
                editeClickListener.invoke(currentItem)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            ItemContactBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            )
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind(position)
}