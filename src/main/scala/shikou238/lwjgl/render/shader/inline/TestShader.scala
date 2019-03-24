package shikou238.lwjgl.render.shader.inline

import shikou238.lwjgl.render.shader.inline.code.Version

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

  val light_power :GLSLFloatIndentifier = 1.0f.c

  val vertexMain = {
    val mw = world * model * side .i
    tex := texcoord
    pos := mw * vec4(position, 1.0f)
    nor := normalize(vec3(side * vec4(normal, 1.0f)))
    vertexColor := vec3(1.0f, 1.0f, 1.0f)
    gl_Position := projection * view * pos
  }

}
