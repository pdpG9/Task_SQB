package com.example.task_sqb.presentation.screens.add_contact

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task_sqb.R
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.network.request.AddContactRequest
import com.example.task_sqb.databinding.ScreenAddContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactScreen : Fragment(R.layout.screen_add_contact) {
    private val binding: ScreenAddContactBinding by viewBinding(ScreenAddContactBinding::bind)
    private val vm: AddContactViewModel by viewModels<AddContactViewModelImp>()
    private val arg: AddContactScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arg.contactId > 0) {
            vm.loadDefContact(arg.contactId)
        }
        vm.apply {
            defContactFlow.observe(viewLifecycleOwner, defContactObserver)
            showProgressFlow.observe(viewLifecycleOwner, showProgressObserver)
            moveToBackFlow.observe(viewLifecycleOwner, moveToBackObserver)
            messageFlow.observe(viewLifecycleOwner, messageObserver)
        }
        binding.apply {
            btCancel.setOnClickListener { vm.clickClose() }
            btSave.setOnClickListener {
                vm.clickSave(
                    AddContactRequest(
                        inputFirstName.text.toString(),
                        inputLastName.text.toString(),
                        phoneInput.text.toString().replace("-", "")
                    )
                )
            }
        }

    }

    private val defContactObserver = Observer<ContactEntity> {
        binding.inputFirstName.setText(it.firstName)
        binding.inputLastName.setText(it.lastName)
        binding.phoneInput.setText(it.phone)
    }
    private val showProgressObserver = Observer<Boolean> {
        if (it) binding.progress.visibility = View.VISIBLE
        else binding.progress.visibility = View.GONE
    }
    private val moveToBackObserver = Observer<Unit> {
        findNavController().popBackStack()
    }
    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}