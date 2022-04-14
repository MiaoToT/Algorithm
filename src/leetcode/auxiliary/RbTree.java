package leetcode.auxiliary;

/**
 * 红黑树
 *
 * @author 喵粮都输光了
 * @date 2022/4/13 14:28
 */
public class RbTree<Key extends Comparable<Key>, Value> {

    /**
     * 根节点
     */
    private Node<Key, Value> root;
    /**
     * 数量
     */
    private int size = 0;
    /**
     * 红
     */
    private static final boolean RED = false;
    /**
     * 黑
     */
    private static final boolean BLACK = true;

    /**
     * 根据 key 获取 value
     *
     * @param key 键
     * @return 值
     */
    public Value get(Key key) {
        Node<Key, Value> node = this.getNode(key);
        return (node == null) ? null : node.value;
    }

    /**
     * 插入结点，找到同样的键覆盖值，没有找到则需新建结点挂入树中。同时更新挂入结点及其所有双亲结点的大小
     *
     * @param key   键
     * @param value 值
     */
    public void put(Key key, Value value) {
        Node<Key, Value> node = this.root;
        if (node == null) {
            this.root = new Node<>(key, value, null);
            return;
        }
        Node<Key, Value> parent;
        int cmp;
        do {
            parent = node;
            cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                node.value = value;
                return;
            }
        } while (node != null);
        Node<Key, Value> newNode = new Node<>(key, value, parent);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        ++this.size;
        this.fixAfterInsertion(newNode);
    }

    /**
     * 删除对应的 key
     *
     * @param key 键
     * @return 删除 key 对应的值
     */
    public Value remove(Key key) {
        Node<Key, Value> node = this.getNode(key);
        if (node == null) {
            return null;
        }
        Value oldValue = node.value;
        this.removeNode(node);
        return oldValue;
    }

    /**
     * 获取树中节点个数
     *
     * @return 节点个数
     */
    public int getSize() {
        return this.size;
    }

    /**
     * 删除指定节点
     *
     * @param node 节点
     */
    private void removeNode(Node<Key, Value> node) {
        --this.size;
        if (node.left != null && node.right != null) {
            // 如果当前节点有两个孩子，则用当前节点地后继节点覆盖当前节点。
            Node<Key, Value> successor = this.successor(node);
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }
        // 左节点存在用左节点代替否则用右节点，替代当前节点
        Node<Key, Value> replacement = (node.left != null) ? node.left : node.right;
        if (replacement != null) {
            // 重新链接
            replacement.parent = node.parent;
            if (node.parent == null) {
                this.root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            node.left = node.right = node.parent = null;
            if (node.color == BLACK) {
                this.fixAfterInsertion(replacement);
            }
        } else if (node.parent == null) {
            // 当前节点就是根节点
            this.root = null;
        } else {
            // 左、右节点都不存在
            if (node.color == BLACK) {
                // 删除的是黑色节点重新维持平衡
                this.fixAfterDeletion(node);
            }
            // 如果有父节点重新链接
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else if (node == node.parent.right) {
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }
    }

    /**
     * 查找后继节点
     *
     * @param node 节点
     * @return node 的后继节点
     */
    private Node<Key, Value> successor(Node<Key, Value> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            // 找右孩子的最左节点
            Node<Key, Value> successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            return successor;
        } else {
            // 如果没有右孩子，判断当前节点是父节点的左孩子还是右孩子
            // 左孩子：当前节点的父节点是后继节点
            // 右孩子：最左上节点的父节点是后继节点，也就是当前节点不再是父节点的右节点时，该父节点就是后继节点
            Node<Key, Value> parent = node.parent;
            Node<Key, Value> temp = node;
            while (parent != null && temp == parent.right) {
                temp = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 根据 key 获取 对应节点
     *
     * @param key 键
     * @return 节点
     */
    private Node<Key, Value> getNode(Key key) {
        Node<Key, Value> node = this.root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 修复插入后可能导致的不平衡
     *
     * @param node 新插入节点
     */
    private void fixAfterInsertion(Node<Key, Value> node) {
        this.setColor(node, RED);
        while (node != this.root && this.colorOf(this.parentOf(node)) == RED) {
            if (this.parentOf(node) == this.leftOf(this.parentOf(this.parentOf(node)))) {
                // 父节点是祖先节点的左节点
                Node<Key, Value> uncle = this.rightOf(this.parentOf(this.parentOf(node)));
                if (this.colorOf(uncle) == RED) {
                    // 父节点红，叔叔节点红的情况
                    // 将父、叔叔节点变黑，祖先变红
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(uncle, BLACK);
                    this.setColor(this.parentOf(this.parentOf(node)), RED);
                    // 继续调整祖先节点
                    node = this.parentOf(this.parentOf(node));
                } else {
                    // 父节点红，叔叔节点黑的情况
                    // 当前节点是父节点的右孩子
                    if (node == this.rightOf(this.parentOf(node))) {
                        // 左旋父节点(旋最高那个节点)
                        node = this.parentOf(node);
                        this.rotateLeft(node);
                    }
                    // 当前节点是父节点的左孩子
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(this.parentOf(this.parentOf(node)), RED);
                    // 旋最高那个节点
                    this.rotateRight(this.parentOf(this.parentOf(node)));
                }
            } else {
                // 父节点是祖先节点的右节点，与祖先节点的左节点相同，left和right反转
                Node<Key, Value> uncle = this.leftOf(this.parentOf(this.parentOf(node)));
                if (this.colorOf(uncle) == RED) {
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(uncle, BLACK);
                    this.setColor(this.parentOf(this.parentOf(node)), RED);
                    node = this.parentOf(this.parentOf(node));
                } else {
                    if (node == this.leftOf(this.parentOf(node))) {
                        node = this.parentOf(node);
                        this.rotateRight(node);
                    }
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(this.parentOf(this.parentOf(node)), RED);
                    this.rotateLeft(this.parentOf(this.parentOf(node)));
                }
            }
        }
        this.setColor(this.root, BLACK);
    }

    /**
     * 修复删除后可能导致的不平衡
     *
     * @param node 删除的节点或者是替换的节点
     */
    private void fixAfterDeletion(Node<Key, Value> node) {
        while (node != this.root && this.colorOf(node) == BLACK) {
            if (node == this.leftOf(this.parentOf(node))) {
                // 当前节点是父节点的左节点
                Node<Key, Value> sib = this.rightOf(this.parentOf(node));
                if (this.colorOf(sib) == RED) {
                    // 兄弟节点是红色
                    // 兄弟节点变黑，父节点变红，左旋(黑往红旋转，旋转最高点)
                    this.setColor(sib, BLACK);
                    this.setColor(this.parentOf(node), RED);
                    this.rotateLeft(this.parentOf(node));
                    // 继续调整兄弟节点
                    sib = this.rightOf(this.parentOf(node));
                }
                if (this.colorOf(this.leftOf(sib)) == BLACK && this.colorOf(this.rightOf(sib)) == BLACK) {
                    // 兄弟节点的孩子均为黑色
                    this.setColor(sib, RED);
                    node = this.parentOf(node);
                } else {
                    // 兄弟节点的右节点是黑色
                    if (this.colorOf(this.rightOf(sib)) == BLACK) {
                        // 兄弟节点的孩子节点都变黑，兄弟节点变红
                        this.setColor(this.leftOf(sib), BLACK);
                        this.setColor(sib, RED);
                        // 黑往红旋转，旋转最高点
                        this.rotateRight(sib);
                        sib = this.rightOf(this.parentOf(node));
                    }
                    // 兄弟节点的右节点是红色
                    // 兄弟节点颜色变成父节点颜色，父节点颜色变成黑色，兄弟节点的右孩子节点变成黑色
                    this.setColor(sib, this.colorOf(this.parentOf(node)));
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(this.rightOf(sib), BLACK);
                    // 涉及的节点都在右边，只能左旋最高点
                    this.rotateLeft(this.parentOf(node));
                    node = this.root;
                }
            } else {
                // 当前节点是父节点的右边节点
                Node<Key, Value> sib = this.leftOf(this.parentOf(node));
                if (this.colorOf(sib) == RED) {
                    this.setColor(sib, BLACK);
                    this.setColor(this.parentOf(node), RED);
                    this.rotateRight(this.parentOf(node));
                    sib = this.leftOf(this.parentOf(node));
                }
                if (this.colorOf(this.rightOf(sib)) == BLACK && this.colorOf(this.leftOf(sib)) == BLACK) {
                    this.setColor(sib, RED);
                    node = this.parentOf(node);
                } else {
                    if (this.colorOf(this.leftOf(sib)) == BLACK) {
                        this.setColor(this.rightOf(sib), BLACK);
                        this.setColor(sib, RED);
                        this.rotateLeft(sib);
                        sib = this.leftOf(this.parentOf(node));
                    }
                    this.setColor(sib, this.colorOf(this.parentOf(node)));
                    this.setColor(this.parentOf(node), BLACK);
                    this.setColor(this.leftOf(sib), BLACK);
                    this.rotateRight(this.parentOf(node));
                    node = this.root;
                }
            }
        }
        this.setColor(node, BLACK);
    }

    /**
     * 左旋调整：N变成原来parent的位置，parent变成N的左节点，N的左节点变成parent的右节点。
     *
     * @param parent 旋转节点(插入节点的父节点)
     */
    private void rotateLeft(Node<Key, Value> parent) {
        if (parent == null) {
            return;
        }
        Node<Key, Value> node = parent.right;
        // 设置父节点的右节点，即 n 的左节点变成 parent 的右节点
        parent.right = node.left;
        if (node.left != null) {
            node.left.parent = parent;
        }
        // 设置 n 的父节点，即 n 的父节点变成原来的祖先节点
        node.parent = parent.parent;
        if (parent.parent == null) {
            // 处理可能变成了根节点
            this.root = node;
        } else if (parent.parent.left == parent) {
            // 如果是祖先节点的左节点
            parent.parent.left = node;
        } else {
            // 如果是祖先节点的右节点
            parent.parent.right = node;
        }
        // 设置 n 的左节点
        node.left = parent;
        parent.parent = node;
    }

    /**
     * 右旋调整：与左旋调整相同，left和right反转
     *
     * @param parent 旋转节点
     */
    private void rotateRight(Node<Key, Value> parent) {
        if (parent == null) {
            return;
        }
        Node<Key, Value> node = parent.left;
        parent.left = node.right;
        if (node.right != null) {
            node.right.parent = parent;
        }
        node.parent = parent.parent;
        if (parent.parent == null) {
            this.root = node;
        } else if (parent.parent.right == parent) {
            parent.parent.right = node;
        } else {
            parent.parent.left = node;
        }
        node.right = parent;
        parent.parent = node;
    }

    /**
     * 获取父节点
     *
     * @param node 节点
     * @return 父节点
     */
    private Node<Key, Value> parentOf(Node<Key, Value> node) {
        return (node == null) ? null : node.parent;
    }

    /**
     * 获取左节点
     *
     * @param node 节点
     * @return 左节点
     */
    private Node<Key, Value> leftOf(Node<Key, Value> node) {
        return (node == null) ? null : node.left;
    }

    /**
     * 获取右节点
     *
     * @param node 节点
     * @return 右节点
     */
    private Node<Key, Value> rightOf(Node<Key, Value> node) {
        return (node == null) ? null : node.right;
    }

    /**
     * 获取节点颜色
     *
     * @param node 节点
     * @return 节点颜色
     */
    private boolean colorOf(Node<Key, Value> node) {
        return (node == null ? BLACK : node.color);
    }

    /**
     * 设置节点颜色
     *
     * @param node  节点
     * @param color 颜色
     */
    private void setColor(Node<Key, Value> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private static final class Node<Key, Value> {

        /**
         * 键
         */
        Key key;
        /**
         * 值
         */
        Value value;
        /**
         * 左结点
         */
        Node<Key, Value> left;
        /**
         * 右结点
         */
        Node<Key, Value> right;
        /**
         * 父节点
         */
        Node<Key, Value> parent;
        /**
         * 红黑树判断
         */
        boolean color = BLACK;

        public Node(Key key, Value value, Node<Key, Value> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

    }

}
