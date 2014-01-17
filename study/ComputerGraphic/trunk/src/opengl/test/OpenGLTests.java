/**
 * Date:	04 янв. 2014 г.
 * File:	OpenGLTests.java
 *
 * Author:	Zajcev V.
 */

package opengl.test;

import com.unit7.study.computergraphic.solarsystem.core.graphic.GLFrame;
import com.unit7.study.computergraphic.solarsystem.core.graphic.Renderer;

/**
 * @author unit7
 *
 */
public class OpenGLTests {
    public static void main(String[] args) {
        Renderer scene = new Renderer();
        GLFrame frame = new GLFrame(scene);
        frame.setVisible(true);
    }
}
