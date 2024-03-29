package com.unit7.study.translationmethods.labs.lab2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
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
import com.unit7.study.translationmethods.labs.utils.Utils;

public class TreeFrame extends Frame<Container> {
    public TreeFrame(Map<String, String> rules, String target, String chain) {
        super(rules, target);
        this.chain = chain;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void showContent() {
        int i = 1;
        String[] vars = rules.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
        List<JLabel> trees = new ArrayList<JLabel>();
        for (String rule : vars) {
            GrammarRules.State cType;
            // первый символ правила
            if (GrammarRules.isTerminal(rule.charAt(0))) {
                cType = GrammarRules.State.TERMINAL;
            } else {
                cType = GrammarRules.State.NOT_TERMINAL;
            }

            Container container = new Container.Builder().setChain(chain).setChainPos(0).setcType(cType)
                    .setCurrent(new Vertex(target)).setRule(rule).setRulePos(0).setRules(rules).build();

            List<Container> toShow = build(container);

            // показываем
            // формируем список деревьев, тыкаем на него — показывает дерево в
            // отдельном фрейме
            for (final Container cont : toShow) {
                if (cont.isResult() && Utils.validateContainer(cont)) {
                    // add
                    final JLabel label = new JLabel();
                    label.setText("вывод: " + i++);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JFrame frame = new JFrame("Дерево вывода для " + cont.getChain());
                            JPanel tree = showTree(cont.getCurrent());
                            frame.getContentPane().add(tree);
                            Dimension d = tree.getSize();
                            d.setSize(d.getWidth() + 100, d.getHeight() + 100);
                            frame.setSize(d);
                            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            // frame.pack();
                            Utils.centreFrame(TreeFrame.this, frame);
                            frame.setVisible(true);
                        }
                    });

                    trees.add(label);
                }
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

        this.getContentPane().add(content);
        pack();
        setSize(new Dimension(getWidth() + 100, getHeight() + 100));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        wasDrawedOnLevel = new int[levelsCount];
        countVertexOnLevels(vertex, 0);
        final int maxVCount = getMaxVCount(vertex);
        final int maxWidth = distance * (maxVCount - 1) + maxVCount * vertexRadius;

        JPanel result = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;

                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
                if (screeSize.width - (maxVCount - 1) * distance - maxVCount * vertexRadius * 2 < 0) {
                    JOptionPane.showMessageDialog(null, "Слишком толстое дерево, показывать не буду");
                    return;
                }

                for (int i = 0; i < wasDrawedOnLevel.length; ++i) {
                    wasDrawedOnLevel[i] = 0;
                }
                
                Point mainPoint = new Point(maxWidth / 2 - vertexRadius, 0);
                drawVertex(gr, vertex, mainPoint);

                drawTree(gr, vertex, mainPoint, maxWidth, 0);
            }
        };

        result.setSize(maxWidth, levelsCount * vertexRadius * 2 + (levelsCount - 1) * yDist);
        return result;
    }

    /**
     * Считает количество вершин на каждом уровне
     * 
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
     * 
     * @param v
     * @return
     */
    private int getLevelsCount(Vertex v) {
        if (v == null)
            return 0;

        int max = 1;
        for (Vertex vert : v.getChilds()) {
            max = Math.max(max, getLevelsCount(vert) + 1);
        }

        return max;
    }

    private void drawVertex(Graphics2D g, Vertex v, Point p) {
        g.drawOval(p.x, p.y, vertexRadius * 2, vertexRadius * 2);
        Color saved = g.getColor();
        g.setColor(textColor);
        g.drawString(v.getName(), p.x + vertexRadius - 5, p.y + vertexRadius);
        g.setColor(saved);
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
     * 
     * @param g
     * @param v
     * @param parent
     * @param maxWidth
     * @param level
     */
    private void drawTree(Graphics2D g, Vertex v, Point parent, int maxWidth, int level) {
        if (v == null || v.getChilds().size() == 0)
            return;

        int curDist = maxWidth / vertexLevelCount[level];
        int curX = maxWidth / vertexLevelCount[level] - curDist / 2 + wasDrawedOnLevel[level + 1] * curDist;

        for (Vertex vert : v.getChilds()) {
            // рисуем
            Point point = new Point(curX, yDist + parent.y);
            drawVertex(g, vert, point);
            g.drawLine(parent.x + vertexRadius, parent.y + vertexRadius * 2, point.x + vertexRadius, point.y);

            wasDrawedOnLevel[level + 1] += 1;
            log.info(String.format("vertex: %s drawed, tree draw started", point));
            drawTree(g, vert, point, maxWidth, level + 1);
            curX += curDist;
        }
    }

    private String chain;
    private int distance = 50;
    private int yDist = 50;
    private int vertexRadius = 15;
    private int[] vertexLevelCount;
    private int[] wasDrawedOnLevel;
    private Color textColor = Color.RED;
    private static final Logger log = Logger.getLogger(TreeFrame.class.getName());
}
