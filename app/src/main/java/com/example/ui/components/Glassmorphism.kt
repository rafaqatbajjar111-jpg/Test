package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Info
import com.example.R
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.PrimaryDarkGreen
import kotlin.math.sin

@Composable
fun AnimatedLiquidBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "LiquidBG")

    // Animate coordinates for premium floating liquid glass spheres
    val orb1X by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1X"
    )
    val orb1Y by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1Y"
    )

    val orb2X by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2X"
    )
    val orb2Y by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(11000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2Y"
    )

    val orb3X by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3X"
    )
    val orb3Y by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(13000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3Y"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F0EC), // Ultra premium bright clean soft mint
                        Color(0xFFFAF9F6), // Warm alabaster white background
                        Color(0xFFF3F7F5)
                    )
                )
            )
    ) {
        // Drawing the premium ambient floating blurry liquid glow shapes (pastel version for white UI)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .blur(70.dp) // Generates the beautiful soft mist glass blending
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Orb 1: Rich Starbucks Accent Green Pastel
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x3D52B788), Color.Transparent),
                    center = androidx.compose.ui.geometry.Offset(canvasWidth * orb1X, canvasHeight * orb1Y),
                    radius = canvasWidth * 0.45f
                ),
                center = androidx.compose.ui.geometry.Offset(canvasWidth * orb1X, canvasHeight * orb1Y),
                radius = canvasWidth * 0.45f
            )

            // Orb 2: Elegant Starbucks Accent Gold Pastel
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x31CBA258), Color.Transparent),
                    center = androidx.compose.ui.geometry.Offset(canvasWidth * orb2X, canvasHeight * orb2Y),
                    radius = canvasWidth * 0.4f
                ),
                center = androidx.compose.ui.geometry.Offset(canvasWidth * orb2X, canvasHeight * orb2Y),
                radius = canvasWidth * 0.4f
            )

            // Orb 3: Soft Mint Green
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x3882C0A5), Color.Transparent),
                    center = androidx.compose.ui.geometry.Offset(canvasWidth * orb3X, canvasHeight * orb3Y),
                    radius = canvasWidth * 0.5f
                ),
                center = androidx.compose.ui.geometry.Offset(canvasWidth * orb3X, canvasHeight * orb3Y),
                radius = canvasWidth * 0.5f
            )
        }

        // Main content overlays
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@Composable
fun FrostedGlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    // Elegant dynamic asymmetrical corners requested by user ("corner side rotation/asymmetric curve")
    val shape = RoundedCornerShape(
        topStart = 32.dp,
        bottomEnd = 32.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(
                BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0x99FFFFFF), // Bright reflective top-left
                            Color(0x3352B788), // Soft mint accent border glow
                            Color(0x26000000)  // Subtle bottom-right grounding
                        )
                    )
                ),
                shape = shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xE6FFFFFF) // Beautiful pure frosted translucent solid white glass (90% opacity)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Premium elevated shadow for white UI depth
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default
) {
    val shape = RoundedCornerShape(16.dp)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholderText, color = Color(0x7A1B4332), fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = PrimaryDarkGreen
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        shape = shape,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = PrimaryDarkGreen,
            unfocusedTextColor = PrimaryDarkGreen,
            focusedContainerColor = Color(0xFFF1F6F3),
            unfocusedContainerColor = Color(0xFFFAF9F6),
            focusedBorderColor = AccentGreen,
            unfocusedBorderColor = Color(0x261B4332),
            cursorColor = AccentGreen
        )
    )
}

@Composable
fun PremiumSocialLogins(
    onGoogleClick: () -> Unit,
    onAppleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0x1F1B4332),
                thickness = 1.dp
            )
            Text(
                text = "OR CONTINUE WITH",
                color = Color(0x8A1B4332),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp),
                letterSpacing = 1.5.sp
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color(0x1F1B4332),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Premium Google Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color(0x1F1B4332)), RoundedCornerShape(16.dp))
                    .clickable { onGoogleClick() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Google",
                        color = PrimaryDarkGreen,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            // Premium Apple Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, Color(0x1F1B4332)), RoundedCornerShape(16.dp))
                    .clickable { onAppleClick() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = "Apple Logo",
                        tint = PrimaryDarkGreen,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Apple ID",
                        color = PrimaryDarkGreen,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun GoogleAccountPickerDialog(
    onDismissRequest: () -> Unit,
    onAccountSelected: (String) -> Unit
) {
    var customEmail by remember { mutableStateOf("") }
    var isAddingAccount by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = true),
        confirmButton = {},
        dismissButton = {},
        shape = RoundedCornerShape(28.dp),
        containerColor = Color.White,
        modifier = Modifier.padding(16.dp),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Google Logo Header
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose an account",
                    color = Color(0xFF1F1F1F),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "to continue to Starbucks PSP",
                    color = Color(0xFF444746),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                if (!isAddingAccount) {
                    // Option 1: Primary user account (from metadata! "rkkhatab872@gmail.com")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onAccountSelected("rkkhatab872@gmail.com") }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF3F51B5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "R",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "RK Khatab",
                                color = Color(0xFF1F1F1F),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "rkkhatab872@gmail.com",
                                color = Color(0xFF444746),
                                fontSize = 12.sp
                            )
                        }
                    }

                    HorizontalDivider(color = Color(0xFFE3E3E3), thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))

                    // Option 2: Use another account
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { isAddingAccount = true }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(BorderStroke(1.dp, Color(0xFFC4C7C5)), CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add account",
                                tint = Color(0xFF444746),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Use another account",
                            color = Color(0xFF1F1F1F),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    // Custom Gmail Entry Form
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = customEmail,
                            onValueChange = { customEmail = it },
                            label = { Text("Email or phone") },
                            placeholder = { Text("example@gmail.com") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF0B57D0),
                                focusedLabelColor = Color(0xFF0B57D0)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { isAddingAccount = false }) {
                                Text("Back", color = Color(0xFF0B57D0))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    if (customEmail.isNotBlank() && customEmail.contains("@")) {
                                        onAccountSelected(customEmail)
                                    }
                                },
                                shape = RoundedCornerShape(100.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B57D0))
                            ) {
                                Text("Next", color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "To continue, Google will share your name, email address, language preference, and profile picture with Starbucks PSP. See Starbucks PSP's Privacy Policy and Terms of Service.",
                    color = Color(0xFF444746),
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    )
}

