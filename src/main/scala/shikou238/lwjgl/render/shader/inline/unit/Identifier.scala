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

trait Identifier[T <: CanUseInGLSL] <: CanUseInGLSL{
  def article: Article  // why  dose not it work if this is "val"?
  def value: CanUseInGLSL = Default
  var name = RandomName.create

  writeCode()

  def writeCode():Unit = {
    if(value == Default)
      Declalation(article, `type`, name)
    else
      Declalation(article, `type`, name, value)
  }

  @deprecated
  def setName(name: String): Unit = {
    this.name = name
    InLineShader.changeLastName(name)
  }
  override def toString: String = name
}

case class GLSLFloatIndentifier(a: Article) extends GLSLFloat(0f) with Identifier[GLSLFloatIndentifier]{
  override def article = a
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLSampler2DIndentifier(a: Article) extends GLSLSampler2D(0) with Identifier{
  override def article = a
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLVector2fIndentifier(a: Article) extends GLSLVector2f with Identifier {
  override def article = a
  def := (b: GLSLVector2f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLVector3fIndentifier(a: Article) extends GLSLVector3f with Identifier{
  override def article = a
  def := (b: GLSLVector3f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLVector4fIndentifier(a: Article) extends GLSLVector4f with Identifier{
  override def article = a
  def := (b: GLSLVector4f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLMatrix2fIndentifier(a: Article) extends GLSLMatrix2f with Identifier{
  override def article = a
  def := (b: GLSLMatrix2f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLMatrix3fIndentifier(a: Article) extends GLSLMatrix3f with Identifier{
  override def article = a
  def := (b: GLSLMatrix3f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}
case class GLSLMatrix4fIndentifier(a: Article) extends GLSLMatrix4f with Identifier{
  override def article = a
  def := (b: GLSLMatrix4f) = Equal(this, b)
  def as(name: String) = {
    setName(name)
    this
  }
}1

sealed trait Article{
  val vertex: String
  val fragment: String = vertex
}
sealed trait ArgumentArticle
object  Article{
  val notAvailable = "not available"
}
case object In extends Article with ArgumentArticle{
  override val vertex = "in"
  override val fragment = Article.notAvailable
}
case object InOut extends Article with ArgumentArticle{
  override val vertex = "out"
  override val fragment = "in"
}
case object Out extends Article with ArgumentArticle{
  lazy override val vertex = Article.notAvailable
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
case object Reserved extends Article{
  override val vertex: String = Article.notAvailable
  override val fragment: String = Article.notAvailable
}

case object ConstIn extends Article{
  override val vertex: String = "const in"
}
