package org.eggs.nodes;

import org.joda.time.DateTime;
import org.parboiled.trees.TreeNode;

import java.util.Collections;
import java.util.List;

public class Node implements TreeNode<Node> {

    private final NodeType nodeType;
    private Node parent;
    private final List<Node> children;

    public Node(NodeType nodeType, Node parent, List<Node> children) {
        this.nodeType = nodeType;
        this.parent = parent;
        this.children = Collections.unmodifiableList(children == null ? Collections.<Node>emptyList() : children);
    }

    public Node(NodeType nodeType) {
        this(nodeType, null, null);
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     * Calculate a date
     * @return
     */
    public DateTime getDate() {
        return new DateTime();
    }
}
