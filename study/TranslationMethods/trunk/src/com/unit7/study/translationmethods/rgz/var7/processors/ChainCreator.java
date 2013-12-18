/**
 * Date:	15 дек. 2013 г.
 * File:	ChainCreator.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var7.processors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.rgz.var5.processors.Creator;
import com.unit7.study.translationmethods.rgz.var7.RegExp;

/**
 * @author unit7
 *
 */
public class ChainCreator implements Creator<List<String>>, ActionListener {
    public ChainCreator(RegExpCreator regExpCreator) {
        this.regExpCreator = regExpCreator;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("Цепочки");
        JPanel main = new JPanel();
        JTextArea area = new JTextArea(20, 30);
        JScrollPane pane = new JScrollPane(area);
        
        try {
            create();
        } catch (InformationException e1) {
            JOptionPane.showMessageDialog(null, e1.getLocalizedMessage());
            return;
        }
        
        for (String str : chains) {
            area.append(str);
            area.append("\r\n");
        }
        
        pane.revalidate();
        
        main.add(pane);
        
        frame.getContentPane().add(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.rgz.var5.processors.Creator#create()
     */
    @Override
    public List<String> create() throws InformationException {
        try {
            regExp = regExpCreator.getCreated();
        } catch (Exception e) {
            throw new InformationException(e.getLocalizedMessage());
        }
        
        chains = parser(regExp.getExpr());
        return chains;
    }
    
    private List<String> parser(String expr) {
        List<StringBuilder> curChains = new ArrayList<StringBuilder>();
        curChains.add(new StringBuilder());
        
        for (int i = 0; i < expr.length(); ++i) {
            // terminal
            char c = expr.charAt(i);
            if (regExp.getTerminals().indexOf(c) != -1) {
                List<Integer> toDelete = new ArrayList<Integer>();
                for (int d = 0; d < curChains.size(); ++d) {
                    StringBuilder builder = curChains.get(d);
                    if (builder.length() + 1 > regExp.getMax())
                        toDelete.add(d);
                    
                    builder.append(c);
                }
                
                for (int d : toDelete)
                    curChains.remove(d);
            } else {
                // bracket
                int pos = 0;
                Deque<Character> stack = new ArrayDeque<Character>();
                for (int j = i; j < expr.length(); ++j) {
                    char curC = expr.charAt(j);
                    if (curC == '(')
                        stack.push('(');
                    else if (curC == ')')
                        stack.pop();
                    
                    if (stack.isEmpty()) {
                        pos = j;
                        break;
                    }
                }
                
                List<String> tmpRes = parser(expr.substring(i + 1, pos));
                // pos - last ) position and after - * => i = pos + 1;
                i = pos + 1;
                
                List<StringBuilder> tmp = new ArrayList<StringBuilder>();
                for (String str : tmpRes) {
                    for (StringBuilder builder : curChains) {
                        StringBuilder newBuilder = new StringBuilder(builder.toString()).append(str);
                        tmp.add(newBuilder);
                    }
                }
                
                if (!tmpRes.isEmpty())
                    curChains = tmp;
            }
        }
        
        List<String> result = new ArrayList<String>();
        for (StringBuilder builder : curChains)
            result.add(builder.toString());
        
        return result;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.rgz.var5.processors.Creator#getCreated()
     */
    @Override
    public List<String> getCreated() throws Exception {
        if (chains == null)
            throw new InformationException(Creator.EMPTY_PARAMETER);
        
        return chains;
    }

    private List<String> chains;
    private RegExp regExp;
    private RegExpCreator regExpCreator;
}
