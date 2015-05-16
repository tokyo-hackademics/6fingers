package com.fingers.six.elarm.utils;

import com.fingers.six.elarm.common.QuestionItem;
import com.fingers.six.elarm.common.QuestionList;
import com.fingers.six.elarm.common.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {

    public static int grade(QuestionItem[] qq) {
        int cnt = 0;
        for (QuestionItem q : qq) {
            cnt += q.isGoodAnswer() ? 1 : 0;
        }
        return cnt;
    }

    public static QuestionItem generateQuickQuestion(QuestionList questionList) {
        boolean isEngToJap = RandomUtils.next(2) == 0;
        int threshold = RandomUtils.next(1000);
        QuestionItem ret;
        do {
            ret = generateTest(isEngToJap, 1, questionList)[0];
        } while (ret.get_word().get_score() >= threshold);
        return ret;
    }

    public static QuestionItem[] generateTest(boolean isEngToJap, int numberOfQuestions, QuestionList questionList) {
        Object[] allAns = getAllPossibleAns(isEngToJap, questionList).toArray();
        Object[] ww = questionList.get_wordList().toArray();

        QuestionItem[] qq = new QuestionItem[numberOfQuestions];
        for (int i = 0; i < numberOfQuestions; ++i) {
            qq[i] = new QuestionItem();
            qq[i].set_word((Word) ww[RandomUtils.next(ww.length)]);
            qq[i].set_isEngToJap(isEngToJap);
            String[] ans = new String[4];
            int r = RandomUtils.next(4);
            ans[r] = isEngToJap ? qq[i].get_word().get_jap() : qq[i].get_word().get_eng();
            Set<String> s = new HashSet<>();
            s.add(ans[r]);
            for (int j = 0; j < 4; ++j) {
                if (j == r) continue;
                String t;
                do {
                    t = (String) allAns[RandomUtils.next(allAns.length)];
                } while (s.contains(t));
                ans[j] = t;
                s.add(t);
            }
            qq[i].set_answers(ans);
        }

        return qq;
    }

    private static ArrayList<String> getAllPossibleAns(boolean isEngToJap, QuestionList qlist) {
        ArrayList<String> ret = new ArrayList<>();
        for (Word w : qlist.get_wordList()) {
            ret.add(isEngToJap ? w.get_jap() : w.get_eng());
        }
        return ret;
    }
}
