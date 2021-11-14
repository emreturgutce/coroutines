package com.emreturgutce

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class SimpleTest {

    @Test
    fun firstTest() = runBlocking {
        doWork()

        assertEquals(2, 1 + 1)
    }
}