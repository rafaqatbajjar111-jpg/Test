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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
fun RegisterScreen(
    viewModel: AuthViewModel,
    initialReferralCode: String? = null,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var referralCode by remember { mutableStateOf(initialReferralCode ?: "") }
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

    LaunchedEffect(initialReferralCode) {
        if (!initialReferralCode.isNullOrBlank()) {
            referralCode = initialReferralCode
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                onRegisterSuccess()
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
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x3352B788), Color.Transparent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    StarbucksLogo(size = 80.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Join Starbucks PSP",
                    color = PrimaryDarkGreen,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Sign up and earn premium daily rewards together",
                    color = Color(0x991B4332),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                // High-fidelity Frosted Glass Card
                FrostedGlassCard {
                    Text(
                        text = "Create Account",
                        color = PrimaryDarkGreen,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone Glass Input
                    GlassTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        placeholderText = "Enter phone number",
                        leadingIcon = Icons.Default.Phone,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Glass Input
                    GlassTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholderText = "Enter password (min 6 chars)",
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

                    Spacer(modifier = Modifier.height(12.dp))

                    // Referral Code Glass Input
                    GlassTextField(
                        value = referralCode,
                        onValueChange = { referralCode = it },
                        placeholderText = "Referral Code (optional)",
                        leadingIcon = Icons.Default.Info
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Register Action Button
                    Button(
                        onClick = {
                            if (!email.contains("@") || !email.contains(".") || phone.length < 10 || password.length < 6) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Valid email, phone (10 digits) and password (min 6 chars) are required")
                                }
                            } else {
                                val refVal = if (referralCode.isBlank()) null else referralCode
                                viewModel.register(
                                    email = email,
                                    phone = phone,
                                    password = password,
                                    referralCode = refVal
                                )
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
                                text = "Create Account",
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
                    Text(text = "Already have an account? ", color = Color(0x991B4332), fontSize = 14.sp)
                    Text(
                        text = "Login",
                        color = AccentGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onNavigateToLogin() }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
