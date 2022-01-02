package org.school.diary.utils;


public class Index {

    private int idx;

    public Index(int startFrom) {
        this.idx = startFrom;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void clear(){
        idx = 0;
    }

    public int getAndIncrement(){
        return idx++;
    }


}
