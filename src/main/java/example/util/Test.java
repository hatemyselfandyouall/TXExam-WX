package example.util;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by YS-GZD-1495 on 2018/2/27.
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList=new ArrayList<>();
        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(5);
        arrayList.add(11);
        arrayList.add(15);
        LinkedList linkedList=new LinkedList();
        linkedList.add(11);
        linkedList.add(5);
        linkedList.add(3);
        arrayList.removeAll(linkedList);
    }
}
