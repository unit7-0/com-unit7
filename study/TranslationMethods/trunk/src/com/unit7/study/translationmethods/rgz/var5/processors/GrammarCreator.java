/**
 * Date:	15 дек. 2013 г.
 * File:	GrammarCreator.java
 *
 * Author:	Zajcev V.
 */

package com.unit7.study.translationmethods.rgz.var5.processors;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.unit7.study.translationmethods.labs.lab1.GrammarRules;
import com.unit7.study.translationmethods.labs.lab3.exceptions.InformationException;
import com.unit7.study.translationmethods.labs.utils.Utils;
import com.unit7.study.translationmethods.rgz.var5.Grammar;

/**
 * @author unit7
 *
 */
public class GrammarCreator implements Creator<Grammar>, ActionListener {
    public GrammarCreator(JTextField alph, JTextField begin, JTextField end, JTextField startLen, JTextField endLen) {
        this.alph = alph;
        this.begin = begin;
        this.end = end;
        this.startLen = startLen;
        this.endLen = endLen;
    }
    
    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.rgz.var5.processors.Creator#create()
     */
    @Override
    public Grammar create() throws InformationException {
        String alph = this.alph.getText().trim().replaceAll("\\s", "");
        String beg = this.begin.getText().trim().replaceAll("\\s", "");
        String end = this.end.getText().trim().replaceAll("\\s", "");
        String startLen = this.startLen.getText().trim();
        String endLen = this.endLen.getText().trim();
        
        if (alph.equals("")) {
            throw new InformationException(String.format(EMPTY_INPUT_PARAMETER, "Алфавит"));
        }
        
        if (beg.equals("")) {
            throw new InformationException(String.format(EMPTY_INPUT_PARAMETER, "Начальная подцепочка"));
        }
        
        if (end.equals("")) {
            throw new InformationException(String.format(EMPTY_INPUT_PARAMETER, "Конечная подцепочка"));
        }
        
        int start = 0;
        if (startLen.equals("")) {
            throw new InformationException(String.format(EMPTY_INPUT_PARAMETER, "Минимальная длина"));
        } else {
            try {
                start = Integer.parseInt(startLen);
            } catch (NumberFormatException ex) {
                throw new InformationException(String.format(WRONG_INPUT_PARAMETER, "Минимальная длина"));
            }
        }
        
        int fin = 0;
        if (startLen.equals("")) {
            throw new InformationException(String.format(EMPTY_INPUT_PARAMETER, "Максимальная длина"));
        } else {
            try {
                fin = Integer.parseInt(endLen);
            } catch (NumberFormatException ex) {
                throw new InformationException(String.format(WRONG_INPUT_PARAMETER, "Максимальная длина"));
            }
        }
        
        if (start > fin)
            throw new InformationException("Минимальная длина больше максимальной");
        
        String[] alphArr = alph.split("");
        Set<String> terms = new HashSet<String>();
        for (String str : alphArr) {
            if (str.equals(""))
                continue;
            
            if (str.length() > 1)
                throw new InformationException();
            if (Character.isUpperCase(str.charAt(0)))
                throw new InformationException(UPPER_CASE_FOR_NOT_TERMINALS);
            
            terms.add(str);
        }
        
        if (alphArr.length != terms.size() + 1)
            throw new InformationException(SAME_TERMINALS);
        
        // проверка принаждлежности подцепочек алфавиту
        String chain = beg + end;
        for (int i = 0; i < chain.length(); ++i) {
            if (!terms.contains(String.valueOf(chain.charAt(i)))) {
                throw new InformationException(String.format(WRONG_INPUT_PARAMETER, "начальная или конечная подцепочка"));
            }
        }
        
        String terminals = alph;
        Map<String, String> rules = new HashMap<String, String>();
   
        // наложение цепочек
        String suf = "";
        int first = beg.length(), second = 0;
        for (int i = 0; i < beg.length(); ++i) {
            if (beg.length() - i > end.length())
                continue;
            
            boolean matched = true;
            int k = i;
            for (int j = 0; j < end.length() && k < beg.length(); ++j, ++k) {
                if (beg.charAt(k) != end.charAt(j)) {
                    matched = false;
                }
            }
            
            if (matched) {
                first = i;
                second = beg.length() - i;
                suf = beg.substring(i);
                break;
            }
        }
        
        String notTerminals = "SB";
        StringBuilder B = new StringBuilder();
        StringBuilder S = new StringBuilder();
        String shortest = beg.substring(0, first) + suf + end.substring(second);

        if (start < shortest.length()) {
            throw new InformationException("Минимальная длина меньше чем длина минимально возможной цепочки: " + shortest);
        }
        
        if (shortest.length() > start) {
            throw new InformationException(MIN_LEN_GREATER_SHORTEST_CHAIN);
        }
        
        // построение B с каждым символом
        for (int i = 0; i < terminals.length(); ++i) {
            B.append(terminals.charAt(i)).append("B").append(GrammarRules.PURE_GRAMMAR_DELIMITER);
        }
        
        // добавление в B конечной подцепочки
        B.append(end);
        
        // построение S
        S.append(beg).append("B").append(GrammarRules.PURE_GRAMMAR_DELIMITER).append(shortest);
        
        rules.put("S", S.toString());
        rules.put("B", B.toString());
        grammar = new Grammar(rules, terminals, notTerminals, "S", start, fin);
        return grammar;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        try {
            create();
        } catch (InformationException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
            return;
        }
                
        JFrame frame = new JFrame();
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JPanel terminals = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel notTerminals = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel rules = new JPanel();
        rules.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
 
        JLabel termLabel = new JLabel("Терминалы: ");
        JLabel notTermLabel = new JLabel("Нетерминалы: ");
        JLabel rulesLabel = new JLabel("Правила: ");
        
        JTextField termField = new JTextField(15);
        termField.setText(grammar.getTerminals());
        
        JTextField notTermField = new JTextField(15);
        notTermField.setText(grammar.getNotTerminals());
        
        JTextArea rulesArea = new JTextArea(20, 40);
        JScrollPane pane = new JScrollPane(rulesArea);
        
        terminals.add(termLabel);
        terminals.add(termField);
        
        notTerminals.add(notTermLabel);
        notTerminals.add(notTermField);
        
        rules.add(rulesLabel);
        rules.add(pane);
        
        pane.revalidate();
        
        for (String rule : grammar.getRules().keySet()) {
            rulesArea.append(rule + " -> " + grammar.getRules().get(rule));
            rulesArea.append("\r\n");
        }
        
        JMenuBar bar = Utils.createSaveFileMenu(new String[] { "Грамматика" }, rulesArea);
        frame.setJMenuBar(bar);
        
        main.add(terminals);
        main.add(notTerminals);
        main.add(rules);
        
        frame.setTitle("Грамматика");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        
        Utils.centreFrame(null, frame);
        frame.setVisible(true);
    }
    
    /* (non-Javadoc)
     * @see com.unit7.study.translationmethods.rgz.var5.processors.Creator#getCreated()
     */
    @Override
    public Grammar getCreated() throws Exception {
        if (grammar == null)
            throw new Exception(EMPTY_PARAMETER);
        
        return grammar;
    }
    
    public static final String EMPTY_INPUT_PARAMETER = "Параметр %s не задан";
    public static final String WRONG_INPUT_PARAMETER = "Параметр %s задан неверно";
    public static final String ALPHABET_ONE_CHARACTER = "Единица алфавита - символ";
    public static final String UPPER_CASE_FOR_NOT_TERMINALS = "Символы верхнего регистра зарезервированы для нетерминалов";
    public static final String SAME_TERMINALS = "Встречаются одинаковые терминалы";
    public static final String MIN_LEN_GREATER_SHORTEST_CHAIN = "Минимальная длина цепочки превышает длину кратчайшей цепочки";
    
    private JTextField alph;
    private JTextField begin;
    private JTextField end;
    private JTextField startLen;
    private JTextField endLen;
    
    private Grammar grammar;
}
