package com.msd.composelibrary.chpkg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api

data class Todo(
    var userId: Int,
    var id: Int,
    var title: String,
    var completed: Boolean
)
const val BASE_URL = "https://jsonplaceholder.typicode.com/"
interface APIService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    companion object {
        var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
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


