package com.algorithm.leetcode.structs;

class DLinkedNode {
    public int key;
    public int value;
    public DLinkedNode prev;
    public DLinkedNode next;

    public DLinkedNode() {
    }

    public DLinkedNode(int _key, int _value) {
        key = _key;
        value = _value;
    }
}