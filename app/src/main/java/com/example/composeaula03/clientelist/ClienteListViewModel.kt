package com.example.composeaula03.clientelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Cliente

class ClienteListViewModel : ViewModel() {

    private val _clienteList: MutableLiveData<List<Cliente>> = MutableLiveData(
        listOf(
            Cliente(
                0,
                "Charles Santos",
                "50024856324",
                "000012354"
            ) ,
            Cliente(
                1,
                "Antonio Limbas",
                "00245235674",
                "000045892"
            ) ,
            Cliente(
                2,
                "Luan Roberto",
                "51345715468",
                "000041578"
            ) ,
        )
    )

    private val _filterBy: MutableLiveData<String> = MutableLiveData("")

    val filterBy: LiveData<String>
        get() = _filterBy

    val clienteList: LiveData<List<Cliente>>
        get() {
            return if(_filterBy.value == "")
                _clienteList
            else{
                val list: List<Cliente> = _clienteList.value?.filter { cliente ->
                    cliente.name.contains(_filterBy.value ?: "")
                } ?: listOf()
                MutableLiveData(list)
            }
        }

    fun updateFilter(newFilter: String) {
        _filterBy.value = newFilter
    }

    fun insertCliente(cliente: Cliente){
        val list: MutableList<Cliente> = _clienteList.value?.toMutableList() ?: return
        list.add(cliente)
        _clienteList.value = list
    }

    fun updateCliente(updatedCliente: Cliente){
        var pos = -1
        _clienteList.value?.forEachIndexed { index, cliente ->
            if(updatedCliente.id == cliente.id)
                pos = index
        }
        val list: MutableList<Cliente> = _clienteList.value?.toMutableList() ?: return
        list.removeAt(pos)
        list.add(pos, updatedCliente)
        _clienteList.value = list
    }

    fun removeCliente(id: Int){
        var pos = -1
        _clienteList.value?.forEachIndexed { index, cliente ->
            if(id == cliente.id)
                pos = index
        }
        val list: MutableList<Cliente> = _clienteList.value?.toMutableList() ?: return
        list.removeAt(pos)
        _clienteList.value = list
    }

    fun getCliente(id: Int): Cliente {
        _clienteList.value?.forEach{ cliente ->
            if(id == cliente.id)
                return cliente
        }
        return Cliente(
            -1,
            "",
            "",
            ""
        )
    }

}