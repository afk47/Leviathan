package renderPipeline.mesh;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import util.BufferCreator;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 * untextured 3D Model (any number of tris & vertices )
 */
public class Model {

    protected Vector3f position, rotation;

    protected boolean bound = false;

    protected int v_id;//Vertex id
    protected int c_id;//Colors id
    protected int i_id;//indices id
    protected int n_id;//normals id
    protected int ni_id;//Normal indices id
    protected int draw_count;

     ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
     ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
     ArrayList<Vector4f> colors = new ArrayList<Vector4f>();

     int[] indices = new int[]{
            0, 1, 2,
            0, 1, 3,
    };
     int[] normalIndices;
     int[] textureIndices;
     Matrix4f modelView;

    public Model() {

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_ALPHA);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        rotation = new Vector3f();
        position = new Vector3f();
        modelView = new Matrix4f().identity();

        vertices.add(new Vector3f(0.5f, 0.5f, -1f));
        vertices.add(new Vector3f(0.5f, -0.5f, -1f));
        vertices.add(new Vector3f(-0.5f, -0.5f, -1f));
        vertices.add(new Vector3f(-0.5f, 0.5f, -1f));
        vertices.add(new Vector3f(0.5f, 0.5f, 0f));
        vertices.add(new Vector3f(0.5f, -0.5f, 0f));
        vertices.add(new Vector3f(-0.5f, -0.5f, 0f));
        vertices.add(new Vector3f(-0.5f, 0.5f, 0f));

        colors.add(new Vector4f());
        colors.add(new Vector4f());
        colors.add(new Vector4f());
        colors.add(new Vector4f());

        indices = new int[]{
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,

        };
    }

    public Model(ArrayList<Vector3f> vertices, ArrayList<Vector4f> colors, int[] indices) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        this.vertices = vertices;
        this.colors = colors;
        this.indices = indices;
        rotation = new Vector3f();
        position = new Vector3f();
        modelView = new Matrix4f().identity();
    }

    public void bind() {
        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(vertices, new Vector3f()), GL_STATIC_DRAW);

        c_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, c_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(colors, new Vector4f()), GL_STATIC_DRAW);

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
        bound = true;
    }

    //Updates modelView to match position and rotation;
    public void update(){
        modelView.translate(position).rotateXYZ(rotation);
    }

    public int drawCount() {
        return draw_count;
    }

    public int i_id() {
        return i_id;
    }

    public int c_id() {
        return c_id;
    }

    public int v_id() {
        return v_id;
    }

    public Matrix4f getModelView() {
        return modelView;
    }

    public boolean isBound(){
        return bound;
    }

    public void setPosition(Vector3f position){
        this.position = position;
    }

    public void setRotation(Vector3f rotation){
        this.rotation = rotation;
    }
}
