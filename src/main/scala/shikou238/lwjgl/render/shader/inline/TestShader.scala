package shikou238.lwjgl.render.shader.inline

import shikou238.lwjgl.render.shader.inline.code.{Argument, Version}
import shikou238.lwjgl.render.shader.inline.unit._

class TestShader extends InLineShader{

  val version = Version(330)

  val position = in_vec3 as "position"
  val texcoord = in_vec2 as "texcoord"
  val normal = in_vec3 as "normal"

  val vertexColor = inout_vec3 as "vertexColor"
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

  val light_power :GLSLFloatIndentifier = 1.0f.c
//
//  val camera = uniform_vec3
//
//  val texImage = uniform_sampler2D
//
//  def safedot()

//  def main(a: vec3) : vec3 = GLSLFunction("main", a) ;> {
//    /@ void "main"
//
//  }
  def dot(a: vec3, b: vec3): float = GLSLFunction("dot", a, b)
  def clamp(a: float, b: vec3): float = GLSLFunction("dot", a, b)
  val safedot = GLSLFunction[float, (ConstInArg[vec3], ConstInArg[vec3])]("safedot") { (a, b) =>
    normalize
  }
  def safedot(a: vec3, b: vec3): float = GLSLFunction("safedot", a, b);>{
    /@ float "safedot"
      val a = argument_vec3(ConstIn)
      val b = argument_vec3(ConstIn)

    // TODO

  }
  override def vertexMain(): Unit = {
    val mw = world * model * side .i// todo check
    tex := texcoord
    pos := mw * vec4(position, 1.0f)
    nor := normalize(vec3(side * vec4(normal, 1.0f)))
    vertexColor := vec3(1.0f, 1.0f, 1.0f)
    glPosition := projection * view * pos
  }

  override def fragmentMain(): Unit = {

  }

  defineMain

}
