package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.PrimaryDarkGreen

@Composable
fun BottomNavBar(
    selectedRoute: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("team", "Team", Icons.Default.Group),
        BottomNavItem("blog", "Blog", Icons.Default.Campaign),
        BottomNavItem("mine", "Mine", Icons.Default.Person)
    )

    val activeIndex = items.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)

    BoxWithConstraints(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .navigationBarsPadding() // Support safe drawing in notch/bottom bar
            .fillMaxWidth()
            .height(72.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(36.dp),
                clip = false,
                ambientColor = Color(0x3D1B4332),
                spotColor = Color(0x3D1B4332)
            )
            .border(
                BorderStroke(
                    width = 1.2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0x70FFFFFF), // Specular white liquid glass highlight
                            Color(0x3D52B788), // Starbucks accent mint soft edge
                            Color(0x1F000000)  // Deep shadow grounding
                        )
                    )
                ),
                shape = RoundedCornerShape(36.dp)
            )
            .clip(RoundedCornerShape(36.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xD91E3932), // Liquid green base 1 (highly transparent)
                        Color(0xE6006241)  // Liquid green base 2 (highly transparent)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val containerWidth = maxWidth
        val tabWidth = containerWidth / items.size

        // Independent asymmetric left and right edge spring animations to create a satisfying liquid stretching 'gel' effect
        val leftPos by animateDpAsState(
            targetValue = tabWidth * activeIndex,
            animationSpec = spring(
                dampingRatio = 0.72f,
                stiffness = Spring.StiffnessMediumLow
            ),
            label = "liquidLeft"
        )

        val rightPos by animateDpAsState(
            targetValue = tabWidth * (activeIndex + 1),
            animationSpec = spring(
                dampingRatio = 0.64f,
                stiffness = Spring.StiffnessLow
            ),
            label = "liquidRight"
        )

        val indicatorWidth = (rightPos - leftPos).coerceAtLeast(0.dp)

        // 3D Liquid-Glow sliding indicator behind the items
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = leftPos)
                .width(indicatorWidth)
                .fillMaxHeight()
                .padding(vertical = 8.dp, horizontal = 6.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x75FFFFFF), // High reflective highlight
                            Color(0x1F008248), // Liquid green glow
                            Color.Transparent
                        )
                    )
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x40FFFFFF), // Specular light
                            Color(0x14FFFFFF)
                        )
                    )
                )
                .border(
                    BorderStroke(1.dp, Color(0x3DFFFFFF)),
                    shape = RoundedCornerShape(28.dp)
                )
        )

        // Tab Items
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == activeIndex
                
                // Animate scale and color of selected items
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.15f else 1.0f,
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessMedium),
                    label = "tabScale"
                )

                val contentAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 1.0f else 0.55f,
                    animationSpec = tween(200),
                    label = "tabAlpha"
                )

                val accentColor = if (isSelected) AccentGreen else Color.White

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Prevent default square ripples to keep glass aesthetic clean
                        ) {
                            onTabSelected(item.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = accentColor.copy(alpha = contentAlpha),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(3.dp))
                    
                    Text(
                        text = item.label,
                        color = Color.White.copy(alpha = contentAlpha),
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

