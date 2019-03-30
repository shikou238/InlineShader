package shikou238.lwjgl.render.shader.inline.unit

trait CanUseInGLSL{
  val `type`: String
}

abstract class Num

class GLSLFloat(value: Float) extends Num with CanUseInGLSL{
  val `type` = "float"
  def i : GLSLFloatIndentifier = ???
  def c : GLSLFloatIndentifier = {
    val v = this
    new GLSLFloatIndentifier(Const){
      override def value = v
    }
  }

  override def toString: String = value.toString
}
class GLSLSampler2D(value: Int) extends Num with CanUseInGLSL{
  val `type` = "sampler2D"
  def i : GLSLSampler2DIndentifier = ???
  def c : GLSLSampler2DIndentifier = ???
}

abstract class Vector
class GLSLVector2f extends Vector with CanUseInGLSL{
  val `type` = "vec2"
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
  val `type` = "vec3"
  def * (a: GLSLVector3f) : GLSLVector3f = ???
  def + (a: GLSLVector3f) : GLSLVector3f = ???
  def i : GLSLVector3fIndentifier = ???
  def c : GLSLVector3fIndentifier = {
    val v = this
    new GLSLVector3fIndentifier(Const) {
      override def value = v
    }
  }
}
object vec3{
  def apply(x: Float, y:Float, z: Float) : GLSLVector3f = new GLSLVector3f{
    override def toString: String = s"vec3($x, $y, $z)"
  } //TODO
  def apply(base: GLSLVector2f, z: Float): GLSLVector3f = ??? //TODO
  def apply(base: GLSLVector4f) : GLSLVector3f = new GLSLVector3f{
    override def toString: String = s"vec3($base)"
  }
}
class GLSLVector4f extends Vector with CanUseInGLSL{
  val `type` = "vec4"
  def * (a: GLSLVector4f) : GLSLVector4f = ???
  def + (a: GLSLVector4f) : GLSLVector4f = ???
  def i : GLSLVector4fIndentifier = ???
  def c : GLSLVector4fIndentifier = ???
}
object  vec4{
  def apply(x: Float, y:Float, z: Float, a: Float) : GLSLVector4f = ??? //TODO
  def apply(base: GLSLVector2f, z: Float, a: Float) : GLSLVector4f = ??? //TODO
  def apply(base: GLSLVector3f, a: Float) :GLSLVector4f = new GLSLVector4f{
    override def toString: String = s"vec4($base, $a)"
  } //TODO
}

abstract class Matrix
class GLSLMatrix2f extends Matrix with CanUseInGLSL{
  val `type` = "mat2"
  def * (a: GLSLMatrix2f) : GLSLMatrix2f = ???
  def * (a: GLSLVector2f) : GLSLVector2f = ???
  def + (a: GLSLMatrix2f) : GLSLMatrix2f = ???
  def i : GLSLMatrix2fIndentifier = ???
  def c : GLSLMatrix2fIndentifier = ???
}
class GLSLMatrix3f extends Matrix with CanUseInGLSL{
  val `type` = "mat3"
  def * (a: GLSLMatrix3f) : GLSLMatrix3f = ???
  def * (a: GLSLVector3f) : GLSLVector3f = ???
  def + (a: GLSLMatrix3f) : GLSLMatrix3f = ???
  def i : GLSLMatrix3fIndentifier = ???
  def c : GLSLMatrix3fIndentifier = ???
}
class GLSLMatrix4f extends Matrix with CanUseInGLSL{x =>
  val `type` = "mat4"
  def * (a: GLSLMatrix4f) : GLSLMatrix4f = new GLSLMatrix4f{
    override def toString: String = x + " * " + a
  }
  def * (a: GLSLVector4f) : GLSLVector4f = new GLSLVector4f{
    override def toString: String = x + " * " + a
  }
  def + (a: GLSLMatrix4f) : GLSLMatrix4f = ???
  def i : GLSLMatrix4fIndentifier = {
    val v = this
    new GLSLMatrix4fIndentifier(Nothing) {
      override def value = v
    }
  }
  def c : GLSLMatrix4fIndentifier = ???
}

class Void extends CanUseInGLSL{
  override val `type`: String = "void"
}

object GLSLFunction{
  def apply(name: String, values: CanUseInGLSL*) : GLSLVector3f= {
    new GLSLVector3f{
      override def toString: String = s"$name(${values.mkString(", ")})"
    }
  }//TODO
}
case class GLSLFunction(values: CanUseInGLSL){

}