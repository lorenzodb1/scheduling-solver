import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class FooTest {

    val ourVal = 1

    // lateinit indicates a non-nullable type that is yet to be constructed
    lateinit var foo: Foo

    @BeforeAll
    internal fun beforeAll() {
        foo = Foo(ourVal)
    }

    @Test
    fun canConstructFoo() {
        assertEquals(ourVal, foo.i())
    }

    @Test
    fun testBar() {
        assertEquals(ourVal + 3, foo.bar(1, 2))
    }

    @Test
    fun testBaz() {
        val word = "wowie"
        assertEquals(
            "word is: $word and i is: $ourVal",
            foo.baz(word)
        )
    }
}