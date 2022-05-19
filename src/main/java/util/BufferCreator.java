package util;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class BufferCreator {


    public static FloatBuffer createBuffer(ArrayList<Vector2f> data, Vector2f type){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 3);
        for (Vector2f vect : data) {
            buffer.put(vect.x);
            buffer.put(vect.y);
        }
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createBuffer(ArrayList<Vector3f> data, Vector3f type){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 3);
        for (Vector3f vect : data) {
            buffer.put(vect.x);
            buffer.put(vect.y);
            buffer.put(vect.z);
        }
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createBuffer(ArrayList<Vector4f> data,Vector4f type){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 4);
        for (Vector4f vect : data) {
            buffer.put(vect.x);
            buffer.put(vect.y);
            buffer.put(vect.z);
            buffer.put(vect.w);
        }
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }




}
