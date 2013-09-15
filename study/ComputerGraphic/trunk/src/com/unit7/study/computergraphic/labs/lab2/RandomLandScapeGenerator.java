package com.unit7.study.computergraphic.labs.lab2;

import java.util.Random;

public class RandomLandScapeGenerator implements MapGenerator {
    @Override
    public float[][] generateMap(int n) {
        float[][] hMatrix = new float[n][n];
        
        hMatrix[0][0] = rand.nextFloat();
        hMatrix[0][n - 1] = rand.nextFloat();
        hMatrix[n - 1][0] = rand.nextFloat();
        hMatrix[n - 1][n - 1] = rand.nextFloat();
        
        doBuildMap(hMatrix, 0, 0, n - 1, n - 1);
        
        return hMatrix;
    }

    private void doBuildMap(float[][] map, int x1, int y1, int x2, int y2) {
        int xc = (x1 + x2) / 2;
        int yc = (y1 + y2) / 2;
        
        if (xc == x1 && yc == y1)
            return;
        
        boolean type = rand.nextBoolean();
        float vertexAdd = type ? up : down;
        float len = (x2 - x1) * vertexAdd;
        map[xc][y1] = ((map[x1][y1] + map[x2][y1]) / 2 + len) % mod;
        float val = map[xc][y1];
        
        type = rand.nextBoolean();
        vertexAdd = type ? up : down;
        len = (x2 - x1) * vertexAdd;
        map[xc][y2] = ((map[x1][y2] + map[x2][y2]) / 2 + len) % mod;
        val = map[xc][y2];
        
        type = rand.nextBoolean();
        vertexAdd = type ? up : down;
        len = (y2 - y1) * vertexAdd;
        map[x1][yc] = ((map[x1][y1] + map[x1][y2]) / 2 + len) % mod;
        val = map[x1][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? up : down;
        len = (y2 - y1) * vertexAdd;
        map[x2][yc] = ((map[x2][y1] + map[x2][y2]) / 2 + len) % mod;
        val = map[x2][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? up : down;
        len = ((x2 - x1) + (y2 - y1)) * vertexAdd;
        map[xc][yc] = ((map[xc][y1] + map[xc][y2] + map[x1][yc] + map[x2][yc]) / 4 + len) % mod;
        val = map[xc][yc];
        
        doBuildMap(map, x1, y1, xc, yc);
        doBuildMap(map, x1, yc, xc, y2);
        doBuildMap(map, xc, y1, x2, yc);
        doBuildMap(map, xc, yc, x2, y2);
    }
    
    @Override
    public void regenerateMap(float[][] map) {
        map[0][0] = rand.nextFloat();
        map[0][map[0].length - 1] = rand.nextFloat();
        map[map.length - 1][0] = rand.nextFloat();
        map[map.length - 1][map[0].length - 1] = rand.nextFloat();
        
        doBuildMap(map, 0, 0, map.length - 1, map[0].length - 1);
    }
    
    public float getUp() {
        return up;
    }

    public void setUp(float up) {
        this.up = up;
    }

    public float getDown() {
        return down;
    }

    public void setDown(float down) {
        this.down = down;
    }

    public float getMod() {
        return mod;
    }

    public void setMod(float mod) {
        this.mod = mod;
    }


    private Random rand = new Random(System.currentTimeMillis());
    
    private float up = 0.0005f;
    private float down = 0.0005f;
    private float mod = 1.1f;
}
