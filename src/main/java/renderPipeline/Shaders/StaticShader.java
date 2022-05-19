package renderPipeline.Shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class StaticShader extends ShaderProgram{

    private static final String vertexFile = "src/main/resources/shaders/default.vert";
    private static final String fragmentFile = "src/main/resources/shaders/default.frag";
    public StaticShader() {
        super(vertexFile, fragmentFile);
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1, "colors");
        super.bindAttribute(2, "texCoords");
    }


}
