package com.main.mp1.openmiba;

import java.util.ArrayList;
import java.util.List;

public class ImageIndexTable {
    public static final int NEEDEDPICTURES = 512;
    public static final int PICTURELIMIT = 30;
    public static int m = 521;
    List<Integer> indexedpos = new ArrayList();
    boolean[] isused = new boolean[m];

    public int getIndex(int pos) {
        int pos2 = (pos % 30) + ((this.indexedpos.size() / 30) * 30);
        if (pos2 > m - 1) {
            return 0;
        }
        if (this.isused[pos2]) {
            return getIndex(pos2, 1);
        }
        this.isused[pos2] = true;
        this.indexedpos.add(Integer.valueOf(pos2));
        return pos2 % 30;
    }

    /* access modifiers changed from: package-private */
    public void initArray(boolean[] a, boolean init) {
        for (int i = 0; i < a.length; i++) {
            a[i] = false;
        }
    }

    /* access modifiers changed from: protected */
    public int getIndex(int pos, int i) {
        int temp = ((pos + i) % 30) + ((this.indexedpos.size() / 30) * 30);
        if (this.isused[temp]) {
            return getIndex(pos, i + 1);
        }
        this.isused[temp] = true;
        this.indexedpos.add(Integer.valueOf(temp));
        return temp % 30;
    }

    public void removeLastIndex() {
        int end;
        if (this.indexedpos.size() == 1) {
            end = 1;
        } else if (this.indexedpos.size() == 0) {
            end = 0;
        } else {
            end = 2;
        }
        for (int i = 0; i < end; i++) {
            this.isused[this.indexedpos.get(this.indexedpos.size() - 1).intValue()] = false;
            this.indexedpos.remove(this.indexedpos.size() - 1);
        }
    }

}
