package org.example;

import static org.lwjgl.opengl.GL11.*;

public class Square implements Object{


    private float squareX;
    private float squareY;

    private float[] size = new float[2];


    public Square(float squareX, float squareY, float[] size) {
        this.size = size;
        this.squareX = squareX;
        this.squareY = squareY;
    }

    @Override
    public void renderObject() {

        glColor3f(4.0f, 1.0f, 2.0f);

        // Рендеринг квадрата з урахуванням його позиції по осі X
        glBegin(GL_QUADS);
        glVertex2f(squareX - size[0],  squareY - size[1]); // Верхній лівий кут
        glVertex2f(squareX + size[0],  squareY - size[1]); // Верхній правий кут
        glVertex2f(squareX + size[0], squareY - -size[1]); // Нижній правий кут
        glVertex2f(squareX - size[0], squareY - -size[1]); // Нижній лівий кут
        glEnd();
    }

    public float getSquareX() {
        return squareX;
    }

    public void setSquareX(float squareX) {
        this.squareX = squareX;
    }

    public float getSquareY() {
        return squareY;
    }

    public void setSquareY(float squareY) {
        this.squareY = squareY;
    }

    public float[] getSize() {
        return size;
    }

    public void setSize(float[] size) {
        this.size = size;
    }
}
