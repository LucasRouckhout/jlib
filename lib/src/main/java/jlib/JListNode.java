package jlib;

import lombok.Data;
import lombok.NonNull;

@Data
public class JListNode<T> {

    private T data;
    private JListNode<T> next;
    private JListNode<T> previous;

    public JListNode(@NonNull final T data) {
        this.data = data;
        this.next = this.previous = null;
    }
}
