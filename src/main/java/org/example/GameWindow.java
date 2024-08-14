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

public class GameWindow {

    private long window;

    private Person person = new Person(
            0.0f,
            0.0f,
            0.02f, new float[]{0.1f, 0.1f});

    private Square[] squares = new Square[]{
            new Square(
                    -0.5f,
                    -0.5f, new float[]{0.1f, 0.1f}),
            new Square(
                    1.0f,
                    1.0f, new float[]{0.1f, 0.1f})
    };

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
        // Ініціалізація GLFW
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
            // Очистка екрану перед рендерингом нового кадру
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Зміна кольору фону
            glClearColor(0.2f, 0.3f, 0.3f, 0.0f);

            // Обробка вводу з клавіатури
            processInput();


            renderGrid(0.1f, 1.0f, 1.0f);

            // Рендеринг квадрата
            person.renderObject();
            for(Square square : squares) {
                square.renderObject();
            }
            // Заміна кадру
            glfwSwapBuffers(window);

            // Обробка подій
            glfwPollEvents();
        }
    }

    private void processInput() {
        // Перевірка натискання клавіші ВЛІВО
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            person.moveX(-1);
        }

        // Перевірка натискання клавіші ВПРАВО
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            person.moveX(1);
        }

        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            person.moveY(-1);
        }

        // Перевірка натискання клавіші ВГОРУ
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            person.moveY(1);
        }

    }

    private void renderGrid(float gridSize, float width, float height) {
        glColor3f(0.5f, 0.5f, 0.5f); // Колір сітки (сірий)
        glBegin(GL_LINES);

        // Вертикальні лінії
        for (float x = -width; x <= width; x += gridSize) {
            glVertex2f(x, -height);
            glVertex2f(x, height);
        }

        // Горизонтальні лінії
        for (float y = -height; y <= height; y += gridSize) {
            glVertex2f(-width, y);
            glVertex2f(width, y);
        }

        glEnd();
    }


//    private boolean checkCollision() {
//        float[] personPos = {person.getPersonX(), person.getPersonY()};
//        float[] personSize = person.getSize();
//
//
//        float[] squarePos = {square.getSquareX(), square.getSquareY()};
//        float[] squareSize = square.getSize();
//
//        return (personPos[0] + personSize[0] > squarePos[0] - squareSize[0] &&
//                personPos[0] - personSize[0] < squarePos[0] + squareSize[0] &&
//                personPos[1] + personSize[1] > squarePos[1] - squareSize[1] &&
//                personPos[1] - personSize[1] < squarePos[1] + squareSize[1]);
//
//    }



    public static void main(String[] args) {
        new GameWindow().run();
    }
}
