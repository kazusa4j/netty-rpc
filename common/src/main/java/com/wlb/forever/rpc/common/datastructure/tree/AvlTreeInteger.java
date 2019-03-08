package com.wlb.forever.rpc.common.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: william
 * @Date: 18/11/21 09:44
 * @Description:
 */
public class AvlTreeInteger {
    /***
     * 遍历方式，
     * 1-前序遍历
     * 2-中序遍历
     * 3-后序遍历
     * 4-层级遍历
     */
    public static final int QIANXU = 1;
    public static final int ZHONGXU = 2;
    public static final int HOUXU = 3;
    public static final int CENGJI = 4;


    private AvlNodeInteger root;
    private int size;


    public AvlTreeInteger() {
    }

    public AvlTreeInteger(AvlNodeInteger root) {
        this.root = root;
    }

    private void initRoot(Integer val) {
        AvlNodeInteger AvlNodeInteger = new AvlNodeInteger(val);
        this.root = AvlNodeInteger;
        System.out.println(this.root.getValue());
    }

    /****
     * 对外开放，插入操作
     * @param val
     * @throws Exception
     */
    public void insert(Integer val) throws Exception {
        if (null == root) {
            initRoot(val);
            size++;
            return;
        }

        if (contains(val)) {
            throw new Exception("The value is already exist!");
        }

        insertNode(this.root, val);
        size++;
    }


    private AvlNodeInteger createSingleNode(Integer val) {
        return new AvlNodeInteger(val);
    }

    /**
     * 递归插入
     * parent == null 到最底部插入前节点判断情况
     *
     * @param parent
     * @param val
     * @return
     */
    private AvlNodeInteger insertNode(AvlNodeInteger parent, Integer val) {
        if (parent == null) {
            return createSingleNode(val);
        }
        if (val < parent.getValue()) {
            //插入判断，小于父节点，插入到右边
            //注意理解回溯，这里最终返回的是插入完成节点
            //每一层回溯，都会返回相应当时递归的节点！！！
            parent.setLeft(insertNode(parent.getLeft(), val));

            //判断平衡，不要在意这里的parent是谁，
            //这个parent肯定是递归层级上，回溯的一个节点！每一个节点都需要判断平衡
            if (height(parent.getLeft()) - height(parent.getRight()) > 1 || (height(parent.getLeft()) == 2 && height(parent.getRight()) == 1)) {
                Integer compareVal = (Integer) parent.getLeft().getValue();
                //左左旋转类型
                if (val < Integer.valueOf(compareVal)) {
                    parent = leftLeftRotate(parent);
                } else {                  //左右旋转类型
                    parent = leftRightRotate(parent);
                }
            }
        }
        if (val > parent.getValue()) {
            //插入判断，小于父节点，插入到右边
            //注意理解回溯，这里最终返回的是插入完成节点
            //每一层回溯，都会返回相应当时递归的节点！！！
            parent.setRight(insertNode(parent.getRight(), val));

            //判断平衡，不要在意这里的parent是谁，
            //这个parent肯定是递归层级上，回溯的一个节点！每一个节点都需要判断平衡
            if (height(parent.getRight()) - height(parent.getLeft()) > 1) {
                Integer compareVal = (Integer) parent.getLeft().getValue();
                if (val > compareVal) {
                    parent = rightRightRotate(parent);
                } else {
                    parent = rightLeftRotate(parent);
                }
            }
        }

        parent.setHeight((maxHeight(parent.getLeft(), parent.getRight())) + 1);
        return parent;
    }


    /***
     * 左左旋转模型
     * @param node  旋转之前的parent node 节点
     * @return 旋转之后的parent node节点
     */
    private AvlNodeInteger leftLeftRotate(AvlNodeInteger node) {
        AvlNodeInteger newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        //由此node的高度降低了，newRoot的高度提高了。
        //newRoot的高度由node的高度而来
        node.setHeight(maxHeight(node.getLeft(), node.getRight()) + 1);
        newRoot.setHeight(maxHeight(newRoot.getLeft(), newRoot.getRight()) + 1);
        return newRoot;
    }


    /***
     * 右右旋转模型
     * @param node
     * @return
     */
    private AvlNodeInteger rightRightRotate(AvlNodeInteger node) {
        AvlNodeInteger newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        //由此node的高度降低了，newRoot的高度提高了。
        //newRoot的高度由node的高度而来
        node.setHeight(maxHeight(node.getLeft(), node.getRight()));
        newRoot.setHeight(maxHeight(newRoot.getLeft(), newRoot.getRight()));
        return newRoot;
    }

