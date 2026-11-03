package com.example.exampleutapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exampleutapp.ui.theme.ExampleUTAppTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleUTAppTheme {
                var records by remember { mutableStateOf(UserDataRepository.getAllRecords()) }

                Scaffold { padding ->
                    ThirdScreen(
                        records = records,
                        onRemoveRecord = { record ->
                            UserDataRepository.removeRecord(record)
                            records = UserDataRepository.getAllRecords()
                        },
                        onBackToMain = {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        },
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun ThirdScreen(
    records: List<UserDataRepository.UserRecord>,
    onRemoveRecord: (UserDataRepository.UserRecord) -> Unit,
    onBackToMain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Report", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(records) { record ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "${record.firstName} ${record.paternalLastName} ${record.maternalLastName}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "RFC: ${record.rfc}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(onClick = { onRemoveRecord(record) }) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackToMain, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Main")
        }
    }
}
