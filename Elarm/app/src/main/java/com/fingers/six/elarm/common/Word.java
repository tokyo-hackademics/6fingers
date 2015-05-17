package com.fingers.six.elarm.common;

import com.fingers.six.elarm.utils.DateTimeUtils;

import org.joda.time.LocalDateTime;

/**
 * Created by Nghia on 5/16/2015.
 */
public class Word implements Comparable<Word> {
    private int _id;
    private String _jap;
    private String _eng;
    private int _lastAsked;
    private int _score;

    public Word() { }

    public Word(int id, String eng, String jap, int score, int date) {
        set_id(id);
        set_jap(jap);
        set_eng(eng);
        set_score(score);
        set_lastAsked(date);
    }

    public Word(String eng, String jap, int date){
        set_jap(jap);
        set_eng(eng);
        set_lastAsked(date);
    }

    public void subtractPoint() {
        int now = DateTimeUtils.convertToSeconds(new LocalDateTime());
        int gap = now - _lastAsked;
        if (gap < 60 * 5) set_score(_score - 150);
        else if (gap < 60 * 30) set_score(_score - 120);
        else if (gap < 60 * 60 * 6) set_score(_score - 70);
        else if (gap < 60 * 60 * 24) set_score(_score - 30);
        else if (gap < 60 * 60 * 24 * 7) set_score(_score - 10);
        else set_score(_score - 1);
    }

    public void increasePoint() {
        int now = DateTimeUtils.convertToSeconds(new LocalDateTime());
        int gap = now - _lastAsked;
        if (gap < 60 * 5) _score += 1;
        else if (gap < 60 * 30) _score += 10;
        else if (gap < 60 * 60 * 6) _score += 30;
        else if (gap < 60 * 60 * 24) _score += 70;
        else if (gap < 60 * 60 * 24 * 7) _score += 150;
        else set_score(900);
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
        if (_score < -300) _score = -300;
        else if (_score > 990) _score = 900;
        this._score = _score;
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

    public long get_lastAsked() {
        return _lastAsked;
    }

    public void set_lastAsked(int _lastAsked) {
        this._lastAsked = _lastAsked;
    }
}
