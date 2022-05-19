package renderPipeline.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import main.Window;
import util.Input;

public class Camera {
    private Matrix4f cameraView = new Matrix4f().identity();
    private Vector3f position, rotation;
    private float moveSpeed = 0.05f, mouseSensitivity = 0.005f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    private static final float FOV = (float) Math.toRadians(60f);
    private static final float Z_NEAR = 0.001f;
    private static final float Z_FAR = 1000f;
    private static float aspect = Window.width / Window.height;
    private static final float TAU = 6.28318530718f;
    private static final float clamp = (3*TAU)/4;
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Camera() {
        this(new Vector3f(),new Vector3f());
    }

    public void update() {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();


        //rotation is already in radians
        float x = (float) Math.sin(rotation.y) * moveSpeed;
        float z = (float) Math.cos(rotation.y) * moveSpeed;



        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
            moveSpeed = 0.1f;
        }else{
            moveSpeed = 0.05f;
        }


         if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position.add( new Vector3f(moveSpeed, 0, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position.add( new Vector3f(-moveSpeed, 0, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position.add( new Vector3f(0, 0, moveSpeed));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position.add( new Vector3f(0, 0,-moveSpeed));


        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position.add( new Vector3f(0, moveSpeed, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) position.add( new Vector3f(0, -moveSpeed, 0));

        float dx = (float) (newMouseX - oldMouseX) * mouseSensitivity;
        float dy = (float) (newMouseY - oldMouseY) * mouseSensitivity;


        if(!(rotation.x + dy > TAU/4 || rotation.x + dy < -clamp))
        rotation.add(new Vector3f(dy, dx , 0));


        // rotation.set(rotation.x % TAU,rotation.y % TAU,rotation.z % TAU);


        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Matrix4f getView(){
        Matrix4f target = projection();
        cameraView.identity().rotateXYZ(rotation).translate(position);
        target.mul(cameraView);

        return target;
    }

    public static Matrix4f projection(){

        return new Matrix4f().perspective(FOV, aspect, Z_NEAR, Z_FAR);

    }
}