package com.wlb.forever.rpc.common.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: william
 * @Date: 18/11/21 11:16
 * @Description:
 */
public class RedBlackTree {

    /**
     *节点
     */
    public static class Node {
        public static final String RED = "red";
        public static final String BLACK = "black";

        public int value;
        public String color;
        public Node left;
        public Node right;
        public Node parent;


        public Node(int value) {
            this.value = value;
            initNode(value, Node.RED, null, null, parent);
        }


        public Node(int value, String color, Node left, Node right, Node parent) {
            initNode(value, color, left, right, parent);
        }

        private void initNode(int value, String color, Node left, Node right, Node parent) {
            this.value = value;
            this.color = color;
            this.left = left;
            this.right = right;
        }


        //获取父亲节点
        public Node getFather() {
            return this.parent;
        }

        //获取爸爸的兄弟节点
        public Node getUncle() {
            if (null == this.parent) {
                return null;
            }
            if (null == this.parent.parent) {
                return null;
            }
            if (this.parent == this.parent.parent.left) {
                return this.parent.parent.right;
            } else {
                return this.parent.parent.left;
            }
        }

        /***
         * 获取爷爷节点
         * @return
         */
        public Node getGrandFather() {
            if (null == this.parent){
                return null;
            }
            return this.parent.parent;
        }

        /***
         * 获取兄弟节点
         * @return
         */
        public Node getBro() {
            if (null == this.parent) {
                return null;
            }

            if (value == this.parent.left.value) {
                return this.parent.right;
            } else {
                return this.parent.left;
            }

        }


    }


    private Node root;
    private int size;


    public RedBlackTree(Node root) {
        setBlack(root);
        this.root = root;
    }

    public RedBlackTree() {
    }


    /***
     * 设置节点为黑色
     * @param node
     */
    private void setBlack(Node node) {
        if (null != node) {
            node.color = Node.BLACK;
        }
    }

    private void setRed(Node node) {
        if (null != node) {
            node.color = Node.RED;
        }
    }


    /***
     * 从父节点及其子节点中找value值
     * @param father
     * @param value
     * @return
     */
    private Node findValue(Node father, int value) {
        while (father != null) {
            if (value > father.value) {
                father = father.right;
            } else if (value < father.value) {
                father = father.left;
            } else {
                return father;
            }
        }
        return null;
    }

    /***
     * 只有插入操作，只执行插入
     * @param value 插入的值
     * @return 插入完成后返回插入节点
     */
    private Node doInsert(int value) {
        Node insertNode = new Node(value);
        Node father = root;
        Node tempFather = null;
        while (father != null) {
            tempFather = father;
            if (value < father.value) {
                father = father.left;
            } else {
                father = father.right;
            }
        }
        if (value < tempFather.value) {
            tempFather.left = insertNode;
        } else {
            tempFather.right = insertNode;
        }
        insertNode.parent = tempFather;
        return insertNode;
    }


    /***
     * 对外开放insert
     * @param value
     * @throws Exception
     */
    public void insert(Integer value) throws Exception {
        //验证root初始化
        if (null == root) {
            Node node = new Node(value);
            setBlack(node);
            this.root = node;
            return;
        }

        //验证是否已插入节点
        if (null != findValue(root, value)) {
            throw new Exception("不允许重复插入值");
        }

        Node insertNode = doInsert(value);
        rebuildInsertCore(insertNode);
    }


    /***
     * 右旋
     * 右右类型传入父亲节点
     * 左右类型传入儿子节点
     * @param currentNode 传入的永远是要变成爸爸的儿子
     * @Return node 返回最新的爸爸
     */
    private Node rightRotate(Node currentNode) {
        //获取旋转前角色
        Node grandFather = currentNode.getGrandFather();
        Node father = currentNode.getFather();
        //双方丢失的节点重新绑定
        father.left = currentNode.right;

        //绑定爷爷和父亲的角色
        if (null != grandFather) {
            if (father == grandFather.left) {
                grandFather.left = currentNode;
            } else {
                grandFather.right = currentNode;
            }
            currentNode.parent = grandFather;
        } else {  //替换root节点
            this.root = currentNode;
            currentNode.parent = null;
        }


        //儿子与父节点的关联
        currentNode.right = father;
        father.parent = currentNode;
        return currentNode;
    }


