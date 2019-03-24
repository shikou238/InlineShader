package shikou238.lwjgl.render.shader.inline.unit

import shikou238.lwjgl.render.shader.inline.InLineShader
import shikou238.lwjgl.render.shader.inline.code._

import scala.collection.mutable.ListBuffer
import scala.util.Random

object RandomName{
  val alreadyExist = ListBuffer.empty[String]
  def create: String = {
    val name = "i" + Random.alphanumeric.take(9).mkString
    if(!alreadyExist.contains(name))
      name
    else
      create
  }
}

trait Identifier <: CanUseInGLSL{
  val article: Article
  val name = RandomName.create
  InLineShader.add(Declalation(article, `type`, name))
}

case class GLSLFloatIndentifier(a: Article) extends GLSLFloat with Identifier{
  override val article = a
}
case class GLSLIntIndentifier(a: Article) extends GLSLInt with Identifier{
  override val article = a
}
case class GLSLVector2fIndentifier(a: Article) extends GLSLVector2f with Identifier {
  override val article = a
  def := (b: GLSLVector2f) = ???
}
case class GLSLVector3fIndentifier(a: Article) extends GLSLVector3f with Identifier{
  override val article = a
  def := (b: GLSLVector3f) = ???
}
case class GLSLVector4fIndentifier(a: Article) extends GLSLVector4f with Identifier{
  override val article = a
  def := (b: GLSLVector4f) = ???
}
case class GLSLMatrix2fIndentifier(a: Article) extends GLSLMatrix2f with Identifier{
  override val article = a
  def := (b: GLSLMatrix2f) = ???
}
case class GLSLMatrix3fIndentifier(a: Article) extends GLSLMatrix3f with Identifier{
  override val article = a
  def := (b: GLSLMatrix3f) = ???
}
case class GLSLMatrix4fIndentifier(a: Article) extends GLSLMatrix4f with Identifier{
  override val article = a
  def := (b: GLSLMatrix4f) = ???
}

sealed trait Article{
  val vertex: String
  val fragment: String = vertex
}
case object In extends Article{
  override val vertex = "in"
  override val fragment = ???
}
case object InOut extends Article{
  override val vertex = "out"
  override val fragment = "in"
}
case object Out extends Article{
  override val vertex = ???
  override val fragment = "out"
}
case object Uniform extends Article{
  override val vertex = "uniform"
  override val fragment = "uniform"
}
case object Const extends Article{
  override val vertex = "const"
  override val fragment = "const"
}
case object Nothing extends Article{
  override val vertex = ""
  override val fragment = ""
}