@Composable
fun AppleSignInDialog(
    onDismissRequest: () -> Unit,
    onAccountSelected: (String) -> Unit
) {
    var appleEmail by remember { mutableStateOf("") }
    var applePassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {},
        dismissButton = {},
        shape = RoundedCornerShape(28.dp),
        containerColor = Color(0xFF1C1C1E), // Dark iOS Apple style background
        modifier = Modifier.padding(16.dp),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Apple logo
                Icon(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = "Apple Logo",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Sign in with Apple ID",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Enter your Apple ID credentials to continue securely to Starbucks PSP.",
                    color = Color(0xFF8E8E93),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                OutlinedTextField(
                    value = appleEmail,
                    onValueChange = { appleEmail = it },
                    placeholder = { Text("Apple ID (email address)", color = Color(0xFF8E8E93)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF0A84FF),
                        unfocusedBorderColor = Color(0xFF3A3A3C),
                        focusedContainerColor = Color(0xFF2C2C2E),
                        unfocusedContainerColor = Color(0xFF2C2C2E)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = applePassword,
                    onValueChange = { applePassword = it },
                    placeholder = { Text("Password", color = Color(0xFF8E8E93)) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF0A84FF),
                        unfocusedBorderColor = Color(0xFF3A3A3C),
                        focusedContainerColor = Color(0xFF2C2C2E),
                        unfocusedContainerColor = Color(0xFF2C2C2E)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", color = Color(0xFF0A84FF), fontWeight = FontWeight.Medium)
                    }

                    Button(
                        onClick = {
                            if (appleEmail.isNotBlank() && appleEmail.contains("@") && applePassword.length >= 6) {
                                onAccountSelected(appleEmail)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A84FF)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Continue", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}

@Composable
fun SocialPhoneAndReferralDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (phone: String, referralCode: String?) -> Unit
) {
    var phoneVal by remember { mutableStateOf("") }
    var refVal by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {},
        dismissButton = {},
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White,
        modifier = Modifier.padding(16.dp),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Verified/Security Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Verify Mobile",
                        tint = AccentGreen,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Verify Mobile Number",
                    color = PrimaryDarkGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Please link your 10-digit mobile number to complete registration & claim your 100 Rs registration bonus.",
                    color = Color(0xFF555555),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
                )

                // Mobile Number Field
                OutlinedTextField(
                    value = phoneVal,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } && input.length <= 10) {
                            phoneVal = input
                            phoneError = if (input.length == 10) "" else "Phone number must be exactly 10 digits"
                        }
                    },
                    label = { Text("Mobile Number", color = PrimaryDarkGreen) },
                    placeholder = { Text("Enter 10-digit number") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = PrimaryDarkGreen
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = phoneError.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentGreen,
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedLabelColor = AccentGreen,
                        errorBorderColor = Color.Red
                    )
                )

                if (phoneError.isNotEmpty()) {
                    Text(
                        text = phoneError,
                        color = Color.Red,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 4.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Referral Code Field (Optional)
                OutlinedTextField(
                    value = refVal,
                    onValueChange = { refVal = it },
                    label = { Text("Referral Code (Optional)", color = PrimaryDarkGreen) },
                    placeholder = { Text("e.g. SB1001") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = PrimaryDarkGreen
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentGreen,
                        unfocusedBorderColor = Color(0xFFCCCCCC),
                        focusedLabelColor = AccentGreen
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel", color = PrimaryDarkGreen, fontWeight = FontWeight.Medium)
                    }

                    Button(
                        onClick = {
                            if (phoneVal.length == 10) {
                                val refParam = if (refVal.isBlank()) null else refVal.trim()
                                onConfirm(phoneVal, refParam)
                            } else {
                                phoneError = "Phone number must be exactly 10 digits"
                            }
                        },
                        enabled = phoneVal.length == 10,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGreen,
                            disabledContainerColor = Color(0xFFCCCCCC)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}
