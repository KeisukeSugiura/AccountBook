package com.example.AccountBook.module;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/02/25.
 */
public class ExcuteFormula {

    //文字列分解のための記号
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTI = "*";
    public static final String DIVIDE = "/";
    public static final String LEFTARK = "(";
    public static final String RIGHTARK = ")";
    public static final String SIN = "sin";
    public static final String COS = "cos";
    public static final String TAN = "tan";
    public static final String LOG = "log";
    public static final String DOT = ".";
    public static final String HAT = "^";

    private static int mMinusFlg = 0;

    private static int PRECISE = 8;

    //文字列たち
    //  public String mOriginalFormula;
    //  public ArrayList<String> mParsedFormula;
    //  public ArrayList<String> mInvPorlandFormula;
    //  public double mResult;
    public static ExcuteFormula sInstance;

    //コンストラクタ
     /*
    public ExcuteFormula(String str) {
        mOriginalFormula = str;
        mParsedFormula = parseFormula(mOriginalFormula);
        Log.d("parse", mParsedFormula.toString());
        mInvPorlandFormula = changeOrder(mParsedFormula);
        Log.d("inv", mInvPorlandFormula.toString());
        mResult = calculateFormula(mInvPorlandFormula);


    }*/

    private ExcuteFormula() {

    }


    /*
    メソッド
     */

    private static ExcuteFormula getInstance() {
        if (sInstance == null) {
            sInstance = new ExcuteFormula();
        }
        return sInstance;
    }

    public static String calculate(String formula) {


        return getInstance().calculateFormula(changeOrder(parseFormula(formula)));

    }


    //getter
    //public double getResult() {
    //    return mResult;
    //}

    //checker

