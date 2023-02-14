# This repository contains good running examples of scala cats and cats-effects

### Semigroups:
- A type class which combines values of two similar types.
- x |+| y, where x and y are of same type T.
- |+| is associative, i.e., x |+| y = y |+| x
- Drawback: cannot have general APIs with starting values like foldLeft and foldRight.

### Monoids:
- A monoid is a semigroup with identity operation, i.e., empty method.
- we can have general APIs with generic starting values.

### Functors:
- A Functor is a type class which provided map function.

### Monads:

### Applicatives: