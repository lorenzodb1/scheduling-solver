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
sealed class OptionalNode<T: Node> {
    data class Some<T: Node>(val node: T) : OptionalNode<T>()
    data class Id<T: Node>(val id: IdNode) : OptionalNode<T>()
}