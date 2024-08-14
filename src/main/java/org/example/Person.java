package org.example;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;


public class Person implements Object, Movable, Camera {

    private float[] size = new float[2];
    private float personX; // Початкова позиція квадрата по X
    private float personY; // Початкова позиція квадрата по Y
    private float speed; // Швидкість руху квадрата


    public Person(float personX, float personY, float speed, float[] size) {
        this.size = size;
        this.personX = personX;
        this.personY = personY;
        this.speed = speed;

    }

    private void setPersonX(float personX) {
        this.personX = personX;
    }

    private void setPersonY(float personY) {
        this.personY = personY;
    }

    @Override
    public void moveX(float leftOrRight){
        setPersonX(personX + speed * leftOrRight);
        moveCameraX(leftOrRight);
    }

    @Override
    public void moveY(float upOrDown){
        setPersonY(personY + speed * upOrDown);
        moveCameraY(upOrDown);
    }

    @Override
    public void renderObject() {
        // Встановлення кольору квадрата (червоний)
        glColor3f(1.0f, 0.0f, 0.0f);

        // Рендеринг квадрата з урахуванням його позиції по осі X
        glBegin(GL_QUADS);
        glVertex2f(personX - size[0],  personY - size[1]); // Верхній лівий кут
        glVertex2f(personX + size[0],  personY - size[1]); // Верхній правий кут
        glVertex2f(personX + size[0], personY - -size[1]); // Нижній правий кут
        glVertex2f(personX - size[0], personY - -size[1]); // Нижній лівий кут
        glEnd();
    }

    @Override
    public float getX() {
        return personX;
    }

    @Override
    public float getY() {
        return personY;
    }

    @Override
    public void moveCameraX(float leftOrRight) {
        glTranslatef(speed * -leftOrRight, 0.0f, 0.0f);
    }

    @Override
    public void moveCameraY(float upOrDown) {
        glTranslatef(0.0f, speed * -upOrDown, 0.0f);
    }
}
