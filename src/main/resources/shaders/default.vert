#version 330 core

uniform mat4 projection;
uniform mat4 view;
uniform mat4 modelView;

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 colors;
layout(location = 2) in vec2 texCoords;
out vec4 color;
out vec2 texCoord;

void main(void){
    gl_Position = view * vec4(position,1.0);
	color = vec4(1.0,0,0,1.0);
	texCoord = texCoords;
}

