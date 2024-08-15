package org.example;

interface Object {
    public float getX();
    public float getY();
    public float[] getSize();
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


interface Collision{
    public boolean checkTouch(Object[] object);
}