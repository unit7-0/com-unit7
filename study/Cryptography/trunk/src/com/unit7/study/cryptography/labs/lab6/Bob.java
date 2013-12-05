package com.unit7.study.cryptography.labs.lab6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab6.interfaces.Question;
import com.unit7.study.cryptography.labs.lab6.interfaces.QuestionType;
import com.unit7.study.cryptography.labs.lab6.interfaces.VerificationData;
import com.unit7.study.cryptography.labs.lab6.interfaces.Verifier;
import com.unit7.study.cryptography.tools.Pair;

public class Bob implements Verifier {
    @Override
    public Question verifySubject(VerificationData data) {
        if (!(data instanceof GraphObject)) {
            throw new IllegalArgumentException("data of this type is not supported by Bob");
        }

        try {
            this.data = (GraphObject) ((GraphObject) data).clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Question q = new QuestionImpl();
        QuestionType qType = QuestionType.values()[rnd.nextInt(QuestionType.values().length)];
        q.setType(qType);

        this.qType = qType;

        return q;
    }

    @Override
    public boolean acceptVerifying(VerificationData data) throws Exception {
        if (!(data instanceof Answer)) {
            throw new IllegalArgumentException("data of this type is not supported by Bob");
        }

        Answer answer = (Answer) data;

        Map<Pair<Integer, Integer>, Integer> indicies = answer.getIndicies();
        int[][] h = answer.getH();
        CoderInfo coder = answer.getCoder();
        List<Pair<Integer, Integer>> swaped = answer.getSwaped();

        if ((QuestionType.GAMILTONS_CYCLE.equals(qType) && (indicies == null || indicies.size() == 0))
                || (QuestionType.ISOMORPHIC_GRAPH.equals(qType) && (h == null) || swaped == null) || coder == null) {
            throw new Exception("answer not matched with question");
        }

        int[][] f = this.data.getF();
        if (QuestionType.GAMILTONS_CYCLE.equals(qType)) {
            Map<Integer, Integer> vertexes = new HashMap<Integer, Integer>();
            // проверяем, что прислали именно то, что нужно
            for (Pair<Integer, Integer> pair : indicies.keySet()) {
                int val = indicies.get(pair);
                int coded = (int) coder.getEncoded(val);

                int start = 0;
                if (vertexes.containsKey(pair.getFirst()))
                    start = vertexes.get(pair.getFirst());

                vertexes.put(pair.getFirst(), start);

                start = 0;
                if (vertexes.containsKey(pair.getSecond()))
                    start = vertexes.get(pair.getSecond());

                vertexes.put(pair.getSecond(), start);

                if (f[pair.getFirst()][pair.getSecond()] != coded) {
                    System.out.println("Не соответствует значение в цикле и матрице F");
                    return false;
                }
            }

            // проверяем, что цикл проходит через все вершины
            if (vertexes.size() != this.data.getG().length) {
                System.out.println("Не все вершины пройдены циклом");
                return false;
            }

            int oneCounter = 0;
            for (int val : vertexes.keySet()) {
                int sec = vertexes.get(val);
                if (sec == 1)
                    ++oneCounter;
                else if (sec != 2) {
                    System.out.println("Заход в одну вершину более 1го раза");
                    return false;
                }
            }

            if (oneCounter != 2) {
                System.out.println("Стартовая и конечная вершины должно быть две!");
                return false;
            }
        } else if (QuestionType.ISOMORPHIC_GRAPH.equals(qType)) {
            // проверяем, что кодированные значения верны
            for (int i = 0; i < h.length; ++i) {
                for (int j = 0; j < h.length; ++j) {
                    int coded = (int) coder.getEncoded(h[i][j]);
                    if (coded != this.data.getF()[i][j]) {
                        System.out.println("Не соответствует матрица H матрице F");
                        return false;
                    }
                }
            }

            for (Pair<Integer, Integer> pair : swaped) {
                int first = pair.getFirst();
                int second = pair.getSecond();

                // меняем строки first и second
                int[] tmp = h[first];
                h[first] = h[second];
                h[second] = tmp;

                // меняем столбцы first и second
                for (int j = 0; j < h.length; ++j) {
                    int val = h[j][first];
                    h[j][first] = h[j][second];
                    h[j][second] = val;
                }

            }

            // проверяем, что h соотвествует g
            int[][] g = this.data.getG();
            for (int i = 0; i < g.length; ++i) {
                for (int j = 0; j < g.length; ++j) {
                    if (((g[i][j] & 1) == 1 && (h[i][j] & 1) == 0) || ((g[i][j] & 1) == 0 && (h[i][j] & 1) == 1)) {
                        System.out.println("Не соответствует матрица H матрице G");
                        return false;
                    }
                }
            }
        } else {
            throw new Exception("type of answer is incorrect");
        }

        return false;
    }

    private GraphObject data;
    private Random rnd = new Random();
    private QuestionType qType;
}