    /****
     * 左旋
     * 右右类型传入父亲节点
     * 左右类型传入儿子节点
     * @param currentNode 传入的永远是要变成爸爸的儿子，
     * @Return node 返回最新的爸爸
     */
    private Node leftRotate(Node currentNode) {
        //获取旋转前角色
        Node grandFather = currentNode.getGrandFather();
        Node father = currentNode.getFather();

        //双方丢失的节点重新绑定
        father.right = currentNode.left;
        //绑定爷爷和父亲的角色
        if (null != grandFather) {    //到root节点，则重新赋值root
            if (father == grandFather.left) {
                grandFather.left = currentNode;
            } else {
                grandFather.right = currentNode;
            }
            currentNode.parent = grandFather;
        } else {
            root = currentNode;
            currentNode.parent = null;
        }
        //儿子与父节点的关联
        currentNode.left = father;
        father.parent = currentNode;
        return currentNode;
    }


    /****
     * 红黑黑类型
     * @param colorNode 传入固定时间的父亲节点
     */
    private void redBlackBlack(Node colorNode) {
        setRed(colorNode);
        setBlack(colorNode.left);
        setBlack(colorNode.right);
    }


    /*****
     * 黑红红类型
     * @param colorNode 传入固定时间的父亲节点
     */
    private void blackRedRed(Node colorNode) {
        setBlack(colorNode);
        setRed(colorNode.left);
        setRed(colorNode.right);
    }


    /****
     * 插入之后重新构建红黑树
     * @param insertNode
     */
    private void rebuildInsertCore(Node insertNode) {
        while (null != insertNode.getFather() && insertNode.getFather().color == Node.RED && insertNode.color == Node.RED) {
            if (null != insertNode.getFather() && insertNode.getFather().color == Node.RED && null != insertNode.getUncle() && insertNode.getUncle().color == Node.RED) {
                //爷爷红，叔叔爸爸黑，引用节点为爷爷，继续向上查找
                redBlackBlack(insertNode.getGrandFather());
                insertNode = insertNode.getGrandFather();
            } else {
                //父节点在爷爷左边
                if (null != insertNode.getGrandFather() && insertNode.getGrandFather().left == insertNode.getFather()) { //左
                    if (insertNode == insertNode.getFather().left) {      //左
                        //左左右旋
                        insertNode = rightRotate(insertNode.getFather());
                    } else {              //右
                        //先左旋，再右旋
                        insertNode = leftRotate(insertNode);
                        insertNode = rightRotate(insertNode);
                    }
                } else {  //父节点在爷爷右边                              //右
                    if (null != insertNode.getGrandFather() && insertNode == insertNode.getFather().left) {      //左
                        insertNode = rightRotate(insertNode);   //注意这里传入的方法，永远是要变成爸爸的儿子。返回的是新爸爸
                        insertNode = leftRotate(insertNode);
                    } else {              //右
                        insertNode = leftRotate(insertNode.getFather());
                    }
                }
                //旋转完成，重新染色！！
                blackRedRed(insertNode);
            }
        }
        setBlack(root);
    }


    /****
     * 删除对外开放操作
     * @param value 要删除的对象
     * @throws Exception
     */
    public void remove(int value) throws Exception {
        Node removeNode;
        if ((removeNode = findValue(root, value)) == null) {
            throw new Exception("要删除的节点不存在!");
        }
        doRemove(removeNode);
    }


