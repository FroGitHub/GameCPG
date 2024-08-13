package org.example;

import static org.lwjgl.opengl.GL11.*;

public class Square {


    private float squareX; // Початкова позиція квадрата по X
    private float squareY; // Початкова позиція квадрата по Y
    private float squareSpeed; // Швидкість руху квадрата

    public Square(float squareX, float squareY, float squareSpeed) {
        this.squareX = squareX;
        this.squareY = squareY;
        this.squareSpeed = squareSpeed;
    }


    public void moveX(float leftOrRight){
        this.squareX += this.squareSpeed * leftOrRight;
    }

    public void moveY(float upOrDown){
        this.squareY += this.squareSpeed * upOrDown;
    }

    public void renderSquare() {
        // Встановлення кольору квадрата (червоний)
        glColor3f(1.0f, 0.0f, 0.0f);

        // Рендеринг квадрата з урахуванням його позиції по осі X
        glBegin(GL_QUADS);
        glVertex2f(squareX - 0.1f,  squareY - 0.1f); // Верхній лівий кут
        glVertex2f(squareX + 0.1f,  squareY - 0.1f); // Верхній правий кут
        glVertex2f(squareX + 0.1f, squareY - -0.1f); // Нижній правий кут
        glVertex2f(squareX - 0.1f, squareY - -0.1f); // Нижній лівий кут
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

    public float getSquareSpeed() {
        return squareSpeed;
    }

    public void setSquareSpeed(float squareSpeed) {
        this.squareSpeed = squareSpeed;
    }
}
