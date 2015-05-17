package com.fingers.six.elarm.common;

/**
 * Created by Nghia on 5/16/2015.
 */
public class QuestionItem {
    private Word _word;
    private boolean _isEngToJap;
    private String[] _answers;
    private String _userAnswer = "";

    public boolean isGoodAnswer() {
        return _userAnswer.equals(_isEngToJap ? _word.get_jap() : _word.get_eng());
    }

    public Word get_word() {
        return _word;
    }

    public void set_word(Word _word) {
        this._word = _word;
    }

    public String[] get_answers() {
        return _answers;
    }

    public void set_answers(String[] _answers) {
        this._answers = _answers;
    }

    public String get_userAnswer() {
        return _userAnswer;
    }

    public void set_userAnswer(String _userAnswer) {
        this._userAnswer = _userAnswer;
    }

    public boolean is_isEngToJap() {
        return _isEngToJap;
    }

    public void set_isEngToJap(boolean _isEngToJap) {
        this._isEngToJap = _isEngToJap;
    }
}
