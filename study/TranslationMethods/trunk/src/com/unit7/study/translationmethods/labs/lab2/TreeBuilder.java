package com.unit7.study.translationmethods.labs.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.unit7.study.translationmethods.labs.chains.Builder;
import com.unit7.study.translationmethods.labs.chains.Container;
import com.unit7.study.translationmethods.labs.chains.Vertex;
import com.unit7.study.translationmethods.labs.lab1.GrammarRules;

public class TreeBuilder implements Builder<Container> {
    @Override
    public List<Container> build(Container container, Object... args) {
        Container result = copyContainer(container);
        result.setResult(false); // первоначально не можем построить
        List<Container> total = new ArrayList<Container>();
        total.add(result);

        int count = 0;
        if (args != null && args.length != 0 && args[0] instanceof Integer) {
            count = (Integer) args[0];
        }

        if (count > maxIter) {
            return total;
        }

        // step by step through expression if we met notTerminal then buid all
        // possible chains

        String expr = container.getRule();
        for (int i = container.getRulePos(); i < expr.length(); ++i) {
            if (GrammarRules.isTerminal(expr.charAt(i))) {
                if (result.getChainPos() >= result.getChain().length()
                        || !(expr.charAt(i) == result.getChain().charAt(result.getChainPos()))) {
                    return total;
                } else {
                    result.setChainPos(result.getChainPos() + 1);
                    result.getCurrent().addChild(new Vertex(String.valueOf(expr.charAt(i))));
                }
            } else if (GrammarRules.isNotTerminal(expr.charAt(i))) {
                // possible chains
                String name = expr.substring(i, i + 1);
                String[] possibleChains = expandChain(name, container.getRules());
                Vertex current = new Vertex(name);
                // result.getCurrent().addChild(current);
                // each chain may present as before = result[pos..i - 1],
                // current = one of chains[]
                // and after = before + each of chains[] + remain
                // expression(expr)
                for (String possible : possibleChains) {
                    if (possible.equals(GrammarRules.GRAMMAR_EMPTY)) {
                        // одной из правил развернули в пустую цепочку,
                        // продолжом анализировать после текущего нетерминала
                        Container cur = copyContainer(result);
                        cur.setRulePos(i + 1);
                        cur.setCurrent(new Vertex(name));
                        List<Container> anotherResults = build(cur);
                        for (Container cont : anotherResults) {
                            if (cont.isResult()) {
                                // цепочка должна была законочиться, так как
                                // обработали в build оставшееся правило, иначе
                                // цепочка не подходит и результаты не добавляем
                                if (cont.getChainPos() == cont.getChain().length()) {
                                    Container resultCopy = copyContainer(result);
                                    List<Vertex> resultChilds = resultCopy.getCurrent().getChilds();
                                    resultChilds.add(cont.getCurrent());
                                    resultCopy.setResult(true);
                                    total.add(resultCopy);
                                }
                            }
                        }

                        // log.put(expr, result.toString());
                        continue;
                    }

                    Container cur = copyContainer(result);
                    Vertex newNode = new Vertex(name);
                    cur.setRule(possible);
                    cur.setRulePos(0);
                    cur.setCurrent(newNode);
                    List<Container> res = build(cur);

                    for (Container cont : res) {
                        if (cont.isResult()) {
                            // цепочка закончилась после анализирования
                            if (cont.getChainPos() == cont.getChain().length()) {
                                // проверим, что и остальное выражение
                                // закончилось, тогда наш контейнер хороший и
                                // является деревом вывод иначе он плохой,
                                // потому как правило осталось, а цепочка уже
                                // кончилась, можно проверить, что остальные
                                // символы в правле нетерминалы и они
                                // разворачиваются в пустые символы
                                if (i + 1 == expr.length()) {
                                    Container resultCopy = copyContainer(result);
                                    List<Vertex> resultChilds = resultCopy.getCurrent().getChilds();
                                    resultChilds.add(cont.getCurrent());
                                    resultCopy.setResult(true);
                                    total.add(resultCopy);
                                } else {
                                    // разворачиваются в пустой символ
                                    Container curCont = copyContainer(result);
                                    curCont.setRulePos(i + 1);
                                    curCont.getCurrent().addChild(cont.getCurrent());
                                    curCont.setChainPos(cont.getChainPos());
                                    List<Container> anotherResults = build(curCont);
                                    for (Container anRes : anotherResults) {
                                        // обработка терминалов была
                                        // недопустима, могли только в пустые
                                        // развернуть => результат успешен если
                                        // result == true, дополнительных
                                        // проверок не нужно
                                        if (anRes.isResult()) {
                                            Container resultCopy = copyContainer(result);
                                            List<Vertex> resultChilds = resultCopy.getCurrent().getChilds();
                                            resultChilds.add(cont.getCurrent());
                                            resultChilds.add(anRes.getCurrent());
                                            resultCopy.setResult(true);
                                            total.add(resultCopy);
                                        }
                                    }
                                }
                            } else {
                                // правило из развернутого нетерминала
                                // соответствует части цепочки, но еще осталась
                                // часть => ее нужно сопоставить оставщейся
                                // части правила, иначе она не подходит
                                if (i + 1 < expr.length()) {
                                    Container resultCopy = copyContainer(result);
                                    List<Vertex> resultChilds = resultCopy.getCurrent().getChilds();
                                    resultChilds.add(cont.getCurrent());
                                    resultCopy.setRulePos(i + 1);
                                    List<Container> anRes = build(resultCopy);
                                    for (Container resCont : anRes) {
                                        if (resCont.isResult()) {
                                            Container resultCopy2 = copyContainer(resultCopy);
                                            resultCopy2.getCurrent().addChild(resCont.getCurrent());
                                            resultCopy2.setResult(true);
                                            total.add(resultCopy2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                return total;
            }
        }

        // выражение кончилось и цепочка тоже
        if (result.getChainPos() == result.getChain().length()) {
            result.setResult(true);
        }
        
        return total;
    }

    private String[] expandChain(String name, Map<String, String> rules) {
        return rules.get(name).split(GrammarRules.GRAMMAR_DELIMETER);
    }

    /**
     * Копирует все поля в новый контейнер и копирует дерево полностью
     * 
     * @param src
     * @return
     */
    private Container copyContainer(Container src) {
        Vertex vertCopy = new Vertex(src.getCurrent().getName());
        vertCopy.setChilds(copyTree(src.getCurrent()));

        Container copy = new Container.Builder().setChain(src.getChain()).setChainPos(src.getChainPos())
                .setcType(src.getcType()).setResult(src.isResult()).setRule(src.getRule()).setRulePos(src.getRulePos())
                .setCurrent(vertCopy).build();

        return copy;
    }

    /**
     * Копирует детей вершины
     * 
     * @param vert
     * @return
     */
    private List<Vertex> copyTree(Vertex vert) {
        List<Vertex> list = new ArrayList<Vertex>();
        for (Vertex v : vert.getChilds()) {
            Vertex copy = new Vertex(v.getName());
            copy.setChilds(copyTree(v));
        }

        return list;
    }

    // фиктивное поле, для нормальной реализации не требуется, заменить если
    // появится надобность и время.
    private int maxIter = 5000;
}
