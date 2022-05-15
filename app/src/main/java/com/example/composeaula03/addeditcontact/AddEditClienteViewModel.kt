package com.example.composeaula03.addeditcontact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Cliente

class AddEditClienteViewModel : ViewModel() {

    private val _id : MutableLiveData<Int> = MutableLiveData(5)

    val name : MutableLiveData<String> = MutableLiveData("")
    val number : MutableLiveData<String> = MutableLiveData("")
    val vendas : MutableLiveData<String> = MutableLiveData("")

    fun insertContact(
        onInsertContact: (Cliente) -> Unit
    ){
        val newContact = Cliente(
            _id.value ?: return,
            name.value ?: return,
            number.value?: return,
            vendas.value?: return
        )
        onInsertContact(newContact)
        var tempId: Int = _id.value ?: return
        tempId++
        _id.value = tempId

        name.value = ""
        number.value = ""
        vendas.value = ""
    }

    fun updateCliente(
        id: Int,
        onUpdateCliente: (Cliente) -> Unit
    ){
        val cliente = Cliente(
            id,
            name.value ?: return,
            number.value ?: return,
            vendas.value ?: return,
        )
        onUpdateCliente(cliente)
    }
}