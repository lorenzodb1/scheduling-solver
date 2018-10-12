import io.readFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ReadFileTest {


    val fileString =
"""Hello world
This is a

test


FILE 123"""

    @Test
    fun testFileRead() {
        assertEquals(fileString, readFile("test.txt"))
    }
}