    /****
     * 内部执行删除操作
     * @param removeNode
     */
    private void doRemove(Node removeNode) {
        if (null != removeNode.left && null != removeNode.right) {    //左右都不为空的情况
            //左边最大方案.找到替代节点，替换！转换思维删除单节点
            Node replaceNode = removeNode.left;
            while (replaceNode.right != null) {
                replaceNode = replaceNode.right;
            }
            String color = replaceNode.color;

            //判断node节点是否为root节点
            if (null == removeNode.getFather()) {
                this.root = replaceNode;
            } else {
                if (removeNode.getFather().left == removeNode) {
                    removeNode.getFather().left = replaceNode;
                } else {
                    removeNode.getFather().right = replaceNode;
                }
            }
            replaceNode.color = removeNode.color;

            //如果替换节点是直接子节点，那么则不用再指定相应方向的子节点了，否则无限关联
            if (removeNode.left != replaceNode) {
                replaceNode.left = removeNode.left;
                replaceNode.left.parent = replaceNode;
            }
            if (removeNode.right != replaceNode) {
                replaceNode.right = removeNode.right;
                replaceNode.right.parent = replaceNode;
            }
            replaceNode.parent = removeNode.parent;

            //如果替换节点的颜色是黑色，那么替换节点原来所在的地方应该已经丢失了一个黑色节点，
            //此时就需要重新调整数目
            if (color.equals(Node.BLACK)) {
                removeRebuildRB(replaceNode.left, replaceNode);
            }
            //删除
            removeNode = null;
        } else if (null != removeNode.left || null != removeNode.right) {  //有一个儿子的情况
            //子节点替换，变更颜色即可
            removeSingleSon(removeNode);
            removeNode = null;
        } else {
            //一个儿子也没有,并且是黑色的,需要重新修正红黑树
            removeRebuildRB(removeNode, removeNode.getFather());
            //黑色处理完删除！红色直接删除
            if (removeNode.getFather().left == removeNode) {
                removeNode.getFather().left = null;
            } else {
                removeNode.getFather().right = null;
            }
        }
    }


    /***
     * 删除只有一个儿子节点时
     * -替换被删除节点的颜色
     * -替换被删除节点的位置
     * -绑定关系
     */
    private void removeSingleSon(Node removeNode) {
        if (null != removeNode.left) {            //左儿子不为空
            removeNode.left.getGrandFather().left = removeNode.left;
            removeNode.left.parent = removeNode.parent;
            removeNode.left.color = removeNode.color;
        } else if (null != removeNode.right) {     //右儿子不为空的情况
            removeNode.right.getGrandFather().right = removeNode.right;
            removeNode.right.parent = removeNode.parent;
            removeNode.right.color = removeNode.color;
        }
    }


