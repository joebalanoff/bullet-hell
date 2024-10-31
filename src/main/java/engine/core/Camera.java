package engine.core;

import engine.utils.Vector2;

public class Camera {
    private final Window window;

    private Vector2 position;
    private Vector2 follow;
    private float zoom = 1.0f;

    private Vector2 minPosition;
    private Vector2 maxPosition;

    public Camera(Window window) {
        this.window = window;
        this.position = new Vector2();
    }

    public void onUpdate(double delta) {
        if(follow != null) {
            Vector2 targetPosition = new Vector2(
                    follow.x - (double) getViewportWidth() / 2,
                    follow.y - (double) getViewportHeight() / 2
            );
            if(minPosition != null && maxPosition != null) {
                targetPosition.x = clamp(targetPosition.x, minPosition.x, maxPosition.x);
                targetPosition.y = clamp(targetPosition.y, minPosition.y, maxPosition.y);
            }

            float lerpSpeed = 0.3f;
            position.x += (targetPosition.x - position.x) * lerpSpeed;
            position.y += (targetPosition.y - position.y) * lerpSpeed;
        }
    }

    public void setFollow(Vector2 follow) {
        this.follow = follow;
    }

    public void setMinPosition(Vector2 minPosition) {
        this.minPosition = minPosition;
    }

    public void setMaxPosition(Vector2 maxPosition) {
        this.maxPosition = maxPosition;
    }

    public int getX() {
        return (int) position.x;
    }

    public int getY() {
        return (int) position.y;
    }

    public int getViewportWidth() {
        return (int) (window.getWidth() / zoom);
    }

    public int getViewportHeight() {
        return (int) (window.getHeight() / zoom);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public float getZoom() {
        return zoom;
    }
}
