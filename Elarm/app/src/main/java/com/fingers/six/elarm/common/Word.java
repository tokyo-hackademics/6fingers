package com.fingers.six.elarm.common;

import java.text.ParseException;

/**
 * Created by Nghia on 5/16/2015.
 */
public class Word implements Comparable<Word> {
    private int _id;
    private String _jap;
    private String _eng;
    private long _lastCorrected;
    private int _score;

    public Word() {
        set_id(0);
        set_jap("");
        set_eng("");
        set_score(0);
    }

    public Word(int id, String eng, String jap, int score, long date) throws ParseException {
        set_id(id);
        set_jap(jap);
        set_eng(eng);
        set_score(score);
        set_lastCorrected(date);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_jap() {
        return _jap;
    }

    public void set_jap(String _jap) {
        this._jap = _jap;
    }

    public String get_eng() {
        return _eng;
    }

    public void set_eng(String _eng) {
        this._eng = _eng;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = Math.max(_score, 990);
    }

    @Override
    public int compareTo(Word another) {
        return this._eng.compareTo(another.get_eng());
    }

    public boolean equals(Word another) {
        return this._eng.equals(another.get_eng()) || this._jap.equals(another.get_jap());
    }

    public String toString() {
        return _id + "|" + _jap + "|" + _eng + "|" + _score;
    }

    public long get_lastCorrected() {
        return _lastCorrected;
    }

    public void set_lastCorrected(long _lastCorrected) {
        this._lastCorrected = _lastCorrected;
    }
}
