package sandbox.test

object OptionUsage extends App{

  def parseInt(x: String): Option[Int] =
    scala.util.Try(x.toInt).toOption

  def tryDiv(a: Int, b: Int): Option[Int] =
    if(b==0) None else Some(a/b)

  println(parseInt("123"))
  println(parseInt("123!!"))

  println(tryDiv(1,2))
  println((tryDiv(4,2)))
  println(tryDiv(3,0))

  def stringsDevBy(aStr: String, bStr: String): Option[Int] =
    parseInt(aStr).flatMap { aNum =>
      parseInt(bStr).flatMap { bNum =>
        tryDiv(aNum, bNum)
      }
    }

  println(stringsDevBy("100", "50"))
  println(stringsDevBy("100", "a"))
  println(stringsDevBy("100", "0"))

  def stringsDevBy1(aStr: String, bStr: String): Option[Int] =
    for{
      aNum <- parseInt(aStr)
      bNum <- parseInt(bStr)
      result <- tryDiv(aNum, bNum)
    }yield result

  println(stringsDevBy1("100", "50"))
  println(stringsDevBy1("100", "a"))
  println(stringsDevBy1("100", "0"))
}
