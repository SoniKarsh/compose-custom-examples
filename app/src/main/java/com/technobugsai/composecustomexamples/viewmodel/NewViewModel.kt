package com.technobugsai.composecustomexamples.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technobugsai.composecustomexamples.uistate.PageUiState
import com.technobugsai.composecustomexamples.utils.TestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewViewModel: ViewModel() {

    val listOfCartoons = TestData.getCartoonList()
    private var _currentPage = 0
    private var _nextPage = 0

    // Page UI state
    private val _uiState = MutableStateFlow(PageUiState())
    private val _uiStateAnimation = MutableStateFlow(PageUiState())
    val uiState: StateFlow<PageUiState> = _uiState.asStateFlow()
    val uiStateAnimation: StateFlow<PageUiState> = _uiStateAnimation.asStateFlow()

    private val _isAnimationInProgress = MutableStateFlow(false)
    var inProgress = false
    val isAnimationInProgress = _isAnimationInProgress.asStateFlow()
//    val isAnimationInProgress: StateFlow<Boolean> get() = _isAnimationInProgress

    init {
        val pageUiState = PageUiState(
            bgColor = listOfCartoons[0].color
        )
        _uiState.value = pageUiState
    }

    fun getCurrentPage() = listOfCartoons[_nextPage]

    fun updateState(index: Int) {
        if (_currentPage != index) {
            Log.e("Called", index.toString())
            _currentPage = index
            _uiState.value = PageUiState(
                bgColor = listOfCartoons[index].color
            )
        }
    }

    fun updateStateForAnimation(current: Int, settled: Int, target: Int, offset: Float) {
//        Log.e("Offset",
//            "current: ${current}" +
//                    "\nsettled: ${settled}" +
//                    "\ntarget: ${target}" +
//                    "\noffset: ${offset}")
        /*
            if offset value is negative & target == current
            then target must be or going to be the prev one
         */
        if (offset > 0 && !inProgress) {
            // start next position animation
            _nextPage = current + 1
            inProgress = true
            viewModelScope.launch(Dispatchers.IO) {
                _isAnimationInProgress.value = true
                Log.e("called", "Callss1")
                delay(TestData.ANIMATION_DURATION)
                Log.e("called", "Callss2")
                inProgress = false
                _isAnimationInProgress.value = false
            }
        } else if (offset < 0 && !inProgress) {
            // start prev position animation
            _nextPage = current - 1
            inProgress = true
            _isAnimationInProgress.value = inProgress
            viewModelScope.launch(Dispatchers.IO) {
                Log.e("called", "Callss1")
                delay(TestData.ANIMATION_DURATION)
                Log.e("called", "Callss2")
                inProgress = false
                _isAnimationInProgress.value = inProgress
            }
        } else {
            // offset == 0
//            _isAnimationInProgress.value = false
        }
        /*
            if offset value is positive & target == current
            then target must be or going to be the next one
         */
    }

}