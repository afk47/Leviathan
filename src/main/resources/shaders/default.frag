#version 330 core

out vec4 FragColor;
in vec4 color;

in vec2 texCoord;

uniform sampler2D tex;

void main(void){

    //if (colors.w < .00001){
         //FragColor = vec4(1.0,0,0,1.0);
    //} else {
        FragColor = texture(tex, texCoord);
    //}
}