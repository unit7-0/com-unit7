/**
 * Date:	22 февр. 2014 г.
 * File:	ImageMap.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.pathfinder.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.unit7.pathfinder.graphs.Edge;
import com.unit7.pathfinder.graphs.ListGraph;
import com.unit7.pathfinder.graphs.Node;
import com.unit7.pathfinder.graphs.Pair;
import com.unit7.pathfinder.gui.ChangeConnections;
import com.unit7.pathfinder.gui.CreateConnection;
import com.unit7.pathfinder.gui.ImageMapPanel;
import com.unit7.pathfinder.gui.ShowConnections;
import com.unit7.pathfinder.gui.ShowPath;
import com.unit7.pathfinder.tools.Utils;

/**
 * @author unit7 Объект картинка-карта. Является также обработчиком для кнопок и
 *         для нажатий мыши. В зависимости от состояния выбирает поведение,
 *         делегирует выполнение объекту state.
 */
public class ImageMap extends MouseAdapter implements State, Serializable {
    private static final long serialVersionUID = -6654681313826803880L;
    
    public ImageMap() {
    }

    public ImageMap(BufferedImage image) {
        this.image = image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        clear();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setState(State state) {
        this.state = state;
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#addPlace(com.unit7.pathfinder.gui.ImageMap
     * )
     */
    @Override
    public void addPlace(ImageMap map) {
        state.addPlace(this);
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#addConnection(com.unit7.pathfinder.gui
     * .ImageMap)
     */
    @Override
    public void addConnection(ImageMap map) {
        state.addConnection(this);
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#changeConnection(com.unit7.pathfinder.
     * gui.ImageMap)
     */
    @Override
    public void changeConnection(ImageMap map) {
        state.changeConnection(this);
    }

    /*
     * @see
     * com.unit7.pathfinder.gui.State#findPath(com.unit7.pathfinder.gui.ImageMap
     * )
     */
    @Override
    public void findPath(ImageMap map) {
        state.findPath(this);
    }

    @Override
    public void showConnections(ImageMap map) {
        state.showConnections(this);
    }

    @Override
    public void afterClick(ImageMap map) {
        state.afterClick(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (image == null)
            return;
        
        try {
            if (e.getButton() == MouseEvent.BUTTON3) {
                firstPoint = null;
                secondPoint = null;
                return;
            }

            int x = e.getX();
            int y = e.getY();
            coords.setFirst(x);
            coords.setSecond(y);
            changed = true;
            afterClick(null);
        } finally {
            if (panel != null)
                panel.repaint();
        }
    }

    public Node findPoint(Pair<Integer, Integer> p) {
        int x = p.getFirst();
        int y = p.getSecond();
        for (Iterator<Pair<Integer, Integer>> it = points.keySet().iterator(); it.hasNext();) {
            Pair<Integer, Integer> p1 = it.next();
            if (Utils.isIntersect(p, p1, POINT_RADIUS))
                return points.get(p1);
        }

        return null;
    }

    public void addPoint() {
        Node node = findPoint(coords);
        System.out.println("addpoint: " + node);
        if (node == null) {
            String name = JOptionPane.showInputDialog("Enter the name of the place");
            node = new Node(name);
            points.put(new Pair<Integer, Integer>(coords.getFirst(), coords.getSecond()), node);
            graph.add(node);
            changed = true;
        }
    }

    public void selectPoint() {
        Node node = findPoint(coords);
        System.out.println("selectpoint: " + node);
        if (node != null) {
            Pair<Integer, Integer> point = new Pair<Integer, Integer>(coords.getFirst(), coords.getSecond());
            if (firstPoint == null) {
                firstPoint = point;
                changed = true;
            } else if (secondPoint == null) {
                secondPoint = point;
                changed = true;
            }
        }
    }

    public void createConnection() {
        if (firstPoint == null || secondPoint == null) {
            JOptionPane.showMessageDialog(null, "Points not selected");
            return;
        }

        Object data = Utils.getUserInput(new CreateConnection((String) null));
        if (data == null)
            return;

        Object[] d = (Object[]) data;
        String name = (String) d[0];
        String timeS = (String) d[1];

        if (name == null || timeS == null)
            return;

        int time = 0;

        try {
            time = Integer.parseInt(timeS);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Wrong format");
            return;
        }

        Node f = findPoint(firstPoint);
        Node s = findPoint(secondPoint);

        graph.connect(f, s, name, time);
        changed = true;

        System.out.println("ok: " + f);
    }

    public void showConnections() {
        if (firstPoint == null || secondPoint == null) {
            JOptionPane.showMessageDialog(null, "Points not selected");
            return;
        }

        List<Edge> edges = graph.getEdgesBetween(findPoint(firstPoint), findPoint(secondPoint));
        Utils.getUserInput(new ShowConnections(edges));
    }

    public void changeConnection() {
        if (firstPoint == null || secondPoint == null) {
            JOptionPane.showMessageDialog(null, "Points not selected");
            return;
        }

        List<Edge> edges = graph.getEdgesBetween(findPoint(firstPoint), findPoint(secondPoint));
        Edge e;

        if (edges.size() > 1) {
            Object data = Utils.getUserInput(new ChangeConnections(edges));
            e = (Edge) data;
        } else if (edges.size() == 1)
            e = edges.get(0);
        else {
            JOptionPane.showMessageDialog(null, "No connectnions between points");
            return;
        }

        if (e == null)
            return;

        Object data = Utils.getUserInput(new CreateConnection(e.getName()));
        if (data == null)
            return;

        Object[] d = (Object[]) data;
        String name = (String) d[0];
        String timeS = (String) d[1];

        if (name == null || timeS == null)
            return;

        int time = 0;

        try {
            time = Integer.parseInt(timeS);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Wrong format");
            return;
        }

        Node f = findPoint(firstPoint);
        Node s = findPoint(secondPoint);

        graph.setConnectionWeight(f, s, name, e.getWeight(), time);
        changed = true;

        System.out.println("ok: " + f);
    }

    public void findPath() {
        if (firstPoint == null || secondPoint == null) {
            JOptionPane.showMessageDialog(null, "Points not selected");
            return;
        }
        
        Node f = findPoint(firstPoint);
        Node s = findPoint(secondPoint);
        
        List<Edge> path = graph.getPath(f, s);
        
        if (path.size() == 0) {
            JOptionPane.showMessageDialog(null, "No ways between points");
            return;
        }
        
        int i = 0;
        int total = 0;
        String[] sPath = new String[path.size() + 1];
        Node node = f;
        for (Edge e : path) {
            sPath[i++] = String.format("%s %s %s (%d)", node.getName(), e.getName(), e.getDestination().getName(), e.getWeight());
            total += e.getWeight();
            node = e.getDestination();
        }
        
        sPath[i] = "Total: " + total;
        Utils.getUserInput(new ShowPath(sPath));
    }
    
    public void clear() {
        graph = new ListGraph();
        firstPoint = null;
        secondPoint = null;
        points.clear();
        changed = false;
    }
    
    public Collection<Pair<Integer, Integer>> getPoints() {
        return Collections.unmodifiableCollection(points.keySet());
    }

    @SuppressWarnings("unchecked")
    public Pair<Integer, Integer>[] getSelectedPoints() {
        return new Pair[] { firstPoint, secondPoint };
    }

    public void setImagePanel(ImageMapPanel panel) {
        this.panel = panel;
    }

    public boolean isChanged() {
        return changed;
    }

    public void loadImage() throws IOException {
        File f = new File(imagePath);
        image = ImageIO.read(f);
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    /**
     * @return
     */
    public String getImagePath() {
        // TODO Auto-generated method stub
        return imagePath;
    }

    /**
     * @param b
     */
    public void setChanged(boolean changed) {
        // TODO Auto-generated method stub
        this.changed = changed;
    }
    
    // изображение
    private transient BufferedImage image;
    // состояние
    private State state = new SelectPlacesState();
    // граф
    private ListGraph graph = new ListGraph();
    // выбранные точки
    private Pair<Integer, Integer> firstPoint, secondPoint;
    // координаты клика
    private Pair<Integer, Integer> coords = new Pair<Integer, Integer>();
    // отмеченные точки - связь с графом
    private Map<Pair<Integer, Integer>, Node> points = new HashMap<Pair<Integer, Integer>, Node>();
    // панель, на котороый лежит imageMap
    private ImageMapPanel panel;
    // признак изменения данных
    private boolean changed;
    // путь до картинки для сериализации
    private String imagePath;

    public static final State NEW_PLACE_STATE = new NewPlaceState();
    public static final State SELECT_PLACE_STATE = new SelectPlacesState();
    public static final int POINT_RADIUS = 7;
}
