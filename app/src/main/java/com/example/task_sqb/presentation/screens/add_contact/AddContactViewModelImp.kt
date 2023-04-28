package com.example.task_sqb.presentation.screens.add_contact

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.data.local.room.entity.ContactState
import com.example.task_sqb.data.network.request.AddContactRequest
import com.example.task_sqb.domain.usecase.AddContactUseCase
import com.example.task_sqb.domain.usecase.GetContactByIdUseCase
import com.example.task_sqb.domain.usecase.UpdateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModelImp @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) :
    ViewModel(), AddContactViewModel {
    override val showProgressFlow = MutableLiveData<Boolean>()
    override val defContactFlow = MutableLiveData<ContactEntity>()
    override val moveToBackFlow = MutableLiveData<Unit>()
    override val messageFlow = MutableLiveData<String>()

    override fun loadDefContact(id: Int) {
        getContactByIdUseCase.execute(id).onEach {
            defContactFlow.value = it
        }.launchIn(viewModelScope)
    }

    override fun clickClose() {
        moveToBackFlow.value = Unit
    }

    override fun clickSave(addContactRequest: AddContactRequest) {
        if (checkInputs(addContactRequest)) {
            showProgressFlow.value = true
            var id = -1
            defContactFlow.value?.let { id = it.id }
            val state =
                if (id != -1) ContactState.OFFLINE_CHANGED.value else ContactState.OFFLINE_ADDED.value
            if (id != -1) {
                Log.d("TAG", "clickSave: updateContactUseCase")
                updateContactUseCase.execute(
                    ContactEntity(
                        id,
                        addContactRequest.firstName,
                        addContactRequest.lastName,
                        addContactRequest.phone,
                        state
                    )
                ).onEach {
                    messageFlow.value = it
                    moveToBackFlow.value = Unit
                }.launchIn(viewModelScope)
            } else {
                Log.d("TAG", "clickSave: addContactUseCase")
                addContactUseCase.execute(addContactRequest).onEach {
                    moveToBackFlow.value = Unit
                Log.d("TAG", "clickSave: $it")
                    messageFlow.value = it }
                    .launchIn(viewModelScope) }
                showProgressFlow.value = false
        }
    }

    private fun checkInputs(addContactRequest: AddContactRequest): Boolean {
        if (addContactRequest.firstName.length <= 3) {
            messageFlow.value = "first name must be longer than 3!"
            return false
        }
        if (addContactRequest.lastName.length <= 3) {
            messageFlow.value = "last name must be longer than 3!"
            return false
        }
        Log.d("TAG", "checkInputs: ${addContactRequest.phone}")
        if (addContactRequest.phone.length != 13) {
            messageFlow.value = "Phone number is invalid!"
            return false
        }
        return true
    }
}