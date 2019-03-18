package shikou238.lwjgl.shader

import sourcecode.Name



class TestShader extends InLineShader{
  val version = Version(330)

  val position = in_vec3
  val texcoord = in_vec2
  val normal = in_vec3

  val vertexColor = inout_vec3
  val tex = inout_vec2
  val nor = inout_vec3
  val pos = inout_vec4

  val side = uniform_mat4
  val model = uniform_mat4
  val world = uniform_mat4
  val view = uniform_mat4
  val projection = uniform_mat4

  val fragColor = out_vec4

  val light = normalize(vec3(1.0f, 1.0f, 1.0f)).c

  val light_power = 1.0f.c

  val vertexMain = {
    val mw = world * model * side .i
    tex := texcoord
    pos := mw * vec4(position, 1.0f)
    nor := normalize(vec3(side * vec4(normal, 1.0f)))
    vertexColor := vec3(1.0f, 1.0f, 1.0f)
    gl_Position := projection * view * pos
  }


  implicit def scalaToGLSL(f: Float) = new GLSLFloat(f)
  implicit def scalaToGLSL(i: Int) = new GLSLInt(i)

}

class InLineShader{
  def normalize(a: vec3) : vec3 = ???

  val gl_Position = out_vec4

  case class Version(version: Int)

  type vec2 = GLSLVector2f
  type vec3 = GLSLVector3f
  type vec4 = GLSLVector4f
  type mat2 = GLSLMatrix2f
  type mat3 = GLSLMatrix3f
  type mat4 = GLSLMatrix4f

  type float = GLSLFloatIndentifier


  def in_vec2 = GLSLVector2fIndentifier(In)
  def in_vec3 = GLSLVector3fIndentifier(In)
  def in_vec4 = GLSLVector4fIndentifier(In)

  def inout_vec2 = GLSLVector2fIndentifier(InOut)
  def inout_vec3 = GLSLVector3fIndentifier(InOut)
  def inout_vec4 = GLSLVector4fIndentifier(InOut)

  def out_vec2 = GLSLVector2fIndentifier(Out)
  def out_vec3 = GLSLVector3fIndentifier(Out)
  def out_vec4 = GLSLVector4fIndentifier(Out)

  def const_vec2 = GLSLVector2fIndentifier(Const)
  def const_vec3 = GLSLVector3fIndentifier(Const)
  def const_vec4 = GLSLVector4fIndentifier(Const)


  def in_mat2 = GLSLMatrix2fIndentifier(In)
  def in_mat3 = GLSLMatrix3fIndentifier(In)
  def in_mat4 = GLSLMatrix4fIndentifier(In)

  def inout_mat2 = GLSLMatrix2fIndentifier(InOut)
  def inout_mat3 = GLSLMatrix3fIndentifier(InOut)
  def inout_mat4 = GLSLMatrix4fIndentifier(InOut)

  def out_mat2 = GLSLMatrix2fIndentifier(Out)
  def out_mat3 = GLSLMatrix3fIndentifier(Out)
  def out_mat4 = GLSLMatrix4fIndentifier(Out)

  def uniform_mat2 = GLSLMatrix2fIndentifier(Uniform)
  def uniform_mat3 = GLSLMatrix3fIndentifier(Uniform)
  def uniform_mat4 = GLSLMatrix4fIndentifier(Uniform)

  def const_mat2 = GLSLMatrix2fIndentifier(Const)
  def const_mat3 = GLSLMatrix3fIndentifier(Const)
  def const_mat4 = GLSLMatrix4fIndentifier(Const)


}

trait CanUseInGLSL

abstract class Num

class GLSLFloat(value: Float) extends Num with CanUseInGLSL{
  def i : GLSLFloatIndentifier = ???
  def c : GLSLFloatIndentifier = ???
}
class GLSLInt(value: Int) extends Num with CanUseInGLSL{
  def i : GLSLIntIndentifier = ???
  def c : GLSLIntIndentifier = ???
}

