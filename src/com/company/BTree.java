package com.company;

import java.io.Serializable;
import java.util.*;

public class BTree<Key extends Comparable> implements Serializable
{
    public BTree(int degree)
    {
        if (degree <= 0)
        {
            throw new IllegalArgumentException("Degree must be greater 0!");
        }

        _root = new Node();
        _root.children.add(null);

        _degree = degree;
        _size = 0;
    }

    public void insert(Key key)
    {
        Pair result = _insert(_root, key);

        if (result == null) return;

         
        Node newRoot = new Node();

        newRoot.keys.add(result.key);

         
        newRoot.children.add(_root);

         
        newRoot.children.add(result.node);

        _root = newRoot;
    }

    public boolean contains(Key key)
    {
        return _contains(_root, key);
    }

    public void erase(Key key)
    {
        _erase(_root, key, null, 0);
    }

    public void clear()
    {
         
        _root = new Node();
        _root.children.add(null);

        _size = 0;
    }

    public Key minimum()
    {
        return _minimum(_root).firstKey();
    }

    public Key maximum()
    {
        return _maximum(_root).lastKey();
    }

    public int size()
    {
        return _size;
    }

    public boolean isEmpty()
    {
        return _size == 0;
    }

    Iterator<Key> iterator()
    {
        return keys().iterator();
    }

    Iterable<Key> keys()
    {
        ArrayList<Key> keys = new ArrayList<>();

        _collect(_root, keys);

        return keys;
    }

    private static int _lowerBound(List<? extends Comparable> sequence,
                                   int i,
                                   int j,
                                   Comparable target)
    {
        if (i >= j) return j;

        int middle = i + (j - i)/2;

        int comparison = target.compareTo(sequence.get(middle));

        if (comparison < 0)
        {
            return _lowerBound(sequence, i, middle, target);
        }

        else if (comparison > 0)
        {
            return _lowerBound(sequence, ++middle, j, target);
        }

        else return middle;
    }

    private class Node implements Serializable
    {
        public Node()
        {
             
             
             
            this.keys = new ArrayList<>(2 * _degree + 1);
            this.children = new ArrayList<>(2 * _degree + 2);
        }

        public Pair split()
        {
            Node right = new Node();

            int middle = size() / 2;

             
            for (int i = size() - 1; i > middle; --i)
            {
                right.keys.add(this.keys.remove(i));
                right.children.add(this.children.remove(i));
            }

             
            Collections.reverse(right.keys);
            Collections.reverse(right.children);

             
            right.children.add(this.children.remove(size()));

             
            return new Pair(this.keys.remove(middle), right);
        }

        public int size()
        {
            return keys.size();
        }

        public void insert(int index, Key key, Node child)
        {
            if (full()) throw new RuntimeException("Node is full!");

            keys.add(index, key);
            children.add(index + 1, child);

            assert(keys.size() + 1 == children.size());
        }

        public void add(Key key, Node child)
        {
            insert(size(), key, null);
        }

        public void merge(Node other)
        {
            this.keys.addAll(other.keys);
            this.children.addAll(other.children);
        }

        public Pair erase(int index)
        {
            assert(index >= 0 && index < size());

             
            Pair dead = new Pair(keys.get(index), children.get(index));

            keys.remove(index);
            children.remove(index);

            assert(keys.size() + 1 == children.size());

            return dead;
        }

        public Node leftChild(int index)
        {
            return (index >= 0) ? children.get(index) : null;
        }

        public Node rightChild(int index)
        {
            return (index < size()) ? children.get(index + 1) : null;
        }

        public boolean full()
        {
            return keys.size() > 2 * _degree;
        }

        public Key firstKey()
        {
            return keys.get(0);
        }

        public Key lastKey()
        {
            return keys.get(size() - 1);
        }

        public Node firstChild()
        {
            return children.get(0);
        }

        public Node lastChild()
        {
            return children.get(size());
        }

        public ArrayList<Key> keys;
        public ArrayList<Node> children;
    }

    private class Pair implements Serializable
    {
        public Pair(Key key, Node node)
        {
            this.key = key;
            this.node = node;
        }

        public Key key;
        public Node node;
    }

    private int _lowerBound(Node node, Key target)
    {
        return _lowerBound(node.keys, 0, node.size(), target);
    }