    /**
     * +-かどうかチェック
     *
     * @param s
     * @return
     */
    private static boolean isLevel1(String s) {
        if (s.equals(PLUS) || s.equals(MINUS)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 積商かどうかをチェック
     *
     * @param s
     * @return
     */
    private static boolean isLevel2(String s) {
        if (s.equals(MULTI) || s.equals(DIVIDE)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 右側依存関数
     *
     * @param s
     * @return
     */
    private static boolean isLevel3(String s) {
        if (s.equals(HAT) || s.equals(SIN) || s.equals(COS) || s.equals(TAN) || s.equals(LOG)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * かっこ最強
     *
     * @param s
     * @return
     */
    private static boolean isLevel4(String s) {
        if (s.equals(LEFTARK) || s.equals(RIGHTARK)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 指定記号かチェック
     *
     * @param s
     * @return
     */
    private static boolean isMark(String s) {

        if (isLevel1(s) || isLevel2(s) || isLevel3(s) || isLevel4(s)) {
            return true;
        } else {
            return false;
        }

    }

    //解析

    /**
     * 文字列をノードに分解する
     *
     * @param str
     * @return
     */
    private static ArrayList<String> parseFormula(String str) {
        ArrayList<String> formulaList = new ArrayList<String>();
        String regStr = new String("");

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (('0' <= ch && ch <= '9') || ch == '.') {
                //数字あるいは小数点
                regStr += String.valueOf(ch);
            } else {

                String s = String.valueOf(ch);
                if (isMark(s)) {
                    //指定一文字記号

                    if (!regStr.isEmpty()) {
                        formulaList.add(regStr);
                        if (mMinusFlg > 0) {
                            formulaList.add(")");
                            mMinusFlg--;
                        }
                        regStr = null;
                        regStr = new String("");
                        if (s.equals(LEFTARK)) {
                            formulaList.add("*");
                        }
                    }
                    if (s.equals("-")) {
                        if (!formulaList.isEmpty() && !isMark(formulaList.get(formulaList.size() - 1))) {
                            formulaList.add("+");
                            formulaList.add("0");
                            formulaList.add(s);
                        } else if (!formulaList.isEmpty() && formulaList.get(formulaList.size() - 1).equals(RIGHTARK)) {
                            formulaList.add(s);
                        } else {
                            formulaList.add("(");
                            formulaList.add("0");
                            formulaList.add(s);
                            mMinusFlg++;
                        }
                    } else {
                        formulaList.add(s);
                    }
                } else {
                    //sincostanlog
                    //Todo 問題の点
                    if (str.length() > 2) {
                        s += String.valueOf(str.charAt(++i));
                        s += String.valueOf(str.charAt(++i));

                        if (!regStr.isEmpty()) {
                            formulaList.add(regStr);
                            if (mMinusFlg > 0) {
                                formulaList.add(")");
                                mMinusFlg--;
                            }
                            regStr = null;
                            regStr = new String("");

                        }
                        if (isLevel3(s)) {
                            if (!formulaList.isEmpty() && !isMark(formulaList.get(formulaList.size() - 1))) {

                                formulaList.add("*");
                            } else if (!formulaList.isEmpty() && formulaList.get(formulaList.size() - 1).equals(RIGHTARK)) {
                                formulaList.add("*");
                            }
                            formulaList.add(s);
                        } else {
                            //パースに失敗
                            return new ArrayList<String>();
                        }
                    } else {
                        //パースに失敗
                        return new ArrayList<String>();
                    }
                }


            }

        }
        //残りの処理
        if (!regStr.isEmpty()) {
            formulaList.add(regStr);
            while (mMinusFlg > 0) {
                formulaList.add(")");
                mMinusFlg--;
            }
        }
       // Log.d("parse", formulaList.toString());
        return formulaList;
    }

    /**
     * ノードリストを逆ポーランド記法に書き換える
     *
     * @param list
     * @return
     */
    private static ArrayList<String> changeOrder(ArrayList<String> list) {

        ArrayList<String> outputQue = new ArrayList<String>();
        ArrayList<String> operatorStack = new ArrayList<String>();
        int stackLevel = 0; // stack2の状態1ならば+-,2falseならば*/
        ArrayList<Integer> preStackLevel = new ArrayList<Integer>();
        int stackPointer = 0;

		/*
         * 優先順位 () > ^sctl > +- > /*
		 */
        //操車場アルゴリズム

        for (String token : list) {
            switch (checkLevel(token)) {
                case 1:
                    //同じレベルか高いレベルでオペレータスタックに積むかどうかをちぇけら
                    while (!operatorStack.isEmpty() && stackLevel >= 1) {
                        outputQue.add(operatorStack.get(stackPointer - 1));
                        operatorStack.remove(--stackPointer);
                        if (stackPointer < 1) {
                            stackPointer = 0;
                            break;
                        } else {
                            if (operatorStack.get(stackPointer - 1).equals(LEFTARK)) {
                                stackLevel = 0;
                            } else {
                                stackLevel = checkLevel(operatorStack.get(stackPointer - 1));
                            }
                        }

                    }
                    operatorStack.add(token);
                    stackPointer++;
                    stackLevel = checkLevel(token);

                    break;
                case 2:
                    //同じレベルか高いレベルでオペレータスタックに積むかどうかをちぇけら
                    while (!operatorStack.isEmpty() && stackLevel >= 2) {
                        outputQue.add(operatorStack.get(stackPointer - 1));
                        operatorStack.remove(--stackPointer);
                        if (stackPointer < 1) {
                            stackPointer = 0;
                            break;
                        } else {
                            if (operatorStack.get(stackPointer - 1).equals(LEFTARK)) {
                                stackLevel = 0;
                            } else {
                                stackLevel = checkLevel(operatorStack.get(stackPointer - 1));
                            }
                        }
                    }
                    operatorStack.add(token);
                    stackPointer++;

                    stackLevel = checkLevel(token);

                    break;
                case 3:
                    operatorStack.add(token);
                    stackPointer++;

                    stackLevel = checkLevel(token);
                    Log.d("change", String.valueOf(stackLevel));
                    break;
                case 4:
                    if (token.equals(LEFTARK)) {

                        operatorStack.add(token);
                        stackPointer++;
                        preStackLevel.add(stackLevel);
                        stackLevel = 0;
                    } else if (token.equals(RIGHTARK)) {
                        while (!operatorStack.isEmpty() && !operatorStack.get(stackPointer - 1).equals(LEFTARK)) {
                            outputQue.add(operatorStack.get(stackPointer - 1));
                            operatorStack.remove(--stackPointer);
                            if (stackPointer < 1) {
                                stackPointer = 0;
                                break;
                            } else {
                                // stackLevel = checkLevel(operatorStack.get(stackPointer - 1));
                            }
                        }

                        if (!operatorStack.isEmpty()) {
                            operatorStack.remove(--stackPointer);
                            stackLevel = preStackLevel.get(preStackLevel.size() - 1);
                            preStackLevel.remove(preStackLevel.size() - 1);
                        } else {
                            stackLevel = 0;
                            stackPointer = 0;
                        }

                    }
                    break;
                case 0:
                    outputQue.add(token);
                    break;


            }


        }

        while (stackPointer > 0) {
            outputQue.add(operatorStack.get(--stackPointer));

        }
        //Log.d("revpor", outputQue.toString());
        return outputQue;
    }

    private static int checkLevel(String s) {
        if (isLevel1(s)) {
            return 1;
        } else if (isLevel2(s)) {
            return 2;
        } else if (isLevel3(s)) {
            return 3;
        } else if (isLevel4(s)) {
            return 4;
        } else {
            return 0;
        }

    }

    /**
     * 逆ポーランド記法のノードリストを計算する
     */
    private static String calculateFormula(ArrayList<String> formula) {
        ArrayList<String> calStack = new ArrayList<String>();
        double accumulator = Double.NaN;

        for (String token : formula) {
            if (!isMark(token)) {
                if (token.equals(".")) {
                    accumulator = 0.0;
                } else if (checkPeriodCount(token)) {
                    accumulator = Double.parseDouble(token);
                } else {
                    //accumulator = Double.NaN;
                    return "Error";
                }
                calStack.add(token);
            } else {
                if (token.equals(PLUS)) {
                    try {
                        accumulator = Double.parseDouble(calStack.get(calStack.size() - 2)) + Double.parseDouble(calStack.get(calStack.size() - 1));
                        calStack.remove(calStack.size() - 1);
                    } catch (Exception e) {
                        //Log.e("plus","error");
                        //return Double.NaN;
                        return "Error";
                    }
                } else if (token.equals(MINUS)) {
                    try {
                        accumulator = Double.parseDouble(calStack.get(calStack.size() - 2)) - Double.parseDouble(calStack.get(calStack.size() - 1));
                        calStack.remove(calStack.size() - 1);
                    } catch (Exception e) {
                        //Log.e("minus","error");
                        //return Double.NaN;
                        return "Error";

                    }

                } else if (token.equals(MULTI)) {
                    try {
                        accumulator = Double.parseDouble(calStack.get(calStack.size() - 2)) * Double.parseDouble(calStack.get(calStack.size() - 1));
                        calStack.remove(calStack.size() - 1);
                    } catch (Exception e) {
                        //Log.e("multi","error");
                        //return Double.NaN;
                        return "Error";

                    }
                } else if (token.equals(DIVIDE)) {
                    try {
                        accumulator = Double.parseDouble(calStack.get(calStack.size() - 2)) / Double.parseDouble(calStack.get(calStack.size() - 1));
                        calStack.remove(calStack.size() - 1);
                    } catch (Exception e) {
                        //Log.e("div","error");
                        // return Double.NaN;
                        return "Error";

                    }
                } else if (token.equals(SIN)) {
                    accumulator = Math.sin(Math.toRadians(Double.parseDouble(calStack.get(calStack.size() - 1))));
                } else if (token.equals(COS)) {
                    accumulator = Math.cos(Math.toRadians(Double.parseDouble(calStack.get(calStack.size() - 1))));

                } else if (token.equals(TAN)) {
                    accumulator = Math.tan(Math.toRadians(Double.parseDouble(calStack.get(calStack.size() - 1))));

                } else if (token.equals(LOG)) {
                    accumulator = Math.log10(Double.parseDouble(calStack.get(calStack.size() - 1)));
                } else if (token.equals(HAT)) {
                    try {
                        accumulator = Math.pow(Double.parseDouble(calStack.get(calStack.size() - 2)), Double.parseDouble(calStack.get(calStack.size() - 1)));
                        calStack.remove(calStack.size() - 1);
                    } catch (Exception e) {
                        //Log.e("hat","error");
                        // return Double.NaN;
                        return "Error";

                    }

                } else {
                    return "Error";

//                    return Double.NaN;
                }
                calStack.remove(calStack.size() - 1);
                if(!Double.isNaN(accumulator) && !Double.isInfinite(accumulator)) {
                    BigDecimal bigDecimal = new BigDecimal(accumulator);
                    bigDecimal = bigDecimal.setScale(PRECISE, BigDecimal.ROUND_HALF_UP);
                    accumulator = bigDecimal.doubleValue();

                    calStack.add(String.valueOf(bigDecimal));
                }else{
                    break;
                }


            }


        }


        if (Double.isNaN(accumulator)) {
            return "NaN";
        } else if (Double.isInfinite(accumulator)) {
            return "Infinity";
        } else {
            if (Integer.MAX_VALUE < accumulator || Integer.MIN_VALUE > accumulator) {
                return String.valueOf(accumulator);
            } else {

                int checker = (int) accumulator;
             //   Log.d("result", String.valueOf(checker));
                //整数は整数で返す
                if (Double.compare(accumulator, 0.0) < 0) {
                  //  Log.d("-",String.valueOf(accumulator));
                    if (checker-1 < accumulator && accumulator < checker) {

                        return String.valueOf(accumulator);
                    } else {
                        return String.valueOf(checker);
                    }

                } else {
                  //  Log.d("+",String.valueOf(accumulator));

                    if (checker < accumulator && accumulator < checker + 1) {

                        return String.valueOf(accumulator);
                    } else {
                        return String.valueOf(checker);
                    }
                }
            }
        }
    }


    private static boolean checkPeriodCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                count++;

            }
        }
        if (count < 2) {
            return true;
        } else {
            return false;
        }


    }


}
