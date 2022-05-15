package com.example.composeaula03.addeditcontact

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeaula03.data.Cliente

@Composable
fun AddEditClienteScreen(
    navController: NavController,
    addEditClienteListViewModel: AddEditClienteViewModel,
    onInsertCliente: (Cliente) -> Unit,
    onUpdateCliente: (Cliente) -> Unit,
    onRemoveCliente: (Int) -> Unit,
    cliente: Cliente
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(cliente.id == -1)
                    addEditClienteListViewModel.insertContact(onInsertCliente)
                else
                    addEditClienteListViewModel.updateCliente(
                        cliente.id,
                        onUpdateCliente
                    )

                navController.navigate("clientelist"){
                    popUpTo("clientelist"){
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirmar")
            }
        }
    ) {
        addEditClienteListViewModel.name.value = cliente.name
        addEditClienteListViewModel.number.value = cliente.number
        addEditClienteListViewModel.vendas.value = cliente.vendas

        AddEditClienteForm(
            addEditClienteListViewModel,
            cliente.id,
            onRemoveCliente,
        ){
            navController.navigate("clientelist"){
                popUpTo("clientelist"){
                    inclusive = true
                }
            }
        }

    }
}

@Composable
fun AddEditClienteForm(
    addEditClienteListViewModel: AddEditClienteViewModel,
    id: Int,
    onRemoveCliente: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    var name = addEditClienteListViewModel.name.observeAsState()
    var number = addEditClienteListViewModel.number.observeAsState()
    var vendas = addEditClienteListViewModel.vendas.observeAsState()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                label = {
                    Text(text = "Seu Nome")
                },
                value = "${name.value}",
                onValueChange = { newName->
                    addEditClienteListViewModel.name.value = newName
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),

                label = {
                    Text(text = "Seu CPF")
                },
                value = "${number.value}",
                onValueChange = { newNumber->
                    addEditClienteListViewModel.number.value = newNumber
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "O número da sua venda")
                },
                value = "${vendas.value}",
                onValueChange = { newVendas->
                    addEditClienteListViewModel.vendas.value = newVendas
                }
            )
        }
        if(id != -1)
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    onRemoveCliente(id)
                    navigateBack()
                }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
    }
}
