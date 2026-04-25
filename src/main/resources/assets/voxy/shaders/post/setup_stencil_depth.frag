#version 330 core

layout(binding = 0) uniform sampler2D depthTex;
layout(location = 1) uniform vec2 scaleFactor;

in vec2 UV;
void main() {
    gl_FragDepth = 0.0f;
    if (texture(depthTex, UV*scaleFactor).r==1.0f) {
        discard;
    }
}