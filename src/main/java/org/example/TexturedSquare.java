package org.example;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.file.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengles.GLES20.glGenerateMipmap;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class TexturedSquare {

    private long window;
    private int textureID;

    public void run() {
        init();
        loadTexture("src/main/java/org/example/textures.jpg"); // Завантаження текстури
        loop();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Textured Square", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loadTexture(String path) {
        ByteBuffer imageBuffer;
        try {
            imageBuffer = ioResourceToByteBuffer(path, 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer width = MemoryStack.stackMallocInt(1);
        IntBuffer height = MemoryStack.stackMallocInt(1);
        IntBuffer channels = MemoryStack.stackMallocInt(1);

        ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load texture file!" + System.lineSeparator() + STBImage.stbi_failure_reason());
        }

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);

        STBImage.stbi_image_free(image);
    }

    private void loop() {
        GL.createCapabilities();
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(0.2f, 0.3f, 0.3f, 0.0f);
            glBindTexture(GL_TEXTURE_2D, textureID);

            glBegin(GL_QUADS);  // Рендер квадрату
            glTexCoord2f(0.0f, 0.0f); glVertex2f(-0.5f, -0.5f);  // Нижня ліва вершина
            glTexCoord2f(1.0f, 0.0f); glVertex2f( 0.5f, -0.5f);  // Нижня права вершина
            glTexCoord2f(1.0f, 1.0f); glVertex2f( 0.5f,  0.5f);  // Верхня права вершина
            glTexCoord2f(0.0f, 1.0f); glVertex2f(-0.5f,  0.5f);  // Верхня ліва вершина
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;
        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = ByteBuffer.allocateDirect((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        } else {
            try (InputStream source = TexturedSquare.class.getClassLoader().getResourceAsStream(resource);
                 ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = ByteBuffer.allocateDirect(bufferSize);
                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) break;
                    if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                }
            }
        }
        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static void main(String[] args) {
        new TexturedSquare().run();
    }
}
