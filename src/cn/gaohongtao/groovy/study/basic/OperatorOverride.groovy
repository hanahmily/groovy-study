class Man {
    private boolean sex;

    Man(boolean sex) {
        this.sex = sex;
    }

    boolean getSex() {
        return sex
    }

    List<Man> plus(Man man) {
        //异性生个娃
        if (man.sex != this.sex) {
            return [man, this, new Man(man.sex)];
        } else {
            //同性俩人自己过
            return [man, this];
        }
    }
}

def man = new Man(true);
def woman = new Man(false);

assert 3 == (man + woman).size();

def anotherMan = new Man(true);

assert 2 == (man + anotherMan).size();

Man realMan = man as Man;
assert realMan.sex;
//重载列表
/*表 1. Groovy 的可重载操作符
1.	比较操作符对应着普通的 Java equals 和 compareTo 实现
2.	Java 的算术类操作符，例如 +、- 和 *
3.	数组存取类操作符 []

表 2. 比较操作符  is()作为引用相等
操作符	方法
a == b	a.equals(b)
a <=> b	a.compareTo(b)

表 3. 四个附加的值
操作符	含义
a > b	如果 a.compareTo(b) 返回的值大于 0，那么这个条件为 true
a >= b	 如果 a.compareTo(b) 返回的值大于等于 0，那么这个条件为 true
a < b	如果 a.compareTo(b) 小于 0，那么这个条件为 true
a <= b	如果 a.compareTo(b) 小于等于 0，那么这个条件为 true

表 3. Groovy 的算术类操作符
操作符	方法
a + b	 a.plus(b)
a - b	 a.minus(b)
a * b	 a.multiply(b)
a / b	 a.divide(b)
a++ or ++a	 a.next()
a-- or --a	 a.previous()
a << b	 a.leftShift(b)
a >> b   a.rightShift(b)
a <<< b	 a.leftShiftUnsigned(b)
a >>> b   a.rightShiftUnsigned(b)

表 4. 数组操作符
操作符	方法
a[b]	 a.getAt(b)
a[b] = c	 a.putAt(b, c)

a as type a.asType(typeClass)

*/