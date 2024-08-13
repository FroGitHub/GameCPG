package org.example;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameWindow {

    // Ідентифікатор вікна
    private long window;

    public void run() {
        System.out.println("LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Звільнення ресурсів і завершення роботи
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Налаштування GLFW, ініціалізація
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Неможливо ініціалізувати GLFW");
        }

        // Налаштування параметрів вікна
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Створення вікна
        window = glfwCreateWindow(800, 600, "My Game Window", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Неможливо створити вікно");
        }

        // Центрування вікна
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Створення OpenGL контексту
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        // Зробити вікно видимим
        glfwShowWindow(window);
    }

    private void loop() {
        // Ініціалізація OpenGL
        GL.createCapabilities();

        // Головний цикл гри
        while (!glfwWindowShouldClose(window)) {
            // Очистка екрану
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Зміна кольору фону (червоний, зелений, синій, прозорість)
            glClearColor(0.2f, 0.3f, 0.3f, 0.0f);

            // Заміна кадру
            glfwSwapBuffers(window);

            // Обробка подій
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new GameWindow().run();
    }
}

