package renderPipeline.mesh;

import org.joml.Vector2f;
import renderPipeline.texture.Texture;
import util.BufferCreator;

import java.util.ArrayList;


import static org.lwjgl.opengl.GL15C.*;

public class TexturedModel extends Model{
    private Texture texture;
    private int tc_id;//Tex coord id
    private ArrayList<Vector2f> texture_coordinates = new ArrayList<Vector2f>();

    public TexturedModel(){
      super();
      texture = Texture.loadImage("src/main/resources/textures/test.png");
      texture_coordinates.add(new Vector2f(0f,0f));
      texture_coordinates.add(new Vector2f(0f,1f));
      texture_coordinates.add(new Vector2f(1f,1f));
      texture_coordinates.add(new Vector2f(1f,0f));
      texture_coordinates.add(new Vector2f(0f,1f));
      texture_coordinates.add(new Vector2f(1f,1f));
    }
    public TexturedModel(String file){
        super();
        texture = Texture.loadImage("src/main/resources/textures/" + file);
        texture_coordinates.add(new Vector2f(0f,0f));
        texture_coordinates.add(new Vector2f(0f,1f));
        texture_coordinates.add(new Vector2f(1f,1f));
        texture_coordinates.add(new Vector2f(1f,0f));
        texture_coordinates.add(new Vector2f(0f,1f));
        texture_coordinates.add(new Vector2f(1f,1f));
    }

    public void bind(){
        super.bind();
        texture.bind(0);
        tc_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tc_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createBuffer(texture_coordinates, new Vector2f()), GL_STATIC_DRAW);


    }


    /**
     * loads texture from file
     * @param texturePath - Texture path
     */
    public void setTexture(String texturePath){
        texture = Texture.loadImage(texturePath);
    }


    public int tc_id(){
        return tc_id;
    }

    public int textureID(){
        return texture.getImageID();
    }

}

