package renderPipeline.mesh;


import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderPipeline.Shaders.ShaderProgram;
import renderPipeline.Shaders.StaticShader;
import main.Window;
import renderPipeline.objects.Camera;
import util.BufferCreator;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL20.*;


public class Quad {
    private Vector3f position, rotation;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;
    private static final float FOV = (float) Math.toRadians(60f);
    private static final float Z_NEAR = 0.001f;
    private static final float Z_FAR = 1000f;
    public int v_id;//Vertex id
    public int c_id;//Colors id
    public int t_id;//Tex coord id
    public int i_id;//indices id
    public int n_id;//normals id
    public int ni_id;//Normal indices id
    public int draw_count;
    ShaderProgram shader = new StaticShader();
    float x = 0;
    float width = Window.width;
    float height = Window.height;
    float aspect = width / height;
    private Matrix4f projectionMatrix = new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);
    private Camera camera;
    private ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    private ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    private ArrayList<Vector4f> colors = new ArrayList<Vector4f>();
    private ArrayList<Vector2f> texture_coordinates = new ArrayList<Vector2f>();
    private int[] indices = new int[]{
            0, 1, 2,
            0, 1, 3,
    };
    private int[] normalIndices;
    private int[] textureIndices;
    private Matrix4f modelView = new Matrix4f().identity();

    public Quad() {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);

        vertices.add(new Vector3f(-0.5f, 0.5f, -1f));
        vertices.add(new Vector3f(0.5f, 0.5f, -1f));
        vertices.add(new Vector3f(-0.5f, -0.5f, -1f));
        vertices.add(new Vector3f(0.5f, -0.5f, -1f));

        indices = new int[]{
                0, 1, 2,
                1, 2, 3,
        };

        rotation = new Vector3f();
    }


    public void bind() {
        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(vertices, new Vector3f()), GL_STATIC_DRAW);

        c_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, c_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(colors, new Vector4f()), GL_STATIC_DRAW);

        t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(texture_coordinates, new Vector2f()), GL_STATIC_DRAW);

//		n_id = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, n_id);
//		glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);


        i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferCreator.createBuffer(indices), GL_STATIC_DRAW);

//		ni_id = glGenBuffers();
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ni_id);
//		glBufferData(GL_ELEMENT_ARRAY_BUFFER, normalIndexBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        draw_count = indices.length;
        shader.start();
        shader.setUniform("projection", projectionMatrix);
        camera = new Camera(new Vector3f(),new Vector3f());
    }

    public void render() {
        camera.update();
        glfwSwapBuffers(Window.window); // swap the color buffers
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glLoadIdentity();


        shader.setUniform("projection", projectionMatrix);
        shader.setUniform("modelView", modelView);
        shader.setUniform("view",camera.getView());
        //GIVES OPENGL THE VBO INFO SO IT KNOWS HOW TO DRAW TRIS FOR OBJECT

        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, c_id);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        //DRAWS ARRAYS
        glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);

        //glDrawArrays(GL_TRIANGLES, 0,draw_count);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        //shader.stop();

    }

}
