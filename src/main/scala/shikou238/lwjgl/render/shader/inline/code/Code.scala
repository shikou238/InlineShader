package shikou238.lwjgl.render.shader.inline.code

import shikou238.lwjgl.render.shader.inline.unit.{Article, CanUseInGLSL}

sealed trait Code

case class Version(version: Int) extends Code{
  override def toString: String = s"#version $version core"
}
case class Declalation(article: Article, `type`: String, name: String) extends Code{
  def vertex: String = s"${article.vertex} $`type` $name"
  def fragment: String = s"${article.fragment} $`type` $name"
}
case class DeclalationAndInitialize(_article: Article, _type: String, _name: String, value: CanUseInGLSL) extends Declalation (article, `type` ,name){
  override def vertex: String = s"${article.vertex} $`type` $name = $value"
  override def fragment: String = s"${article.fragment} $`type` $name = $value"
}
case class Function(name: String, values: CanUseInGLSL*) extends Code{
  override def toString: String = s"$name(${values.mkString(", ")})"
}
