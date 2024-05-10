package com.msd.composelibrary.chpkg

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun generateForm(context: Context,
                 onFormSubmitCallback: (Context) -> Unit = {},
                 onFormbackCallback: (Context) -> Unit = {}){
    var consumerName by rememberSaveable { mutableStateOf("") }
    var businessPartner by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .border(5.dp, color = Color.Magenta)
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.padding(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Consumer Name",
                    style = MaterialTheme.typography.labelSmall
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = consumerName,
                    onValueChange = { consumerName = it },
                    placeholder = { Text(text = "e.g. Mahendra Singh") },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Business Partner",
                    style = MaterialTheme.typography.labelSmall
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = businessPartner,
                    onValueChange = { businessPartner = it },
                    placeholder = { Text(text = "e.g. Business Partner") },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Address",
                    style = MaterialTheme.typography.labelSmall
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = address,
                    onValueChange = { address = it },
                    placeholder = { Text(text = "e.g. Address") },
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                    onClick = { onFormSubmitCallback(context) }) {
                    Text(
                        text = "Submit",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Button(modifier = Modifier
                    .width(150.dp)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                    onClick = { onFormbackCallback(context) }, ) {
                    Text(
                        text = "Back",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }

}



@Composable
fun HelloFunction(context:Context){
    Toast.makeText(context, "",Toast.LENGTH_SHORT)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoView(vm: TodoViewModel, onBackButtonClick: () -> Unit = {}) {

    LaunchedEffect(Unit, block = {
        vm.getTodoList()
    })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    Text("Todos")
                    Button(onClick = {
                        onBackButtonClick()
                    }) {
                        Text("Click to Back")
                    }
                }
            })
        },
        content = {
            if(vm.errorMessage.isEmpty()){
                Column(modifier = Modifier.padding(15.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(vm.todoList){
                                todo ->
                            Column {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Box(   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 0.dp, 16.dp, 0.dp) ){
                                        Checkbox(checked = todo.completed, onCheckedChange = null)
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            modifier = Modifier.padding(start = 10.dp),
                                            text = todo.title, maxLines = 2,
                                            overflow = TextOverflow.Ellipsis)


                                    }
                                    Divider()
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                Text(vm.errorMessage)
            }
        }
    )
}