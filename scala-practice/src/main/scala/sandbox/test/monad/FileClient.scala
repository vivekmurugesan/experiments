package sandbox.test.monad

import java.nio.file.{Files, Path, Paths}

class FileClient(val targetDir: String) {

  def create(name: String) = {
    val path: Path = Paths.get(s"$targetDir/$name")
    Files.createDirectory(path)
  }

  def delete(name: String) = {
    val path: Path = Paths.get(s"$targetDir/$name")
    Files.deleteIfExists(path)
  }

  def list() ={
    val path: Path = Paths.get(targetDir)
    val iter = Files.list(path).map(x => x.getFileName).iterator()
    iter
  }

}
