package opengl.test;

import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;


public class Test extends JFrame {
    public static void main(String[] args) {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        final Animator animator = new Animator(canvas);        
        
        final Test frame = new Test();
        canvas.addGLEventListener(frame.getListener());
        frame.add(canvas);
        frame.setSize(512, 512);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                animator.stop();
                frame.dispose();
                super.windowClosing(arg0);
            }
        });
        
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
    
    private GLEventListener getListener() {
        return new Listener();
    }
}
