package pet.project.tastetips.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.tastetips.model.TasteTipsViewModel
import pet.project.tastetips.ui.theme.TasteTipsTheme

@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: TasteTipsViewModel) {
    Column(modifier = Modifier.padding(top = 40.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
        ) {
            Image(imageVector = Icons.Filled.Person,
                contentDescription = "User photo",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(128.dp)
                )
            Text("Guest", fontSize = 32.sp)
        }
        Text(text = "Settings",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 16.dp)
        )
        Card(
            modifier = Modifier.padding(start = 40.dp,
                top = 36.dp, bottom = 36.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(text = "Notifications", fontSize = 28.sp)
                    Spacer(Modifier.width(12.dp))
                    Switch(checked = false, onCheckedChange = {})
                }
                Row {
                    Text("Dark theme", fontSize = 28.sp)
                    Spacer(Modifier.width(28.dp))
                    Switch(checked = false, onCheckedChange = {})
                }

            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Logout,
                contentDescription = null,
                tint = Color(0xF7BB1B1B),
                modifier = Modifier.size(32.dp)
            )
            Text("Log out",
                fontSize = 24.sp,
                color = Color(0xF7BB1B1B)
            )
        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    TasteTipsTheme {
        SettingsScreen(rememberNavController(), viewModel())
    }
}