    /**
     * 左右模型，先右右，再左左
     *
     * @param node
     * @return
     */
    private AvlNodeInteger leftRightRotate(AvlNodeInteger node) {
        //注意传递的参数
        node.setLeft(rightRightRotate(node.getLeft()));
        return leftLeftRotate(node);
    }

    /***
     * 右左模型，先左左，在右右
     * @param node
     * @return
     */
    private AvlNodeInteger rightLeftRotate(AvlNodeInteger node) {
        node.setRight(leftLeftRotate(node.getRight()));
        return rightRightRotate(node);
    }


    /***
     * 求左右子节点最大高度
     * @param left
     * @param right
     * @return
     */
    private int maxHeight(AvlNodeInteger left, AvlNodeInteger right) {
        return height(left) > height(right) ? height(left) : height(right);
    }

    /***
     * 求一个节点的高度
     * @param t
     * @return
     */
    private int height(AvlNodeInteger t) {
        return null == t ? 0 : t.getHeight();
    }


    public boolean contains(Integer val) {
        AvlNodeInteger curNode = root;
        if (null == curNode) {
            return false;
        }

        while (null != curNode) {
            if (val > curNode.getValue()) {
                curNode = curNode.getRight();
            } else if (val < curNode.getValue()) {
                curNode = curNode.getLeft();
            } else {
                return true;
            }
        }
        return false;
    }


    public void remove(Integer val) {
        if (null == val || null == root) {
            return;
        }
        if (!contains(val)) {
            return;
        }
        remove(root, val);
    }


    /****
     * AVL删除，平衡树实现
     * @param parent
     * @param val
     * @return
     */
    private AvlNodeInteger remove(AvlNodeInteger parent, Integer val) {
        //左子树递归查询
        if (val < parent.getValue()) {
            //删除以后返回替换的新节点
            AvlNodeInteger newLeft = remove(parent.getLeft(), val);
            parent.setLeft(newLeft);
            //检查是否平衡，删除的左边，那么用右边-左边
            if (height(parent.getRight()) - height(parent.getLeft()) > 1) {
                AvlNodeInteger tempNode = parent.getRight();
                if (height(tempNode.getLeft()) > height(tempNode.getRight())) {
                    //RL类型
                    rightLeftRotate(parent);
                } else {
                    //RR类型
                    rightRightRotate(parent);
                }
            }
        } else if (val > parent.getValue()) {
            //删除以后返回替换的新节点
            AvlNodeInteger newRight = remove(parent.getRight(), val);
            parent.setRight(newRight);
            //检查是否平衡
            if (height(parent.getLeft()) - height(parent.getRight()) > 1) {
                AvlNodeInteger tempNode = parent.getLeft();
                if (height(tempNode.getLeft()) > height(tempNode.getRight())) {
                    //LL类型
                    leftLeftRotate(parent);
                } else {
                    //LR类型
                    leftRightRotate(parent);
                }
            }
        } else {
            //相等，匹配成功
            if (null != parent.getLeft() && null != parent.getRight()) {
                //判断高度，高的一方，拿到最大(左)，最小(右)的节点，作为替换节点。
                //删除原来匹配节点
                //左边更高，获取到左边最大的节点
                if (parent.getLeft().getHeight() > parent.getRight().getHeight()) {
                    AvlNodeInteger leftMax = getMax(parent.getLeft());
                    parent.setLeft(remove(parent.getLeft(), leftMax.getValue()));
                    leftMax.setLeft(parent.getLeft());
                    leftMax.setRight(parent.getRight());
                    leftMax.setHeight(maxHeight(leftMax.getLeft(), leftMax.getRight()));
                    parent = leftMax;
                } else {
                    //右边更高，获取到右边最小的节点
                    AvlNodeInteger rightMin = getMin(parent.getRight());
                    parent.setRight(remove(parent.getRight(), rightMin.getValue()));
                    rightMin.setLeft(parent.getLeft());
                    rightMin.setRight(parent.getRight());
                    rightMin.setHeight(maxHeight(parent.getLeft(), parent.getRight()) + 1);
                    parent = rightMin;
                }
            } else {
                //有任意一方节点为空，则不为空的那一方作为替换节点，删除原来的节点
                parent = null;
            }
        }

        return parent;
    }


