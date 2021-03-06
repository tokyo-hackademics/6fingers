package com.fingers.six.elarm.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeSet;

public class QuestionList {
    private int _id;
    private int _lastId;
    private String _name;
    private String _dbName;
    private ArrayList<Word> _wordList;
    private int[] _status;

    public QuestionList(int id, String name) {
        set_id(id);
        set_name(name);
        while (name.contains(" ")) name = name.replace(" ", "");
        set_dbName(name);
        set_wordList(new ArrayList<Word>());
        set_status(new int[3]);
        set_lastId(0);
    }

    public void addNewWord(int id, String eng, String jap, int score, int date) throws ParseException {
        _wordList.add(new Word(id, eng, jap, score, date));
    }

    public void removeWord(int id) {
        for (Word w : _wordList) {
            if (w.get_id() == id) {
                _wordList.remove(w);
                return;
            }
        }
    }

    public void updateWord(int id, String eng, String jap) {
        for (Word w : _wordList) {
            if (w.get_id() == id) {
                w.set_eng(eng);
                w.set_jap(jap);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Word w : _wordList) {
            sb.append(w.toString()).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public ArrayList<Word> get_wordList() {
        return _wordList;
    }

    public void set_wordList(ArrayList<Word> _wordList) {
        this._wordList = _wordList;
    }

    public int[] get_status() {
        return _status;
    }

    public void set_status(int[] _status) {
        this._status = _status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_lastId() {
        return _lastId;
    }

    public void set_lastId(int _lastId) {
        this._lastId = _lastId;
    }

    public String get_dbName() {
        return _dbName;
    }

    public void set_dbName(String _dbName) {
        this._dbName = _dbName;
    }
}