abstract class Vector
class GLSLVector2f extends Vector with CanUseInGLSL{
  def this(x: Float, y:Float) {this()} //TODO
  def this(base: GLSLVector3f) {this()} //TODO
  def this(base: GLSLVector4f) {this()} //TODO
  def * (a: GLSLVector2f) : GLSLVector2f = ???
  def + (a: GLSLVector2f) : GLSLVector2f = ???
  def i : GLSLVector2fIndentifier = ???
  def c : GLSLVector2fIndentifier = ???
}
object vec2{
  def apply(x: Float, y:Float): GLSLVector2f = ??? //TODO
  def apply(base: GLSLVector3f): GLSLVector2f  = ??? //TODO
  def apply(base: GLSLVector4f): GLSLVector2f = ??? //TODO
}
class GLSLVector3f extends Vector with CanUseInGLSL{
  def * (a: GLSLVector3f) : GLSLVector3f = ???
  def + (a: GLSLVector3f) : GLSLVector3f = ???
  def i : GLSLVector3fIndentifier = ???
  def c : GLSLVector3fIndentifier = ???
}
object vec3{
  def apply(x: Float, y:Float, z: Float) : GLSLVector3f = ??? //TODO
  def apply(base: GLSLVector2f, z: Float): GLSLVector3f = ??? //TODO
  def apply(base: GLSLVector4f) : GLSLVector3f = ??? //TODO
}
class GLSLVector4f extends Vector with CanUseInGLSL{
  def * (a: GLSLVector4f) : GLSLVector4f = ???
  def + (a: GLSLVector4f) : GLSLVector4f = ???
  def i : GLSLVector4fIndentifier = ???
  def c : GLSLVector4fIndentifier = ???
}
object  vec4{
  def apply(x: Float, y:Float, z: Float, a: Float) : GLSLVector4f = ??? //TODO
  def apply(base: GLSLVector2f, z: Float, a: Float) : GLSLVector4f = ??? //TODO
  def apply(base: GLSLVector3f, a: Float) :GLSLVector4f = ??? //TODO
}

abstract class Matrix
class GLSLMatrix2f extends Matrix with CanUseInGLSL{
  def * (a: GLSLMatrix2f) : GLSLMatrix2f = ???
  def * (a: GLSLVector2f) : GLSLVector2f = ???
  def + (a: GLSLMatrix2f) : GLSLMatrix2f = ???
  def i : GLSLMatrix2fIndentifier = ???
  def c : GLSLMatrix2fIndentifier = ???
}
class GLSLMatrix3f extends Matrix with CanUseInGLSL{
  def * (a: GLSLMatrix3f) : GLSLMatrix3f = ???
  def * (a: GLSLVector3f) : GLSLVector3f = ???
  def + (a: GLSLMatrix3f) : GLSLMatrix3f = ???
  def i : GLSLMatrix3fIndentifier = ???
  def c : GLSLMatrix3fIndentifier = ???
}
class GLSLMatrix4f extends Matrix with CanUseInGLSL{
  def * (a: GLSLMatrix4f) : GLSLMatrix4f = ???
  def * (a: GLSLVector4f) : GLSLVector4f = ???
  def + (a: GLSLMatrix4f) : GLSLMatrix4f = ???
  def i : GLSLMatrix4fIndentifier = ???
  def c : GLSLMatrix4fIndentifier = ???
}


case class GLSLFloatIndentifier(a: Article) extends GLSLFloat
case class GLSLIntIndentifier(a: Article) extends GLSLInt
case class GLSLVector2fIndentifier(a: Article) extends GLSLVector2f{
  def := (b: GLSLVector2f) = ???
}
case class GLSLVector3fIndentifier(a: Article) extends GLSLVector3f{
  def := (b: GLSLVector3f) = ???
}
case class GLSLVector4fIndentifier(a: Article) extends GLSLVector4f{
  def := (b: GLSLVector4f) = ???
}
case class GLSLMatrix2fIndentifier(a: Article) extends GLSLMatrix2f{
  def := (b: GLSLMatrix2f) = ???
}
case class GLSLMatrix3fIndentifier(a: Article) extends GLSLMatrix3f{
  def := (b: GLSLMatrix3f) = ???
}
case class GLSLMatrix4fIndentifier(a: Article) extends GLSLMatrix4f{
  def := (b: GLSLMatrix4f) = ???
}

sealed trait Article
case object In extends Article
case object InOut extends Article
case object Out extends Article
case object Uniform extends Article
case object Const extends Article
case object Nothing extends Article