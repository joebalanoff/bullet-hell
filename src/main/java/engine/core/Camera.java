package engine.core;

import engine.utils.Vector2;

import java.util.Random;

public class Camera {
    private final Window window;

    private Vector2 position;
    private Vector2 follow;
    private float zoom = 0.5f;
    private float currentZoom = 1f;

    private Vector2 minPosition;
    private Vector2 maxPosition;
    private Vector2 offset;

    // Shake
    private float shakeIntensity = 0.0f;
    private double shakeDuration = 0.0;
    private double shakeTimer = 0.0;
    private final Random random = new Random();

    public Camera(Window window) {
        this.window = window;
        this.position = new Vector2();
        this.offset = new Vector2();
    }

    public void onUpdate(double delta) {
        float zoomSpeed = 0.1f;
        currentZoom += (zoom - currentZoom) * zoomSpeed;

        Vector2 shakeOffset = null;
        if(shakeTimer > 0) {
            shakeOffset = new Vector2();

            shakeTimer -= delta;
            shakeOffset.x = (float) ((random.nextDouble() * 2 - 1) * shakeIntensity);
            shakeOffset.y = (float) ((random.nextDouble() * 2 - 1) * shakeIntensity);

            if(shakeTimer <= 0) {
                shakeTimer = 0;
                shakeIntensity = 0;
            }
        }

        if(follow != null) {
            Vector2 targetPosition = new Vector2(
                    follow.x - (double) getViewportWidth() / 2,
                    follow.y - (double) getViewportHeight() / 2
            );
            if(minPosition != null && maxPosition != null) {
                targetPosition.x = clamp(targetPosition.x, minPosition.x, maxPosition.x - getViewportWidth());
                targetPosition.y = clamp(targetPosition.y, minPosition.y, maxPosition.y - getViewportHeight());

                targetPosition.x -= getViewportWidth() / 2 - ((maxPosition.x - minPosition.x) / 2);
                targetPosition.y -= getViewportHeight() / 2 - ((maxPosition.y - minPosition.y) / 2);
            }

            float lerpSpeed = 0.2f;
            position.x += (targetPosition.x - position.x) * lerpSpeed;
            position.y += (targetPosition.y - position.y) * lerpSpeed;
        }

        if(shakeOffset != null) {
            position.x += shakeOffset.x;
            position.y += shakeOffset.y;
        }
    }

    public void shake(float intensity, double duration) {
        this.shakeIntensity = intensity;
        this.shakeDuration = duration;
        this.shakeTimer = duration;
    }

    public void setFollow(Vector2 follow) {
        this.follow = follow;
    }

    public void setZoom(float zoom) { this.zoom = zoom; }

    public void setOffset(Vector2 offset) { this.offset = offset; }

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
        return (int) (window.getWidth() / currentZoom);
    }

    public int getViewportHeight() {
        return (int) (window.getHeight() / currentZoom);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public float getZoom() {
        return currentZoom;
    }
}
