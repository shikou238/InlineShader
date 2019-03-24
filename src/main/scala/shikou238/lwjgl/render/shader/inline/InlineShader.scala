package shikou238.lwjgl.render.shader.inline

import shikou238.lwjgl.render.shader.inline.code.{Code, Declalation, Version}

import scala.collection.mutable.ListBuffer
import scala.util.Random


class InLineShader{


  def normalize(a: vec3) : vec3 = InLineShader.add(Function("normalize", a))

  val gl_Position = out_vec4

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



  implicit def scalaToGLSL(f: Float): GLSLFloat = new GLSLFloat(f)
  implicit def scalaToGLSL(i: Int): GLSLInt = new GLSLInt(i)


  val buffer = new ShaderSourceBuffer

}
object InLineShader{
  var nowRecording: Option[ShaderSourceBuffer] = None
  def startRecord(buffer: ShaderSourceBuffer): Unit = {
    nowRecording = Some(buffer)
  }
  def endRecord(buffer: ShaderSourceBuffer) = {
    nowRecording match {
      case Some(`buffer`) =>
        //todo ?
      case _ =>
        throw new IllegalStateException("This is not appropriate buffer. No or other buffer started recording.")
    }
  }
  def add(code: Code): Unit ={
    code match {
      case version: Version => nowRecording.version = version
    }
  }
}

abstract class ShaderSource

class VertexShaderSource
class FragmentShaderSource

class ShaderSourceBuffer{
  var version = Version
  var declacation = ListBuffer.empty[Declalation]
}

