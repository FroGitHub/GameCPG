package org.example;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;


interface Object {
    public void renderObject();
}
interface Movable{
    public void moveX(float leftOrRight);
    public void moveY(float upOrDown);
}

public class Person implements Object, Movable {

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

    @Override
    public void moveX(float leftOrRight){
        this.personX += this.speed * leftOrRight;
    }

    @Override
    public void moveY(float upOrDown){
        this.personY += this.speed * upOrDown;
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


    public void setSquareSpeed(float squareSpeed) {
        this.speed = squareSpeed;
    }


    public float getPersonX() {
        return personX;
    }

    public void setPersonX(float personX) {
        this.personX = personX;
    }

    public float getPersonY() {
        return personY;
    }

    public void setPersonY(float personY) {
        this.personY = personY;
    }

    public float getSquareSpeed() {
        return speed;
    }

    public float[] getSize() {
        return size;
    }
}
