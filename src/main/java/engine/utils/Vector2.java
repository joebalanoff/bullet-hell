package engine.utils;

public class Vector2 {
    public double x, y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitude() {
        return Math.abs(Math.sqrt(x*x + y*y));
    }

    public void Normalize() {
        double mag = getMagnitude();
        if(mag == 0) return;
        this.x /= mag;
        this.y /= mag;
    }

    public Vector2 normalized() {
        double mag = getMagnitude();
        if(mag == 0) return new Vector2();
        Vector2 v = new Vector2();
        v.x = this.x / mag;
        v.y = this.y / mag;
        return v;
    }

    public void Add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public Vector2 multiply(double s) {
        return new Vector2(this.x * s, this.y * s);
    }

    @Override
    public String toString() {
        return x + " : " + y;
    }
}
