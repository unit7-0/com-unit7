/**
 * Date:	08 янв. 2014 г.
 * File:	Camera.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.computergraphic.solarsystem.engine;

import org.apache.log4j.Logger;

/**
 * @author unit7
 * 
 */
public class Camera {
    private Camera() {
        updateCamera();
    }

    public static Camera getInstance() {
        return instance;
    }

    public void updateCamera() {
        // Stub
        if (log.isDebugEnabled()) {
            log.debug(String.format(
                    "\r\neye (%.2f, %.2f, %.2f)\r\ncenter (%.2f, %.2f, %.2f)\r\nup (%.2f, %.2f, %.2f)\r\n"
                            + "rotate (%.2f, %.2f, %.2f) angle [ %.2f ]", eyeX, eyeY, eyeZ, centerX, centerY, centerZ,
                    upX, upY, upZ, rotateX, rotateY, rotateZ, rotatingAngle));
        }
    }

    public double getEyeX() {
        return eyeX;
    }

    public void setEyeX(double eyeX) {
        this.eyeX = eyeX;
        updateCamera();
    }

    public double getEyeY() {
        return eyeY;
    }

    public void setEyeY(double eyeY) {
        this.eyeY = eyeY;
        updateCamera();
    }

    public double getEyeZ() {
        return eyeZ;
    }

    public void setEyeZ(double eyeZ) {
        this.eyeZ = eyeZ;
        updateCamera();
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
        updateCamera();
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
        updateCamera();
    }

    public double getCenterZ() {
        return centerZ;
    }

    public void setCenterZ(double centerZ) {
        this.centerZ = centerZ;
        updateCamera();
    }

    public double getUpX() {
        return upX;
    }

    public void setUpX(double upX) {
        this.upX = upX;
        updateCamera();
    }

    public double getUpY() {
        return upY;
    }

    public void setUpY(double upY) {
        this.upY = upY;
        updateCamera();
    }

    public double getUpZ() {
        return upZ;
    }

    public void setUpZ(double upZ) {
        this.upZ = upZ;
        updateCamera();
    }

    public void addEyeY(double v) {
        eyeY += v;
        updateCamera();
    }

    public void subEyeY(double v) {
        eyeY -= v;
        updateCamera();
    }

    public void addEyeZ(double v) {
        eyeZ += v;
        updateCamera();
    }

    public void subEyeZ(double v) {
        eyeZ -= v;
        updateCamera();
    }

    public void addCenterX(double v) {
        centerX += v;
        updateCamera();
    }

    public void subCenterX(double v) {
        centerX -= v;
        updateCamera();
    }

    public void addCenterY(double v) {
        centerY += v;
        updateCamera();
    }

    public void subCenterY(double v) {
        centerY -= v;
        updateCamera();
    }

    public void addCenterZ(double v) {
        centerZ += v;
        updateCamera();
    }

    public void subCenterZ(double v) {
        centerZ -= v;
        updateCamera();
    }

    public void addUpX(double v) {
        upX += v;
        updateCamera();
    }

    public void subUpX(double v) {
        upX -= v;
        updateCamera();
    }

    public void addUpY(double v) {
        upY += v;
        updateCamera();
    }

    public void subUpY(double v) {
        upY -= v;
        updateCamera();
    }

    public void addUpZ(double v) {
        upZ += v;
        updateCamera();
    }

    public void subUpZ(double v) {
        upZ -= v;
        updateCamera();
    }

    public void addEyeX(double v) {
        eyeX += v;
        updateCamera();
    }

    public void subEyeX(double v) {
        eyeX -= v;
        updateCamera();
    }

    public double getRotateX() {
        return rotateX;
    }

    public void setRotateX(double rotateX) {
        this.rotateX = rotateX;
        updateCamera();
    }

    public double getRotateY() {
        return rotateY;
    }

    public void setRotateY(double rotateY) {
        this.rotateY = rotateY;
        updateCamera();
    }

    public double getRotateZ() {
        return rotateZ;
    }

    public void setRotateZ(double rotateZ) {
        this.rotateZ = rotateZ;
        updateCamera();
    }

    public void resetRotating() {
        rotateX = 0;
        rotateY = 0;
        rotateZ = 0;
        rotatingAngle = 0;
    }

    public double normalizeXRotating() {
        double max = Math.max(rotateX, Math.max(rotateY, rotateZ));
        return rotateX / max;
    }

    public double normalizeYRotating() {
        double max = Math.max(rotateX, Math.max(rotateY, rotateZ));
        return rotateY / max;
    }

    public double normalizeZRotating() {
        double max = Math.max(rotateX, Math.max(rotateY, rotateZ));
        return rotateZ / max;
    }

    public double getRotatingAngle() {
        return rotatingAngle;
    }

    public void setRotatingAngle(double rotatingAngle) {
        this.rotatingAngle = rotatingAngle;
    }

    public void resetCamera() {
        eyeX = eyeY = eyeZ = 0;
        centerX = centerY = centerZ = 0;
        upX = upZ = 0;
        upY = 1;
        resetRotating();
    }
    
    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    private double eyeX, eyeY, eyeZ = 90;
    private double centerX, centerY, centerZ;
    private double upX, upY = 1, upZ;
    private double rotateX, rotateY, rotateZ;
    private double rotatingAngle;
    private double ratio; // коэффициент преобразования размера для объектов

    private static final Logger log = Logger.getLogger(Camera.class);

    private static final Camera instance = new Camera();
}
