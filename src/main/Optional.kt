/**
 * Utility class to represent nodes that may be place-held by Id's
 *
 * TO USE:
 *
 * when (optionalNode) {
 *     is Some -> { optionalNode.node.doNodeStuff() }
 *     is Id   -> { optionalNode.node.doIdNodeStuff() }
 * }
 *
 */
sealed class Optional<T> {
    data class Some<T>(val node: T) : Optional<T>()
    data class Id<T>(val id: IdNode) : Optional<T>()
}