    /****
     * 删除以后，重新构建一颗红黑树
     * 1-删除的是一个单独的黑色节点，那么删除后，两边的黑色节点的数量很有可能不一致
     *
     */
    private Node removeRebuildRB(Node node, Node parent) {
        Node bro = null;
        while (null == node || node.color == Node.BLACK) {
            if (node == parent.left) {
                bro = parent.right;       //获取兄弟节点
                if (null != bro && bro.color == Node.RED) {
                    //情况一：要删除的节点为黑色，且在左边，兄弟节点在右边，红色
                    //，兄弟节点左旋
                    setBlack(bro);
                    setRed(parent);
                    leftRotate(bro);
                    bro = parent.right;
                }
                //情形2，兄弟置换兄弟节点和父节点的颜色节点为黑色;兄弟两个子节点都为黑色;
                if ((bro.left == null || bro.left.color == Node.BLACK)
                        && (bro.right == null || bro.right.color == Node.BLACK)) {
                    setRed(bro);        //要删除一个黑色节点，则兄弟节点也设置为红色,否则黑色数量不一致
                    node = parent;      //设置node为上一层节点，继续查找
                    parent = parent.getFather();    //设置为上一层节点，继续向上查找
                } else {
                    //情形3：删除的节点是黑色；兄弟节点是黑色；兄弟节点左孩子红色；兄弟节点右孩子黑色
                    if ((bro.right == null || bro.right.color == Node.BLACK) && (null != bro.left && bro.left.color == Node.RED)) {
                        //兄弟节点的左孩子是红色的，右节点是黑色
                        setBlack(bro.left);
                        setRed(bro);
                        rightRotate(bro.left);
                        bro = parent.right;
                    }
                    //情形4:删除的节点是黑色;兄弟节点是黑色的；并且兄弟节点右孩子是红色，左孩子任意颜色
                    bro.color = bro.getFather().color;
                    setBlack(bro.getFather());
                    setBlack(bro.right);
                    leftRotate(bro);
                    node = this.root;
                    break;
                }
            } else {  //要删除的节点在右侧
                bro = parent.left;
                if (null != bro && bro.color == Node.RED) {
                    //情况一：要删除的节点为黑色，且在右边，兄弟节点在左边，红色
                    setBlack(bro);
                    setRed(parent);
                    rightRotate(bro);
                    bro = parent.right;
                }
                //情形2，兄弟置换兄弟节点和父节点的颜色节点为黑色;兄弟两个子节点都为黑色;
                if ((bro.left == null || bro.left.color == Node.BLACK)
                        && (bro.right == null || bro.right.color == Node.BLACK)) {
                    setRed(bro);        //要删除一个黑色节点，则兄弟节点也设置为红色,否则黑色数量不一致
                    node = parent;      //设置node为上一层节点，继续查找
                    parent = parent.getFather();
                } else {
                    //情形3：删除的节点是黑色；兄弟节点是黑色；兄弟节点左孩子红色；兄弟节点右孩子黑色
                    if ((bro.right == null || bro.right.color == Node.BLACK) && (null != bro.left && bro.left.color == Node.RED)) {
                        //兄弟节点的左孩子是红色的，右节点是黑色
                        setBlack(bro.right);
                        setRed(bro);
                        leftRotate(bro.right);
                        bro = parent.right;
                    }
                    //情形4:删除的节点是黑色;兄弟节点是黑色的；并且兄弟节点右孩子是红色，左孩子任意颜色
                    bro.color = bro.getFather().color;
                    setBlack(bro.getFather());
                    setBlack(bro.left);
                    rightRotate(bro);
                    node = this.root;
                    break;
                }
            }
        }

        //设置为黑色节点
        setBlack(node);
        return node;
    }


    private void cengji(List<Node> parent) {
        if (null == parent || parent.size() == 0){
            return;
        }

        //打印当前层
        List<Node> AvlNodeIntegers = new ArrayList<Node>();
        int k = 0;
        for (int i = 0; i < parent.size(); i++) {
            Node currentNode = parent.get(i);
            System.out.println(currentNode.value + "," + currentNode.color);
            if (null != currentNode.left) {
                AvlNodeIntegers.add(currentNode.left);
                k++;
            }
            if (null != currentNode.right) {
                AvlNodeIntegers.add(currentNode.right);
                k++;
            }
        }
        System.out.println("--------------------------");
        cengji(AvlNodeIntegers);
    }


    public void printGraph(int style) {
        List a = new ArrayList<>();
        a.add(root);
        cengji(a);
    }

    public static void main(String[] args) throws Exception {
        RedBlackTree.Node root = new RedBlackTree.Node(50);
        RedBlackTree rbt = new RedBlackTree(root);
        //int[] a = new int[]{51,52};

        // int[] a = new int[]{11,12,13,14};
        // int[] a = new int[]{9,8,7,6,5};
        //int[] a = new int[]{15,100,25,65,80,51,43,44};

        //情形4删除，删除51
        // int[] a = new int[]{48,51,52,53,54};

        //情形3删除，先删除80，就到达了我们所说的情形3    51,48,52,80,60
        //        rbt.remove(80);
        //    rbt.remove(48);
        // int[] a = new int[]{48,52,80,60};

        //情形1删除 50,51,52,53,54,55
        //int[] a = new int[]{51,52,53,54,55};

        //情形2删除是旋转过程中出现的情况。


        //删除以后变身情形2；

        //广度测试
        //50,51,52,53,54,55,56,49,48,47,46,45
        int[] a = new int[]{51, 52, 53, 54, 55, 56, 49, 48, 47, 46, 45};


        for (int i = 0; i < a.length; i++) {
            try {
                rbt.insert(a[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rbt.remove(52);
        rbt.printGraph(4);
    }
}


