import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SimpleTest {

    @Test
    fun firstTest() = runBlocking {
        doWork()
        Assert.assertEquals(2, 1 + 1)
    }
}