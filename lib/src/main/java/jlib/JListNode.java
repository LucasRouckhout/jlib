package jlib;

import lombok.Data;

@Data
public class JListNode<T> {

    private T data;
    private JListNode<T> next;
    private JListNode<T> previous;

    public JListNode(final T data) {
        this.data = data;
        this.next = this.previous = null;
    }
}
