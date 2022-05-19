package main;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import renderPipeline.renderers.ModelRenderer;
import renderPipeline.renderers.TexturedModelRenderer;
import util.Input;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    public static long window;
    private static String title;
    public static int width;
    public static int height;
    private static boolean fullscreen;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized = false;

    Input input = new Input();
    ModelRenderer modelRenderer;

    public Window(String title, int width, int height){
    this.title = title;
    this.width = width;
    this.height = height;
    }

    public void run() {

        init();


    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");





        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            if ( key == GLFW_KEY_F11 && action == GLFW_RELEASE ) {
                 fullscreen = true;
                try ( MemoryStack stack = stackPush() ) {
                    IntBuffer pWidth = stack.mallocInt(1); // int*
                    IntBuffer pHeight = stack.mallocInt(1); // int*

                    // Get the window size passed to glfwCreateWindow
                    glfwGetWindowSize(window, pWidth, pHeight);

                    // Get the resolution of the primary monitor
                    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());


                    // Center the window
                    glfwSetWindowPos(
                            window,
                            (vidmode.width() - pWidth.get(0)) / 2,
                            (vidmode.height() - pHeight.get(0)) / 2
                    );

                    if(fullscreen){
                        glfwSetWindowMonitor(window,glfwGetPrimaryMonitor(),(vidmode.width() - pWidth.get(0)) / 2,
                                (vidmode.height() - pHeight.get(0)) / 2,width,height,0);
                    }

                } // the stack frame is popped automatically
            }

        });

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());



            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );

            if(fullscreen){
                glfwSetWindowMonitor(window,glfwGetPrimaryMonitor(),(vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2,width,height,0);
            }

        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Set the clear color
        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
        createCallbacks();
        modelRenderer = new TexturedModelRenderer();
        modelRenderer.bind();
    }


    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);

    }

    public void update(){


        glfwSwapBuffers(Window.window);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        modelRenderer.render();
        glfwPollEvents();

        if(Input.isKeyDown(GLFW_KEY_ESCAPE)){
            glfwSetWindowShouldClose(window, true);
        }
    }


    public void close() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(Window.window);
        glfwDestroyWindow(Window.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
