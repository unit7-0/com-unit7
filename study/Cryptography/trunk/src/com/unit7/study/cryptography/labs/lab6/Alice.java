package com.unit7.study.cryptography.labs.lab6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unit7.study.cryptography.labs.lab1.MathUtils;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;
import com.unit7.study.cryptography.labs.lab6.interfaces.Subject;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.tools.Pair;

public class Alice implements Subject {
    public Alice(GraphObject object) {
        this.graph = object;
        this.coder = new RSACoder();
        this.coder.setDb(this.coder.getD());
        this.coder.setNb(this.coder.getN());
    }

    @Override
    public VerificationData beginVerification() {
        makeIsomorph();
        updateH();
        updateF();

        graph.setH(null);
        return graph;
    }

    @Override
    public VerificationData endVerification(Question question) {
        if (QuestionType.GAMILTONS_CYCLE.equals(question.getType())) {
            return decypherCycle();
        } else if (QuestionType.ISOMORPHIC_GRAPH.equals(question.getType())) {
            return decypherGraph();
        }

        throw new IllegalArgumentException("no realisations for input question types");
    }

    private void makeIsomorph() {
        int[][] g = graph.getG();
        int count = g.length * g.length;
        swaped = new ArrayList<Pair<Integer, Integer>>();

        // 20% вершин поменяем
        int toSwap = (int) (count * 0.2);
        for (int i = 0; i < toSwap; ++i) {
            int first = MathUtils.getRandInt(g.length);
            int second = MathUtils.getRandInt(g.length);

            if (first == second)
                continue;

            // меняем строки first и second
            int[] tmp = g[first];
            g[first] = g[second];
            g[second] = tmp;

            // меняем столбцы first и second
            for (int j = 0; j < g.length; ++j) {
                int val = g[j][first];
                g[j][first] = g[j][second];
                g[j][second] = val;
            }

            // пройдемся по циклу и поменяем перенумерованные вершины
            for (Pair<Integer, Integer> pair : cycle) {
                if (pair.getFirst().equals(first))
                    pair.setFirst(second);
                else if (pair.getFirst().equals(second))
                    pair.setFirst(first);

                if (pair.getSecond().equals(second))
                    pair.setSecond(first);
                else if (pair.getSecond().equals(first))
                    pair.setSecond(second);
            }

            // добавим в список обменянных
            swaped.add(new Pair(first, second));
        }
    }

    private void updateH() {
        int[][] g = graph.getG();
        h = new int[g.length][g[0].length];
        for (int i = 0; i < g.length; ++i) {
            for (int j = 0; j < g[i].length; ++i) {
                h[i][j] = MathUtils.getRandInt(102314120) * 10 + g[i][j];
            }
        }

        graph.setH(h);
    }

    private void updateF() {
        int[][] h = graph.getH();
        int[][] f = new int[h.length][h[0].length];

        for (int i = 0; i < h.length; ++i) {
            for (int j = 0; j < h[i].length; ++j) {
                f[i][j] = (int) coder.getEncoded(h[i][j]);
            }
        }

        graph.setF(f);
    }

    private Answer decypherCycle() {
        Map<Pair<Integer, Integer>, Integer> indicies = new HashMap<Pair<Integer, Integer>, Integer>();

        for (Pair<Integer, Integer> pair : cycle) {
            indicies.put(pair, h[pair.getFirst()][pair.getSecond()]);
        }

        Answer answer = new Answer();
        answer.setIndicies(indicies);
        answer.setCoder(coder);
        return answer;
    }

    private Answer decypherGraph() {
        Answer answer = new Answer();
        int[][] f = graph.getF();
         h = new int[f.length][f[0].length];

        /*for (int i = 0; i < f.length; ++i) {
            for (int j = 0; j < f[i].length; ++j) {
                h[i][j] = coder.getDecoded(f[i][j]);
            }
        }*/

        answer.setSwaped(swaped);
        answer.setCoder(coder);
        answer.setH(h);
        return answer;
    }

    public CoderInfo getCoderInfo() {
        RSACoder other = new RSACoder();
        other.setDb(coder.getDb());
        other.setNb(coder.getNb());
        return other;
    }

    private int[][] h;
    private GraphObject graph;
    private List<Pair<Integer, Integer>> cycle;
    private List<Pair<Integer, Integer>> swaped;
    private RSACoder coder;
}
