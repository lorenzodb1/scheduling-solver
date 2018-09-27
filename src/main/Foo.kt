
// included ONLY as an example for tests

// the 'val' keyword in the constructor args indicates a class field
class Foo(private val i: Int) {

    fun i() : Int {
        return i
    }

    fun bar(j: Int, k: Int) : Int {
        return i + j + k // semicolons are optional
    }

    fun baz(word: String) : String {
        return "word is: $word and i is: $i"
    }
}