    /***
     * 删除时用到，获取当前节点子节点最大值
     * @param currentRoot
     * @return
     */
    private AvlNodeInteger getMax(AvlNodeInteger currentRoot) {
        if (currentRoot.getRight() != null) {
            currentRoot = getMax(currentRoot.getRight());
        }
        return currentRoot;
    }

    /***
     * 删除时用到，获取当前节点子节点最小值
     * @param currentRoot
     * @return
     */
    private AvlNodeInteger getMin(AvlNodeInteger currentRoot) {
        if (currentRoot.getLeft() != null) {
            currentRoot = getMin(currentRoot.getLeft());
        }
        return currentRoot;
    }


    public AvlNodeInteger findMax() {
        if (null == root) {
            return null;
        }

        AvlNodeInteger temp = root;
        while (null != temp.getRight()) {
            temp = temp.getRight();
        }
        return temp;
    }


    public AvlNodeInteger findMin() {
        if (null == root) {
            return null;
        }
        AvlNodeInteger temp = root;
        while (null != temp.getLeft()) {
            temp = temp.getLeft();
        }
        return temp;
    }


    public int getNodeSize() {
        return size;
    }


    public void printGraph(int style) {
        if (root == null) {
            return;
        }

        if (style == 1) {
            xianxu(root);
        } else if (style == 2) {
            zhongxu(root);
        } else if (style == 3) {
            houxu(root);
        } else if (style == 4) {
            List a = new ArrayList<>();
            a.add(root);
            cengji(a);
        }
    }

    /***
     * 前序编译
     * 1-根节点
     * 2-左节点
     * 3-右节点
     * 根左右
     * @param parent
     */
    private void xianxu(AvlNodeInteger parent) {
        System.out.println(parent.getValue());
        if (null != parent.getLeft()) {
            xianxu(parent.getLeft());
        }
        if (null != parent.getRight()) {
            xianxu(parent.getRight());
        }
    }

    /***
     * 中序遍历
     * 左节点
     * 根节点
     * 右节点
     *
     *
     * 左根右
     * @param parent
     */
    private void zhongxu(AvlNodeInteger parent) {
        if (null != parent.getLeft()) {
            zhongxu(parent.getLeft());
        }
        System.out.println(parent.getValue());

        if (null != parent.getRight()) {
            zhongxu(parent.getRight());
        }
    }


    /***
     * 后续遍历
     * 左右根
     * 左节点
     * 右节点
     * 根节点
     */
    private void houxu(AvlNodeInteger parent) {
        if (null != parent.getLeft()) {
            houxu(parent.getLeft());
        }
        if (null != parent.getRight()) {
            houxu(parent.getRight());
        }
        System.out.println(parent);
    }

    /***
     * 层级遍历
     * @param parent
     */
    private void cengji(List<AvlNodeInteger> parent) {
        if (null == parent || parent.size() == 0){
            return;
        }

        //打印当前层
        List<AvlNodeInteger> AvlNodeIntegers = new ArrayList<AvlNodeInteger>();
        int k = 0;
        for (int i = 0; i < parent.size(); i++) {
            AvlNodeInteger currentNode = parent.get(i);
            System.out.println(currentNode.getValue() + ",");
            if (null != currentNode.getLeft()) {
                AvlNodeIntegers.add(currentNode.getLeft());
                k++;
            }
            if (null != currentNode.getRight()) {
                AvlNodeIntegers.add(currentNode.getRight());
                k++;
            }
        }
        System.out.println("--------------------------");
        cengji(AvlNodeIntegers);
    }


    public static class AvlNodeInteger {
        private Integer value;
        private Integer height;
        private AvlNodeInteger left;
        private AvlNodeInteger right;

        public AvlNodeInteger(int t) {
            initNode(t, null, null, 1);
        }

        public AvlNodeInteger(int t, AvlNodeInteger left, AvlNodeInteger right) {
            initNode(t, left, right, null);
        }

        private void initNode(int t, AvlNodeInteger left, AvlNodeInteger right, Integer height) {
            this.setValue(t);
            this.left = left;
            this.right = right;
            this.height = height;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public AvlNodeInteger getLeft() {
            return left;
        }

        public void setLeft(AvlNodeInteger left) {
            this.left = left;
        }

        public AvlNodeInteger getRight() {
            return right;
        }

        public void setRight(AvlNodeInteger right) {
            this.right = right;
        }
    }


}
