package com.example.exampleutapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exampleutapp.ui.theme.ExampleUTAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleUTAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        onNavigateToSecond = { firstName, paternalLastName, maternalLastName, dob ->
                            val intent = Intent(this, SecondActivity::class.java).apply {
                                putExtra("FIRST_NAME", firstName)
                                putExtra("PATERNAL_LAST_NAME", paternalLastName)
                                putExtra("MATERNAL_LAST_NAME", maternalLastName)
                                putExtra("DOB", dob)
                            }
                            startActivity(intent)
                        },
                        onNavigateToReport = {
                            startActivity(Intent(this, ThirdActivity::class.java))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToSecond: (String, String, String, String) -> Unit,
    onNavigateToReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf("") }
    var paternalLastName by remember { mutableStateOf("") }
    var maternalLastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") } // Format: YYYY-MM-DD
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "RFC Calculator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = paternalLastName,
            onValueChange = { paternalLastName = it },
            label = { Text("Paternal Last Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = maternalLastName,
            onValueChange = { maternalLastName = it },
            label = { Text("Maternal Last Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of Birth (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                if (firstName.isBlank() || paternalLastName.isBlank() || dob.isBlank()) {
                    errorMessage = "Name, Paternal Last Name and DOB are required"
                } else if (!dob.matches(Regex("^\\d{4}-\\d{2}-\\d{2}$"))) {
                    errorMessage = "Invalid date format (YYYY-MM-DD)"
                } else {
                    errorMessage = ""
                    onNavigateToSecond(firstName, paternalLastName, maternalLastName, dob)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate RFC")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToReport) {
            Text("View Records Report")
        }
    }
}
