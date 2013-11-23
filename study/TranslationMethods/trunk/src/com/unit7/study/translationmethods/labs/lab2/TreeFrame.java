package com.unit7.study.translationmethods.labs.lab2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.unit7.study.translationmethods.labs.chains.Container;
import com.unit7.study.translationmethods.labs.chains.Frame;
import com.unit7.study.translationmethods.labs.chains.Vertex;
import com.unit7.study.translationmethods.labs.lab1.GrammarRules;

public class TreeFrame extends Frame<Container> {
    public TreeFrame(Map<String, String> rules, String target, String chain) {
        super(rules, target);
        this.chain = chain;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void show() {
        String[] vars = rules.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
        for (String rule : vars) {
            GrammarRules.State cType;
            // первый символ правила
            if (GrammarRules.isTerminal(rule.charAt(0))) {
                cType = GrammarRules.State.TERMINAL;
            } else {
                cType = GrammarRules.State.NOT_TERMINAL;
            }

            Container container = new Container.Builder().setChain(chain).setChainPos(0).setcType(cType)
                    .setCurrent(new Vertex(target)).setRule(rule).setRulePos(0).build();

            List<Container> toShow = build(container);

            // показываем
            // формируем список деревьев, тыкаем на него — показывает дерево в
            // отдельном фрейме
            int i = 1;
            List<JLabel> trees = new ArrayList<JLabel>();
            for (final Container cont : toShow) {
                if (cont.isResult()) {
                    // add
                    final JLabel label = new JLabel();
                    label.setText("вывод: " + i++);
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFrame frame = new JFrame("Дерево вывода для " + cont.getChain());
                            JPanel tree = showTree(cont.getCurrent());
                            frame.add(tree);
                            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            frame.pack();
                            frame.setVisible(true);
                        }
                    });
                }
            }

            log.info("tree list created");

            JPanel content = null;
            if (trees.size() == 0) {
                content = new JPanel(new GridLayout(1, 1, 10, 10));
                JLabel noTrees = new JLabel("Нет деревьев вывода");
                content.add(noTrees);

            } else {
                content = new JPanel(new GridLayout(trees.size(), 1, 10, 10));
            }

            for (JLabel tree : trees) {
                content.add(tree);
            }

            pack();
            setContentPane(content);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    /**
     * Не будем учитывать размеры экрана — предполагается, что цепочки будут
     * такие, что дерево должно влезть в допустимые размеры. Если вылезет —
     * пофиг.
     * 
     * @param vertex
     * @return
     */
    private JPanel showTree(final Vertex vertex) {
        int levelsCount = getLevelsCount(vertex);
        log.info("levels count: " + levelsCount);
        
        vertexLevelCount = new int[levelsCount];
        countVertexOnLevels(vertex, 0);
        
        JPanel result = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D gr = (Graphics2D) g;
                int maxVCount = getMaxVCount(vertex);
                Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
                if (screeSize.width - (maxVCount - 1) * distance - maxVCount * vertexRadius < 0) {
                    JOptionPane.showMessageDialog(null, "Слишком толстое дерево, показывать не буду");
                    return;
                }

                int maxWidth = distance * (maxVCount - 1) + maxVCount * vertexRadius;
                Point mainPoint = new Point(maxWidth / 2 - vertexRadius / 2, 0);
                drawVertex(gr, vertex, mainPoint);

                drawTree(gr, vertex, mainPoint, maxWidth, 0);
                
                super.paintComponent(g);
            }
        };

        return result;
    }

    /**
     * Считает количество вершин на каждом уровне
     * @param v
     * @param level
     */
    private void countVertexOnLevels(Vertex v, int level) {
        int count = v.getChilds().size();
        vertexLevelCount[level] += count;
        for (Vertex vert : v.getChilds()) {
            countVertexOnLevels(vert, level + 1);
        }
    }
    
    /**
     * Считает количество уровней
     * @param v
     * @return
     */
    private int getLevelsCount(Vertex v) {
        if (v == null)
            return 0;
        
        int max = 1;
        for (Vertex vert : v.getChilds()) {
            max = Math.max(max, getLevelsCount(vert));
        }
        
        return max;
    }
    
    private void drawVertex(Graphics2D g, Vertex v, Point p) {
        g.drawOval(p.x, p.y, vertexRadius, vertexRadius);
    }

    private int getMaxVCount(Vertex v) {
        if (v == null)
            return 0;

        int max = 0;
        for (int i = 0; i < v.getChilds().size(); ++i) {
            max = Math.max(getMaxVCount(v.getChilds().get(i)), max);
        }

        return Math.max(v.getChilds().size(), max);
    }

    /**
     * рисует всех детей вершины @v
     * @param g
     * @param v
     * @param parent
     * @param maxWidth
     * @param level
     */
    private void drawTree(Graphics2D g, Vertex v, Point parent, int maxWidth, int level) {
        if (v == null)
            return;
        
        int curDist = maxWidth / vertexLevelCount[level];
        int curX = maxWidth / vertexLevelCount[level] - curDist / 2;
        
        for (Vertex vert : v.getChilds()) {
            // рисуем
            Point point = new Point(curX, yDist);
            drawVertex(g, vert, point);
            // TODO draw to center
            g.drawLine(parent.x, parent.y, point.x, point.y);
            
            log.info(String.format("vertex: %s drawed, tree draw started", point));
            drawTree(g, vert, point, maxWidth, level + 1);
        }
    }

    private String chain;
    private int distance = 20;
    private int yDist = 20;
    private int vertexRadius = 10;
    private int[] vertexLevelCount;
    private static final Logger log = Logger.getLogger(TreeFrame.class.getName());
}
