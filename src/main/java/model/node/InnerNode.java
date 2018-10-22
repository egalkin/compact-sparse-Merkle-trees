package model.node;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class InnerNode<H> extends Node<H> {
    Node<H> left;
    Node<H> right;

    public InnerNode(@NotNull H hash, @NotNull Node<H> left, @NotNull Node<H> right) {
        super(Math.max(left.getKey(), right.getKey()), hash);
        this.left = left;
        this.right = right;
    }
}