    private Pair _insert(Node node, Key key)
    {
        int index = _lowerBound(node, key);

        Node child = null;

         
        if (index < node.size() && node.keys.get(index).equals(key)) return null;

         
        if (node.children.get(index) != null)
        {
            Pair result = _insert(node.children.get(index), key);

            if (result == null) return null;

            key = result.key;
            child = result.node;
        }

         
        if (child == null) ++_size;

        node.insert(index, key, child);

        assert(node == _root || node.size() >= _degree);

        return node.full() ? node.split() : null;
    }

    private boolean _contains(Node node, Key key)
    {
        if (node == null) return false;

        int index = _lowerBound(node, key);

        if (index < node.size() && node.keys.get(index).equals(key)) return true;

        return _contains(node.children.get(index), key);
    }

    private Node _erase(Node node, Key key, Node parent, int parentIndex)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("Key not found!");
        }

        int index = _lowerBound(node, key);

         
        if (index < node.size() && node.keys.get(index).equals(key))
        {
             
            if (node.children.get(index) == null) node.erase(index);

            else node = _eraseInternalKey(node, index);

            --_size;
        }

        else node = _erase(node.children.get(index), key, node, index);

        return _balance(node, parent, parentIndex);
    }

    private Node _balance(Node node, Node parent, int parentIndex)
    {
        if (node == _root || node.size() >= _degree) return parent;

         
        Node left = parent.leftChild(parentIndex - 1);

         
        Node right = parent.rightChild(parentIndex);

         
        if (right != null && right.size() > _degree)
        {
             
             
            node.add(parent.keys.get(parentIndex), right.firstChild());

            parent.keys.set(parentIndex, right.erase(0).key);
        }

         
        else if (left != null && left.size() > _degree)
        {
            node.keys.add(0, parent.keys.get(parentIndex - 1));
            node.children.add(0, left.children.remove(left.size()));

            parent.keys.set(parentIndex - 1, left.keys.remove(left.size() - 1));
        }

         
        else if (left != null)
        {
            left.keys.add(parent.keys.get(parentIndex - 1));

            left.merge(node);

             
             
             
            parent.children.set(parentIndex, left);

            parent.erase(parentIndex - 1);
        }

        else if (right != null)
        {
            node.keys.add(parent.keys.get(parentIndex));

            node.merge(right);

             
            parent.children.set(parentIndex + 1, node);

            parent.erase(parentIndex);
        }

        if (parent.size() == 0)
        {
            if (_root == parent) _root = node;

            parent = node;
        }

        assert(node == _root || node.size() >= _degree);

        return parent;
    }

    private Node _eraseInternalKey(Node node, int index)
    {
        Node left = node.leftChild(index);
        Node right = node.rightChild(index);

        if (left != null && left.size() > _degree)
        {
            Node successor = _maximum(left);

             
            node.keys.set(index, successor.erase(successor.size() - 1).key);
        }

        else if (right != null && right.size() > _degree)
        {
            Node successor = _minimum(right);

             
            node.keys.set(index, successor.erase(0).key);
        }

        else if (left != null)
        {
             
            if (right != null)
            {
                if (right.lastChild() == null) right.children.remove(right.size());

                else if (left.firstChild() == null) left.children.remove(0);

                else
                {
                    Node successor = _minimum(right);

                     
                    node.keys.set(index, successor.firstKey());

                     
                    successor.children.set(0, null);

                    return node;
                }
            }

            left.merge(right);

             
             
            if (node.size() == 1)
            {
                 
                 
                _root = left;

                return left;
            }

             
             
            node.children.set(index + 1, left);

            node.erase(index);
        }

         
        else node.erase(index);

        return node;
    }

    private Node _minimum(Node node)
    {
        while (node.firstChild() != null)
        {
            node = node.firstChild();
        }

        return node;
    }

    private Node _maximum(Node node)
    {
        while (node.lastChild() != null)
        {
            node = node.lastChild();
        }

        return node;
    }

    private void _collect(Node node, List<Key> keys)
    {
         
        boolean isLeaf = node.firstChild() == null;

        for (int i = 0; i < node.size(); ++i)
        {
            if (! isLeaf) _collect(node.children.get(i), keys);

            keys.add(node.keys.get(i));
        }

        if (! isLeaf) _collect(node.lastChild(), keys);
    }

    private Node _root;

    private final int _degree;

    private int _size;
}