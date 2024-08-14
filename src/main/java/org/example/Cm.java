package org.example;

interface Object {
    public float getX();
    public float getY();
    public void renderObject();


}
interface Movable{
    public void moveX(float leftOrRight);
    public void moveY(float upOrDown);
}

interface Camera{
    public void moveCameraX(float leftOrRight);
    public void moveCameraY(float upOrDown);
}
