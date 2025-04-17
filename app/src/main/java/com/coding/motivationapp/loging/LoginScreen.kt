package com.coding.motivationapp.loging

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun LoginScreen() {
    val auth = Firebase.auth

    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        TextField(
            value = emailState.value,
            onValueChange = {
                emailState.value = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordState.value,
            onValueChange = {
                passwordState.value = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                signIn(auth, emailState.value, passwordState.value)
            }
        ) {
            Text(
                text = "Sign In"
            )
        }

        Button(
            onClick = {
                signUp(auth, emailState.value, passwordState.value)
            }
        ) {
            Text(
                text = "Sign Up"
            )
        }

        Button(
            onClick = {
                signOut(auth)
            }
        ) {
            Text(
                text = "Sign Out"
            )
        }
    }
}

private fun signUp(auth: FirebaseAuth, email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyLog", "Sign up is successful")
            } else {
                Log.d("MyLog", "Sign up is not successful")
            }
        }
}

private fun signIn(auth: FirebaseAuth, email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyLog", "Sign in is successful")
            } else {
                Log.d("MyLog", "Sign in is not successful")
            }
        }
}

private fun signOut(auth: FirebaseAuth) {
    auth.signOut()
}

private fun deleteAccount(auth: FirebaseAuth, email: String, password: String) {
    val credential = EmailAuthProvider.getCredential(email, password)
    auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
        if (it.isSuccessful) {
            auth.currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("MyLog", "Was deleted")
                } else {
                    Log.d("MyLog", "Was not deleted")
                }
            }
        } else {
            Log.d("MyLog", "Failure reauthenticate")
        }
    }
}