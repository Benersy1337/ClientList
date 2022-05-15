package com.example.composeaula03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeaula03.addeditcontact.AddEditClienteScreen
import com.example.composeaula03.addeditcontact.AddEditClienteViewModel
import com.example.composeaula03.clientelist.ContactListScreen
import com.example.composeaula03.clientelist.ClienteListViewModel
import com.example.composeaula03.ui.theme.ComposeClienteList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clienteListViewModel: ClienteListViewModel by viewModels()
        val addEditClienteListViewModel: AddEditClienteViewModel by viewModels()

        setContent {
            ComposeClienteList {
                MyApp(
                    clienteListViewModel,
                    addEditClienteListViewModel
                )
            }
        }
    }
}

@Composable
fun MyApp(
    clienteListViewModel: ClienteListViewModel,
    addEditClienteListViewModel: AddEditClienteViewModel
) {
    val navController = rememberNavController()
    Scaffold(){
        NavHost(navController = navController, startDestination = "clientelist"){
            composable("clientelist"){
                ContactListScreen(navController, clienteListViewModel)
            }
            composable(
                route = "addeditcliente?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val contact = clienteListViewModel.getCliente(id)
                AddEditClienteScreen(
                    navController,
                    addEditClienteListViewModel,
                    clienteListViewModel::insertCliente,
                    clienteListViewModel::updateCliente,
                    clienteListViewModel::removeCliente,
                    contact
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeClienteList {

    }
}