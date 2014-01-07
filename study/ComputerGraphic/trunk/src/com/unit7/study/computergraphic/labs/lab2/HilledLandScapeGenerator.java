package com.unit7.study.computergraphic.labs.lab2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class HilledLandScapeGenerator implements MapGenerator, Normalizer {
    public HilledLandScapeGenerator() {
        try {
            log = new PrintWriter(new FileWriter("/home/unit7/log.txt"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public float[][] generateMap(int n) {
        float[][] map = new float[n][n];
        regenerateMap(map);
        return map;
    }
    
    private int sqr(int a) {
        return a * a;
    }

    @Override
    public void regenerateMap(float[][] map) {
        int n = map.length;
        for (int i = 0; i < map.length; ++i) {
            Arrays.fill(map[i], 0f);
        }
        
        
        for (int i = 0; i < stepCount; ++i) {
            int radius = rand.nextInt(maxRadius);
            /*int radius = maxRadius;
            int x1 = map.length / 2; int y1 = map.length / 2;
            */int x1 = rand.nextInt(n + radius);
            int y1 = rand.nextInt(n + radius);
            int quadR = sqr(radius);
            for (int x = x1 - radius; x < x1 + radius; ++x) {
                for (int y = y1 - radius; y < y1 + radius; ++y) {
                    if (x >= 0 && x < n && y >= 0 && y < n) {
                        float h = quadR - (sqr(x - x1) + sqr(y - y1));
                        if (h > 0)
                            map[x][y] += h;Math.sqrt(h);
                        log.print(map[x][y] + "\t");
                    }
                }
                log.println();
            }
        }
        
//        normalize(map);
        valliyng(map);
    }
    
    @Override
    public void normalize(float[][] map) {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                min = Math.min(min, map[i][j]);
                max = Math.max(max, map[i][j]);
            }
        }
        
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                map[i][j] = (map[i][j] - min) / (max - min);
            }
        }
    }

    @Override
    public void valliyng(float[][] map) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                map[i][j] = (float) Math.sqrt(map[i][j]);
            }
        }
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
    }

    private int stepCount = 100;
    private int maxRadius = 128;
    private Random rand = new Random(System.currentTimeMillis());
    PrintWriter log = null;
}
