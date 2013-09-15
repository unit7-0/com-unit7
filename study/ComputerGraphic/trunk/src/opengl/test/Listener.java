package opengl.test;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;

public class Listener implements GLEventListener {
    public Listener() {
        hMatrix = generateMap(512);
    }
    
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        //gl.glLoadIdentity();
        
        gl.glPushMatrix();
        //draw        
        gl.glTranslatef(-.8f, -0.7f, -3.f);
        //gl.glRotatef(-60, 1, 0, 0);        
        
        for (int i = 0; i < hMatrix.length - 1; ++i) {
            for (int j = 0; j < hMatrix[0].length - 1; ++j) {
                gl.glBegin(GL2.GL_QUADS);
                
                float h = hMatrix[i][j] * ZOOM;
                float x = i * ZOOM_XY;
                float y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j], 0);
                gl.glVertex3f(x, y, h);
                
                h = hMatrix[i][j + 1] * ZOOM;
                y = (j + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i][j + 1], 0);
                gl.glVertex3f(x, y + ZOOM_XY, h);
                
                h = hMatrix[i + 1][j + 1] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y + ZOOM_XY, h);
                
                h = hMatrix[i + 1][j] * ZOOM;
                x = (i + 1) * ZOOM_XY;
                y = j * ZOOM_XY;
                gl.glColor3f(0, hMatrix[i + 1][j], 0);
                gl.glVertex3f(x + ZOOM_XY, y, h);
                
                gl.glEnd();
            }
            
        }
        
        gl.glPopMatrix();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width,
            int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        if (height <= 0)
            height = 1;
        
        final float h = (float) width / height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, h, 1.0, 1024);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    private float[][] generateMap(int n) {
        float[][] map = new float[n][n];
        
        map[0][0] = rand.nextFloat();
        map[0][n - 1] = rand.nextFloat();
        map[n - 1][0] = rand.nextFloat();
        map[n - 1][n - 1] = rand.nextFloat();
        
        doBuildMap(map, 0, 0, n - 1, n - 1);
        
        return map;
    }
    
    private void doBuildMap(float[][] map, int x1, int y1, int x2, int y2) {
        int xc = (x1 + x2) / 2;
        int yc = (y1 + y2) / 2;
        
        if (xc == x1 && yc == y1)
            return;
        
        boolean type = rand.nextBoolean();
        float vertexAdd = type ? UP : DOWN;
        float len = (x2 - x1) * vertexAdd;
        map[xc][y1] = ((map[x1][y1] + map[x2][y1]) / 2 + len) % MOD;
        float val = map[xc][y1];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (x2 - x1) * vertexAdd;
        map[xc][y2] = ((map[x1][y2] + map[x2][y2]) / 2 + len) % MOD;
        val = map[xc][y2];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (y2 - y1) * vertexAdd;
        map[x1][yc] = ((map[x1][y1] + map[x1][y2]) / 2 + len) % MOD;
        val = map[x1][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = (y2 - y1) * vertexAdd;
        map[x2][yc] = ((map[x2][y1] + map[x2][y2]) / 2 + len) % MOD;
        val = map[x2][yc];
        
        type = rand.nextBoolean();
        vertexAdd = type ? UP : DOWN;
        len = ((x2 - x1) + (y2 - y1)) * vertexAdd;
        map[xc][yc] = ((map[xc][y1] + map[xc][y2] + map[x1][yc] + map[x2][yc]) / 4 + len) % MOD;
        val = map[xc][yc];
        
        doBuildMap(map, x1, y1, xc, yc);
        doBuildMap(map, x1, yc, xc, y2);
        doBuildMap(map, xc, y1, x2, yc);
        doBuildMap(map, xc, yc, x2, y2);
    }
    
    private GLU glu = new GLU();
    private float[][] hMatrix;
    private float ZOOM = 1;
    private float ZOOM_XY = 0.0031f;
    private Random rand = new Random(System.currentTimeMillis());
    public static final float UP = 0.0004f;
    public static final float DOWN = 0.0002f;
    public static final float MOD = 1.1f;
}
