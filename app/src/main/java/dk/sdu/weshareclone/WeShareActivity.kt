package dk.sdu.weshareclone

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
@ExperimentalMaterialApi
class WeShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeShareApp()
        }
    }
}