package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.LifecycleOwner
import com.gmail.luizjmfilho.sevenwonders.data.GetAppVersionUseCase
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getAppVersion: GetAppVersionUseCase,
    firebaseAnalytics: FirebaseAnalytics,
) : TrackedScreenViewModel(firebaseAnalytics, "About") {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState = _uiState.asStateFlow()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val appVersion = getAppVersion()

        _uiState.update { currentState ->
            currentState.copy(appVersion = appVersion)
        }
    }
}