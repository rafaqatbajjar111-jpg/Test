package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AuthUiState
import com.example.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showGoogleDialog by remember { mutableStateOf(false) }
    var showAppleDialog by remember { mutableStateOf(false) }
    var showPhoneDialog by remember { mutableStateOf(false) }
    var selectedSocialProvider by remember { mutableStateOf("") }
    var selectedSocialEmail by remember { mutableStateOf("") }

    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? android.app.Activity

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is AuthUiState.Error -> {
                snackbarHostState.showSnackbar((uiState as AuthUiState.Error).message)
            }
            else -> {}
        }
    }

    if (showGoogleDialog) {
        GoogleAccountPickerDialog(
            onDismissRequest = { showGoogleDialog = false },
            onAccountSelected = { selectedEmail ->
                showGoogleDialog = false
                selectedSocialEmail = selectedEmail
                selectedSocialProvider = "Google"
                showPhoneDialog = true
            }
        )
    }

    if (showAppleDialog) {
        AppleSignInDialog(
            onDismissRequest = { showAppleDialog = false },
            onAccountSelected = { selectedEmail ->
                showAppleDialog = false
                selectedSocialEmail = selectedEmail
                selectedSocialProvider = "Apple"
                showPhoneDialog = true
            }
        )
    }

    if (showPhoneDialog) {
        SocialPhoneAndReferralDialog(
            onDismissRequest = { showPhoneDialog = false },
            onConfirm = { phone, referralCode ->
                showPhoneDialog = false
                activity?.let {
                    viewModel.signInWithSocialProvider(
                        activity = it,
                        provider = selectedSocialProvider,
                        email = selectedSocialEmail,
                        phone = phone,
                        referralCode = referralCode
                    )
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        // Glassmorphism dynamic liquid background
        AnimatedLiquidBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Beautiful glowing brand logo
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x3352B788), Color.Transparent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    StarbucksLogo(size = 90.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome Back",
                    color = PrimaryDarkGreen,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Log in to your premium Starbucks account",
                    color = Color(0x991B4332),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 28.dp)
                )

                // High-fidelity Frosted Glass Card
                FrostedGlassCard {
                    Text(
                        text = "Sign In",
                        color = PrimaryDarkGreen,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        textAlign = TextAlign.Start
                    )

                    // Email Glass Input
                    GlassTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholderText = "Enter email address",
                        leadingIcon = Icons.Default.Email,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Glass Input
                    GlassTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholderText = "Enter password",
                        leadingIcon = Icons.Default.Lock,
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility",
                                    tint = Color(0x991B4332)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Action Button with Starbucks premium style
                    Button(
                        onClick = {
                            if (!email.contains("@") || !email.contains(".") || password.length < 6) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please enter a valid email and password (min 6 chars)")
                                }
                            } else {
                                viewModel.login(email, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGreen,
                            disabledContainerColor = Color(0x4D52B788)
                        ),
                        enabled = uiState !is AuthUiState.Loading
                    ) {
                        if (uiState is AuthUiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = "Sign In Now",
                                color = PrimaryDarkGreen,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Google & Apple Premium Social Logins requested by user
                    PremiumSocialLogins(
                        onGoogleClick = {
                            showGoogleDialog = true
                        },
                        onAppleClick = {
                            showAppleDialog = true
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Modern Footer Link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(text = "Don't have an account? ", color = Color(0x991B4332), fontSize = 14.sp)
                    Text(
                        text = "Sign Up",
                        color = AccentGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onNavigateToRegister() }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
