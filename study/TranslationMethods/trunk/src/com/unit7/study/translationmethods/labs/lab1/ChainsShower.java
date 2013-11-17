package com.unit7.study.translationmethods.labs.lab1;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChainsShower extends JFrame {
    public ChainsShower(Map<String, String> exprs, String target, int lens) {
        super("Цепочки");
        this.expressions = exprs;
        this.maxLen = lens;
        recursiveRemover.remove(expressions);

        JTextArea area = new JTextArea();
        JPanel panel = new JPanel();
        JScrollPane pane = new JScrollPane(area);
        String[] vars = exprs.get(target).split(GrammarRules.GRAMMAR_DELIMETER);
        // for each rule we call doBuild and get some chain
        Set<String> finalAnswer = new HashSet<String>();
        for (String var : vars) {
            // if there is a repeats...
            finalAnswer.addAll(doBuild(var, 0));
//            log.put(var, target);
        }

        for (String res : finalAnswer) {
            if (!res.isEmpty()) {
                area.append(res + "\n");
                final JLabel label = new JLabel(res);
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String chain = label.getText();
                        List<String> path = new ArrayList<String>();
                        String begin = chain;
/*                        do {
                            path.add(begin);
                        } while ();
*/
                        Collections.reverse(path);
                        StringBuilder result = new StringBuilder();
                        for (String comp : path) {
                            result.append(comp).append("->");
                        }

                        result.delete(result.length() - 2, result.length());
                        JFrame window = new JFrame();
                        window.add(new JLabel(result.toString()));
                        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        window.setVisible(true);
                        window.pack();
                    }
                });

                panel.add(label);
//                log.put(res, var);
            }
        }

        setContentPane(pane);
        setSize(new Dimension(400, 300));
        try {
            FileOutputStream out = new FileOutputStream("/home/unit7/log.txt");
            PrintWriter writer  = new PrintWriter(out);
            writer.println(log.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * build chains TODO improve asymptotic
     * 
     * @param expr
     * @param pos
     * @return
     */
    private List<String> doBuild(String expr, int pos) {
        StringBuilder result = new StringBuilder(expr.substring(0, pos));
        List<String> totalResult = new ArrayList<String>();
        // step by step through expression if we met notTerminal then buid all
        // possible chains
        if (pos > maxLen)
            return totalResult;
        for (int i = pos; i < expr.length(); ++i) {
            if (GrammarRules.isTerminal(expr.charAt(i))) {
                if (result.length() + 1 > maxLen) {
                    return totalResult;
                }

                result.append(expr.charAt(i));
            } else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
                // possible chains
                String name = nameToolz.getName(expr.substring(i));
                String[] possibleChains = expandChain(name);
                // each chain may present as before = result[pos..i - 1],
                // current = one of chains[]
                // and after = before + each of chains[] + remain
                // expression(expr)
                for (String possible : possibleChains) {
                    if (possible.equals(GrammarRules.GRAMMAR_EMPTY)) {
                        // add current chain to result if we find the empty
                        // symbol
                        // and at the end we must add all chains to the set
                        // for construct final answer without repeats
                        // totalResult.add(result.toString());
                        List<String> anotherChains = doBuild(result.toString() + expr.substring(i + name.length()),
                                result.length());
                        totalResult.addAll(anotherChains);
//                        log.put(expr, result.toString());
                        continue;
                    }

                    List<String> chains = null;
                    // TODO make it normal (crazy)
                    try {
                        chains = doBuild(result.toString() + possible + expr.substring(i + name.length()),
                                result.length());
                    } catch (StackOverflowError err) {
                        continue;
                    }

                    // log all manipulations
                    for (String chain : chains) {
//                        log.put(chain, expr);
                    }

                    totalResult.addAll(chains);
                }

                return totalResult;
            }
        }

        totalResult.add(result.toString());
        return totalResult;
    }

    private String[] expandChain(String chain) {
        return expressions.get(chain).split(GrammarRules.GRAMMAR_DELIMETER);
    }

    private int maxLen;
    private RecursionRemover recursiveRemover = new LeftRecursionRemover();
    private NameToolz nameToolz = new DigitNameToolz();
    private Map<String, String> expressions;
    
    private static final Logger log = Logger.getLogger(ChainsShower.class.getName());
}
