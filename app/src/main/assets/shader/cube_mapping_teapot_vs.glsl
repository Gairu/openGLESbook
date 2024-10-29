#version 300 es

uniform mat4 worldMat, viewMat, projMat;
uniform vec3 eyePos;

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

out vec3 v_normal, v_view;

void main() {
    // world-space normal
    v_normal = normalize(transpose(inverse(mat3(worldMat))) * normal);

    // world-space position
    vec3 worldPos = (worldMat * vec4(position, 1.0)).xyz;

    // view vector
    v_view = normalize(eyePos - worldPos);

    // clip-space position
    gl_Position = projMat * viewMat * vec4(worldPos, 1.0);
}
