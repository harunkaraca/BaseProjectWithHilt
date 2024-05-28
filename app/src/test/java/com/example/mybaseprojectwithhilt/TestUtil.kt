package com.example.mybaseprojectwithhilt

import androidx.lifecycle.LiveData
import com.example.mybaseprojectwithhilt.util.Event
import org.junit.Assert
import org.junit.Assert.assertEquals

fun assertLiveDataEventTriggered(
    liveData: LiveData<Event<String>>,
    taskId: String
) {
    val value = liveData.getOrAwaitValue()
    Assert.assertEquals(value.getContentIfNotHandled(), taskId)
}

fun assertSnackbarMessage(snackbarLiveData: LiveData<Event<Int>>, messageId: Int) {
    val value: Event<Int> = snackbarLiveData.getOrAwaitValue()
    Assert.assertEquals(value.getContentIfNotHandled(), messageId)
}