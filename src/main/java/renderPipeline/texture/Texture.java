package renderPipeline.texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL30C.*;

public class Texture {

    private FloatBuffer image;
    private int width;
    private int height;
    private int id;

    public Texture(int width, int height, FloatBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D,id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,this.width,this.height,0,GL_RGBA,GL_FLOAT,image);
        glGenerateMipmap(GL_TEXTURE_2D);

        //STBImage.stbi_image_free(image);
    }

    public static Texture loadImage(String path) {
        FloatBuffer image;
        int width;
        int height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = STBImage.stbi_loadf(path, w, h, comp, 4);
            if (image == null) {
                System.err.println("Couldn't load " + path);
            }
            width = w.get();
            height = h.get();

        }
        return new Texture(width, height, image);
    }

    public void bind(){

    }
    public void bind(int sampler){
        if(sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public FloatBuffer getImage() {
        return image;
    }

    public int getImageID() {
        return id;
    }
}

