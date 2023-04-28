package com.example.task_sqb.presentation.screens.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task_sqb.R
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.databinding.ScreenMainBinding
import com.example.task_sqb.presentation.adapter.ContactAdapter
import com.example.task_sqb.presentation.screens.main.vm.MainViewModelImp
import com.example.task_sqb.utils.networkStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private val vm by viewModels<MainViewModelImp>()
    private val adapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.moveToAddDialogFlow.observe(this, moveToShowContactEntity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        vm.apply {
            contactsFlow.observe(viewLifecycleOwner, contactListObserver)
            messageFlow.observe(viewLifecycleOwner, messageObserver)
            showProgressFlow.observe(viewLifecycleOwner, showProgressObserver)
        }
        networkStateFlow.observe(viewLifecycleOwner, networkStateObserver)
        binding.btAdd.setOnClickListener { findNavController().navigate(MainScreenDirections.actionMainScreenToAddContactScreen()) }
        binding.swiperLayout.setOnRefreshListener { vm.syncData() }

    }

    private val contactListObserver = Observer<LiveData<List<ContactEntity>>> {
        it.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (list.isEmpty()) binding.tvPlaceHolder.visibility = View.VISIBLE
            else binding.tvPlaceHolder.visibility = View.INVISIBLE
        }
    }
    private val networkStateObserver = Observer<Boolean> {
        if (it) vm.syncData()
    }
    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val showProgressObserver = Observer<Boolean> {
        binding.swiperLayout.isRefreshing = it
    }
    private val moveToShowContactEntity = Observer<ContactEntity?> {
        findNavController().navigate(
            R.id.action_mainScreen_to_addContactScreen,
            bundleOf("contact_id" to it?.id)
        )
    }

    private fun initAdapter() {
        adapter.setDeleteClickListener { contact ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete contact")
                .setMessage("Do you want delete ${contact.firstName}?")
                .setPositiveButton("Yes") { d, _ ->
                    vm.clickDelete(contact)
                    d.dismiss()
                }
                .setNegativeButton("No") { d, _ -> d.dismiss() }.show()
        }
        adapter.setEditeClickListener { vm.clickUpdate(it) }
        binding.rvContacts.adapter = adapter
    }

}