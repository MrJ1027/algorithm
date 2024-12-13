package com.jtl.class35;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

/**
 * @author Mr.J
 * @time 2024/11/18 12:04
 * @Version 1.0
 * @description 请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
 */
public class Code07_AllO1 {

    public static void main(String[] args) {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("hello");
        System.out.println(allOne.getMaxKey());
        System.out.println(allOne.getMinKey());
        allOne.dec("hello");
        allOne.dec("hello");
        System.out.println(allOne.getMaxKey());
        allOne.inc("hello");
        System.out.println(allOne.getMinKey());

    }

    //双向链表的节点
    static class Bucket{
        int cnt;//词频
        HashSet<String> set;//存放字符串的set
        Bucket last;//前一个节点
        Bucket next;//后一个节点
        Bucket(int cnt,String str){
            this.cnt = cnt;
            this.set = new HashSet<>();
            this.set.add(str);
            this.last = null;
            this.next = null;
        }
    }

    //双向链表
    static class DoubleLinkedList{

        Bucket head;
        Bucket tail;

        DoubleLinkedList(){
            this.head = new Bucket(0,"");
            this.tail = new Bucket(Integer.MAX_VALUE,"");
            this.head.next = this.tail;
            this.tail.last = this.head;
            this.head.last = null;
            this.tail.next = null;
        }


    }

    static class AllOne {
        //双向链表
        DoubleLinkedList doubleLinkedList;
        //key:字符串;value:字符串所在的桶
        HashMap<String,Bucket> map;

        public AllOne() {
            doubleLinkedList = new DoubleLinkedList();
            map = new HashMap<>();
        }

        //字符串 key 的计数增加 1 。
        //如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
        public void inc(String key) {
            //如果字符串key有桶,放到下一个位置
            if (map.containsKey(key)) {
                Bucket bucket = map.get(key);
                //如果下一个桶是递增的,直接加入
                if(bucket.next.cnt == bucket.cnt + 1){
                    bucket.next.set.add(key);
                }else{
                    //否则新建并插入双向链表
                    Bucket newBucket = new Bucket(bucket.cnt + 1,key);
                    bucket.next.last = newBucket;
                    newBucket.next = bucket.next;
                    bucket.next = newBucket;
                    newBucket.last = bucket;
                }
                //更新哈希表
                map.put(key,bucket.next);
                //最后把key从原桶中删除,如果删完后bucket为空,直接从链表中删除
                bucket.set.remove(key);
                if(bucket.set.isEmpty()){
                    bucket.last.next = bucket.next;
                    bucket.next.last = bucket.last;
                }
            }else{
                //如果字符串key没桶,直接往0后面看即可
                //如果频率为1的桶已经存在,直接加入key
                Bucket oneBucket = doubleLinkedList.head.next;
                if (oneBucket.cnt == 1) {
                    oneBucket.set.add(key);
                }else{
                    //如果频率为1的桶不存在,创建后加入key
                    oneBucket = new Bucket(1,key);
                    doubleLinkedList.head.next.last = oneBucket;
                    oneBucket.next = doubleLinkedList.head.next;
                    doubleLinkedList.head.next = oneBucket;
                    oneBucket.last = doubleLinkedList.head;
                }
                //更新哈希表
                map.put(key,oneBucket);
            }
        }

        //字符串 key 的计数减少 1 。
        //如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。
        public void dec(String key) {
            //拿到要删除的桶
            Bucket removeBucket = map.get(key);
            removeBucket.set.remove(key);
            //在桶的set中删除key后桶不为空,桶不删除
            //如果有前一个桶,直接加入
            if (removeBucket.last.cnt == removeBucket.cnt - 1) {
                removeBucket.last.set.add(key);
                map.put(key,removeBucket.last);
            }else{
                //否则,创建后加入
                //TODO 方法可以封装到双向链表里面,不知道会不会更快?
                Bucket newBucket = new Bucket(removeBucket.cnt - 1,key);
                newBucket.next = removeBucket;
                newBucket.last = removeBucket.last;
                removeBucket.last.next = newBucket;
                removeBucket.last = newBucket;
                map.put(key,newBucket);
            }
            if(removeBucket.set.isEmpty()){
                //如果该桶删除key后桶为空,把桶删除
                removeBucket.last.next = removeBucket.next;
                removeBucket.next.last = removeBucket.last;
            }
        }

        public String getMinKey() {
            return doubleLinkedList.head.next.set.iterator().next();
        }

        public String getMaxKey() {
            return doubleLinkedList.tail.last.set.iterator().next();
        }
    }

}
