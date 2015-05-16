package com.fingers.six.elarm.common;

/**
 * Created by Nghia on 5/16/2015.
 */
public class HistoryItem {
    private int _id;
    private long _date;
    private int _qid;
    private String _questionName;
    private int _score;

    public HistoryItem() {}

    public HistoryItem(long date, int qid, String qName, int score) {
        _date = date;
        _qid = qid;
        _questionName = qName;
        _score = score;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public long get_date() {
        return _date;
    }

    public void set_date(long _date) {
        this._date = _date;
    }

    public int get_qid() {
        return _qid;
    }

    public void set_qid(int _qid) {
        this._qid = _qid;
    }

    public String get_questionName() {
        return _questionName;
    }

    public void set_questionName(String _questionName) {
        this._questionName = _questionName;
    }

    public double get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }
}
