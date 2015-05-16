package com.fingers.six.elarm.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Created by Nghia on 5/16/2015.
 */
public class TestManager {

    public static int grade(QuestionItem[] qq) {
        int cnt = 0;
        for (int i = 0; i < qq.length; ++i) {
            cnt += qq[i].isGoodAnswer() ? 1 : 0;
        }
        return cnt;
    }

    public static QuestionItem[] GenerateTest(boolean isEngToJap, int numberOfQuestions, QuestionList qlist) {
        Object[] allAns = getAllPossibleAns(isEngToJap, qlist).toArray();
        QuestionItem[] qq = new QuestionItem[numberOfQuestions];
        Object[] ww = qlist.get_wordList().toArray();

        for (int i = 0; i < numberOfQuestions; ++i) {
            qq[i] = new QuestionItem();
            qq[i].set_word((Word) ww[(int) (Math.random() * ww.length)]);
            qq[i].set_isEngToJap(isEngToJap);
            String[] ans = new String[4];
            int r = (int)(Math.random() * 4);
            ans[r] = isEngToJap ? qq[i].get_word().get_jap() : qq[i].get_word().get_eng();
            Set<String> s = new HashSet<String>();
            s.add(ans[r]);
            for (int j = 0; j < 4; ++j) {
                if (j == r) continue;
                String t;
                do {
                    t = (String)allAns[(int)(Math.random() * allAns.length)];
                } while (s.contains(t));
                ans[j] = t;
                s.add(t);
            }
            qq[i].set_answers(ans);
        }

        return qq;
    }

    private static ArrayList<String> getAllPossibleAns(boolean isEngToJap, QuestionList qlist) {
        ArrayList<String> ret = new ArrayList<String>();
        for (Word w : qlist.get_wordList()) {
            ret.add(isEngToJap ? w.get_jap() : w.get_eng());
        }
        return ret;
    }
}
