import interpreter.StatementList
import io.constructICal
import io.readFile
import io.writeFile

class Scheduler {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            // Expects either 1 or 2 arguments
            // In the case of 1, simply reads then outputs to command line
            // In the case of 2... TODO: add an output case

            if (args.isEmpty()) {
                println("Expected at least 1 argument!")
                return
            }
            else if (args.size > 2) {
                println("Expected at most 2 arguments!")
                return
            }
            else {
                lateinit var input: String
                try {
                    input = readFile(args[0])

                } catch (e: Exception) {
                    println("Error in reading input file ${args[0]}:")
                    e.printStackTrace()
                    return
                }

                lateinit var statements: StatementList
                try {
                    statements = StatementList(input)
                } catch (e: Exception) {
                    println("Error in parsing input:")
                    e.printStackTrace()
                    return
                }

                lateinit var output: String
                try {
                    output = constructICal(statements)
                } catch(e: Exception) {
                    println("Error in constructing ICal output:")
                    e.printStackTrace()
                    return
                }

                if (args.size == 1) {
                    println(output)
                }
                if (args.size == 2) {
                    try {
                        writeFile(args[2], output)
                    } catch (e: Exception) {
                        println("Error in writing ICal data to file:")
                        e.printStackTrace()
                        return
                    }
                }

            }
        }
    }
}

