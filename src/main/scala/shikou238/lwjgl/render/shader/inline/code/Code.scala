package shikou238.lwjgl.render.shader.inline.code

import shikou238.lwjgl.render.shader.inline.InLineShader
import shikou238.lwjgl.render.shader.inline.unit.{Article, CanUseInGLSL, Identifier}

sealed trait Code{
  InLineShader.add(this)
}

case class Version(version: Int) extends Code{
  override def toString: String = s"#version $version core"
}
case class Declalation(article: Article, `type`: String, var name: String, value: CanUseInGLSL = Default) extends Code{
  def vertex: String = s"${article.vertex} ${`type`} $name${if(value == Default) "" else " = " + value}"
  def fragment: String = s"${article.fragment} ${`type`} $name${if(value == Default) "" else " = " + value}"
}
case class Equal(target: Identifier, value: CanUseInGLSL) extends Code{
  override def toString: String = s"$target = $value"
}
case class Argument(article: Article, `type`: String, var name: String) extends Code {
  override def toString: String = s"${article.vertex} ${`type`} $name"
}

case object Default extends CanUseInGLSL{
  override val `type` = "todo"
}
