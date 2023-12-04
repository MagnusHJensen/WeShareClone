package dk.sdu.weshareclone.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class WeShareViewModel() : ViewModel() {
    fun launchCatching(onError: (String) -> Unit = {}, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                throwable.message?.let { Log.e("APP", it)
                    onError(it)
                }
            },
            block = block
        )
}