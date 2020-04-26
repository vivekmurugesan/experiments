

// Memoized
val x = {
  println("Computing X")
  math.random
}

x
x

// Lazy and not memoized.
def y = {
  println("Coputing Y")
  math.random
}

y
y

// Lazy values are initialized and then memoized
lazy val z = {
  println("Computing Z")
  math.random
}

z
z

import cats.Eval

// Similar to val egar and memoized
val now = Eval.now(math.random * 1000)

// Similar to lazy val, eval on first time and memoize
val later = Eval.later(math.random * 2000)

// Similar to def, eval on all the reference.
val always = Eval.always(math.random * 3000)

now.value
later.value
always.value

now.value
later.value
always.value


