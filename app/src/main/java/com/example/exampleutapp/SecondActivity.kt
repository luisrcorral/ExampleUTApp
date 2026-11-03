package com.example.exampleutapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exampleutapp.ui.theme.ExampleUTAppTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val firstName = intent.getStringExtra("FIRST_NAME") ?: ""
        val paternalLastName = intent.getStringExtra("PATERNAL_LAST_NAME") ?: ""
        val maternalLastName = intent.getStringExtra("MATERNAL_LAST_NAME") ?: ""
        val dob = intent.getStringExtra("DOB") ?: ""
        
        val rfc = RfcCalculator.calculate(firstName, paternalLastName, maternalLastName, dob)
        
        // Save to repository
        UserDataRepository.addRecord(UserDataRepository.UserRecord(firstName, paternalLastName, maternalLastName, rfc))

        setContent {
            ExampleUTAppTheme {
                Scaffold { padding ->
                    SecondScreen(
                        firstName = firstName,
                        paternalLastName = paternalLastName,
                        maternalLastName = maternalLastName,
                        rfc = rfc,
                        onBack = { finish() },
                        onGoToReport = {
                            startActivity(Intent(this, ThirdActivity::class.java))
                        },
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(
    firstName: String,
    paternalLastName: String,
    maternalLastName: String,
    rfc: String,
    onBack: () -> Unit,
    onGoToReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Name: $firstName $paternalLastName $maternalLastName", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "RFC: $rfc", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Main")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onGoToReport, modifier = Modifier.fillMaxWidth()) {
            Text("Go to Report")
        }
    }
}
