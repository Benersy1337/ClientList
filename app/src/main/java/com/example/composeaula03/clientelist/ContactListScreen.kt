package com.example.composeaula03.clientelist

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.composeaula03.data.Cliente


@Composable
fun ContactListScreen(
    navController: NavController,
    clienteListViewModel: ClienteListViewModel,
) {
    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(onClick = {
                navController.navigate("addeditcliente")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Cliente")
            }
        }
    ) {
        val clienteList by clienteListViewModel.clienteList.observeAsState(listOf())
        val filter by clienteListViewModel.filterBy.observeAsState("")
        Column() {
            ProcuraCliente(
                filter,
                clienteListViewModel::updateFilter
            )
            ClienteList(
                clientes = clienteList,
                navController = navController
            )
        }
    }
}

@Composable
fun ProcuraCliente(
    filter: String,
    onFilterChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        label = {
                Row(){
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Procura Clientes")
                    Text(text = "Procurar Clientes")
                }
        },
        value = filter,
        onValueChange = onFilterChange
    )
}

@Composable
fun ClienteList(
    clientes: List<Cliente>,
    navController: NavController
) {
    LazyColumn(){
        items(clientes){ cliente ->
            ContactEntry(cliente = cliente){
                navController.navigate("addeditcliente?id=${cliente.id}")
            }
        }
    }
}

@Composable
fun ContactEntry(
    cliente: Cliente,
    onEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {
            Row(
                modifier = Modifier.
                fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    // Icone do layout

                    modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape)
                        .size(100.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        // Quadrado Do Nome
                        // Seleciona a primeira letra do nome e deixa maiscula
                        text = "${cliente.name[0].uppercase()}",
                        style = MaterialTheme.typography.h3
                            .copy(
                                fontWeight = FontWeight.W900,
                                fontFamily = FontFamily.Cursive
                            )
                    )
                }
                Text(
                    // Texto ao lado do Icone

                    modifier = Modifier
                        .padding(start = 20.dp)
                        .weight(10f),
                    text = cliente.name,
                    style = MaterialTheme.typography.h6
                        .copy(fontWeight = FontWeight.W300)
                )
                if(expanded){
                    // Icone Editar

                        //Atribuindo modificações ao icone
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(32.dp)
                            .clickable {
                                onEdit()

                            },
                        // 'Importando' o Icone
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
            }
            if(expanded){
                Text(
                    // Print do Input CPF
                    modifier = Modifier.
                    padding(start = 75.dp),
                    text = "CPF: ${cliente.number}",
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color.DarkGray,
                        // Uso de Letter spacing para usabilidade
                        letterSpacing = 0.5.em,
                        fontWeight = FontWeight.W900,
                    )
                )
                Text(
                    // Print do Input VENDA
                    modifier = Modifier
                        .padding(start = 75.dp),
                    text = "VENDAS: ${cliente.vendas}",
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color.DarkGray,
                        letterSpacing = 0.5.em,
                        fontWeight = FontWeight.W900,
                    )
                )
            }
        }
    }
}


