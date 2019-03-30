package shikou238.lwjgl.render.shader.inline

import shikou238.lwjgl.render.shader.inline.code._
import shikou238.lwjgl.render.shader.inline.unit._

import scala.collection.mutable.ListBuffer
import scala.util.Random


abstract class InLineShader{


  def normalize(a: vec3) : vec3 = GLSLFunction("normalize", a)


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

  def argument_vec3(a: Article) = new GLSLVector3fIndentifier(ConstIn){
    override def writeCode(): Unit = {
      Argument(a, `type`, name)
    }
  }
  def argument_vec4(a: Article) = new GLSLVector4fIndentifier(ConstIn){
    override def writeCode(): Unit = {
      Argument(a, `type`, name)
    }
  }

  implicit def scalaToGLSL(f: Float): GLSLFloat = new GLSLFloat(f)
  implicit def scalaToGLSL(i: Int): GLSLSampler2D = new GLSLSampler2D(i)

  def >(name: => Unit) = {
    InLineShader.nowRecording.get.startRecord
    name
    InLineShader.nowRecording.get.endRecord
  }
  val buffer = new ShaderSourceBuffer

  class A
  lazy val /@ = new A{
    def a = InLineShader.nowRecording.get.nowRecording.get

    def void(name: String) = {
      a.`type` = "void"
      a.name = name
    }
  }


  def vertexMain(): Unit
  def fragmentMain(): Unit

  InLineShader.startRecord(buffer)

  val glPosition = new GLSLVector4fIndentifier(Reserved) as "gl_Position"


  def defineMain() {
    > {
      /@ void "vmain"
      vertexMain()
    }
    > {
      /@ void "fmain"
      fragmentMain()
    }
  }
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
  private[this]var lastDec : Declalation = null//todo must pay attention
  def add(code: Code): Unit ={
    nowRecording match {
      case Some(b) =>
        code match {
          case dec: Declalation =>
            lastDec = dec
          case _ =>
        }
        b add code
    }
  }
  def changeLastName(name: String): Unit ={
    lastDec.name = name
  }
}

class ShaderSourceBuffer {
  var version : Version = _
  val declacation = ListBuffer.empty[Declalation]


  var nowRecording : Option[FunctionBuffer] = None
  var functions = ListBuffer.empty[FunctionBuffer]

//  var vertexMain : FunctionBuffer
//  var fragmentMain : FunctionBuffer

  class FunctionBuffer{
    val undefined = "undefined"
    var name = undefined
    var `type` = undefined
    val arguments = ListBuffer.empty[Argument]
    val codes = ListBuffer.empty[String]
    def add(code: Code): Unit = {

      code match {
        case ar: Argument =>
          arguments += ar
        case dec: Declalation =>
          this.codes += dec.vertex
        case e: Equal =>
          codes += e.toString
      }
    }
    def vertex: String =
      name match {
        case "vmain" =>
          s"""
             |void main(${arguments.mkString(", ")}) {
             |  ${codes.mkString(System.lineSeparator() + "  ")}
             |}
       """.stripMargin
        case "fmain" => ""
        case _ => toString
      }
    def fragment: String =
      name match {
        case "fmain" =>
          s"""
             |void main(${arguments.mkString(", ")}) {
             |  ${codes.mkString(System.lineSeparator() + "  ")}
             |}
       """.stripMargin
        case "vmain" => ""
        case _ => toString
      }

    override def toString: String =
      s"""
         |${`type`} $name(${arguments.mkString(", ")}) {
         |  ${codes.mkString(System.lineSeparator() + "  ")}
         |}
       """.stripMargin
  }

  def vertexShaderBuffer: String ={
    s"""
       |$version
       |${declacation.filter(_.article.vertex != Article.notAvailable).map(_.vertex).mkString(System.lineSeparator())}
       |${functions.map(_.vertex).mkString}
     """.stripMargin
  }
  def fragmentShaderBuffer: String ={
    s"""
       |$version
       |${declacation.filter(_.article.fragment != Article.notAvailable).map(_.fragment).mkString(System.lineSeparator())}
       |${functions.map(_.fragment).mkString}
     """.stripMargin
  }
  val served = List("gl_Position")
  def add(code :Code): Unit ={
    nowRecording match {
      case Some(f) =>
        f add code
      case None =>
        code match {
          case version: Version => this.version = version
          case dec: Declalation =>
            this.declacation += dec
        }
    }
  }
  def startRecord: Unit ={
    require(nowRecording == None)
    nowRecording = Some(new FunctionBuffer)
  }
  def endRecord: Unit = {
    require(nowRecording != None)
    functions += nowRecording .get
    nowRecording = None
  }
}

