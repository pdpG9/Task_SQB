package com.example.task_sqb.presentation.screens.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.task_sqb.data.local.room.entity.ContactEntity
import com.example.task_sqb.domain.usecase.DeleteContactUseCase
import com.example.task_sqb.domain.usecase.GetAllContactsUseCase
import com.example.task_sqb.domain.usecase.SyncDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelImp @Inject constructor(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val syncDataUseCase: SyncDataUseCase
) : ViewModel(), MainViewModel {
    override val contactsFlow = MutableLiveData<LiveData<List<ContactEntity>>>()
    override val messageFlow = MutableLiveData<String>()
    override val showProgressFlow = MutableLiveData<Boolean>()
    override val showPlaceHolderFlow = MutableLiveData<Boolean>()
    override val moveToAddDialogFlow = MutableLiveData<ContactEntity?>()

    init {
        showProgressFlow.value = true
        viewModelScope.launch {
            contactsFlow.value = getAllContactsUseCase.execute().asLiveData()
            showProgressFlow.value = false
        }
    }

    override fun clickAddContact() {
        moveToAddDialogFlow.value = null
    }

    override fun syncData() {
        showProgressFlow.value = true
        syncDataUseCase.execute().onEach {
            messageFlow.postValue(it)
            showProgressFlow.value = false
        }.launchIn(viewModelScope)
    }

    override fun clickUpdate(contactEntity: ContactEntity) {
        moveToAddDialogFlow.value = contactEntity
    }

    override fun clickDelete(contactEntity: ContactEntity) {
        deleteContactUseCase.execute(contactEntity).onEach { messageFlow.value = it }
            .launchIn(viewModelScope)
    }
}