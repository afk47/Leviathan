package main;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static boolean running = false;
    public static Window window;
    private static String versionCode = "0.0";
    public static void main(String[] args){
        init();
    }

    /**
     * Sets Up Window
     */
    public static void init(){

        window = new Window("Leviathan Engine " + versionCode, 1280, 720);
        window.run();

        running = true;
        run();

    }

    /**
     * Game Main Loop
     */
    public static void run(){

        while(!glfwWindowShouldClose(Window.window)){
            window.update();


        }
        window.close();

    }


}
