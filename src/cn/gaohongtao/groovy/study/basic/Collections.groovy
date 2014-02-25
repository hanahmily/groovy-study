package cn.gaohongtao.groovy.study.basic

/**
 * 集合类测试
 *
 * Date: 14-2-25
 * Time: 上午10:39
 * @auth gaohongtao
 */

/**
 * Range的使用
 */
assert (5..9).contains(5)
assert !(5..<10).contains(10)

//声明range
a = 0..10
assert a instanceof Range
b = new IntRange(0, 10)
assert a == b
assert !a.is(b)

//时间范围
today = new Date();
yestoday = today - 1;
assert (today..yestoday).size() == 2

//迭代rang
log = ''
for (element in 5..9) {
    log += element
}
assert '56789' == log

log = ''
(9..5).each { log += it }
assert '98765' == log;

//range应用在case
target = 34

switch (target) {
    case 10..20: assert false; break;
    case 20..50: assert true; break;
    default: throw new IllegalArgumentException()
}

//实现iscase方法后就可以用用在switch语句中
assert (10..20).isCase(15)

//grep与shell中表述类似，再集合中提取符合范围的
assert [23, 33, 55].grep(20..40) == [23, 33]

/*自定义Range */

class Status implements Comparable<Status> {

    static final ST_LIST = ['A', 'P', 'D']

    Status(String st) {
        this.index = ST_LIST.indexOf(st);
    }

    private int index;

    Status next() {
        return new Status(ST_LIST[(index + 1) % ST_LIST.size()]);
    }

    Status previous() {
        //当状态为A，调用这个方法后构造器入参为-1,其指向的是D
        return new Status(ST_LIST[index - 1]);
    }

    String toString() {
        return ST_LIST[index]
    }

    @Override
    int compareTo(Status o) {
        return index <=> o.index
    }
}

def stP = new Status('P')
def stA = new Status('A')


line = ''
(stP..stA).each { line += it.toString() }
assert 'PA' == line

/**
 * list 操作实例
 */

//从range转换为list
list = (2..9).toList();
assert list == [2, 3, 4, 5, 6, 7, 8, 9]

//替换list
list[2..5] = [7, 6, 5, 4]
assert list == [2, 3, 7, 6, 5, 4, 8, 9]
list[3..4] = []
assert list == [2, 3, 7, 4, 8, 9]

//操作list
list = []
list += 'a'
assert ['a'] == list
list << ['b', 'c']
assert ['a', ['b', 'c']] == list

assert ['b', 'c'] == (list - ['a']).flatten()
assert ['a', ['b', 'c'], 'a', ['b', 'c']] == list * 2

/*list 与 range在实现case的方式相同，这里不举例 除外forin与each方法也相同
*/

//list 常用方法举例
assert [1, 2, 3, 4, 5] == [1, [2, 3], [[4]], [], 5].flatten()
//常用的栈操作
assert 3 == [1, 2, 3].pop()
//排序
list = [1, 2, 3]
//根据闭包里返回的值做升序
list = list.sort({ -it })
assert [3, 2, 1] == list

def doubled = [3, 2, 1].collect { it * 2 }
assert [6, 4, 2] == doubled
def odd = list.find { it % 2 != 0 }
assert 3 == odd;

//join
assert list.join(",") == '3,2,1'
//inject 插入一个中间变量来暂时保存计算结果。可以用来替代循环外声明的临时变量
result = list.inject(0) { tmp, listElement -> tmp += listElement }
assert result == 0 + 3 + 2 + 1

/**
 * map 使用
 */

//表达式作为key和value
def x = 'a'
assert ['x': 1] == [x: 1]
assert [a: 1] == [(x): 1]
//闭包进行迭代
map = [a: 1, b: 2]
map.each { entry -> entry.value *= 2 }
assert [a: 2, b: 4] == map
map.each { key, value -> map[key] = value / 2 }
assert [a: 1, b: 2] == map

//collect可以将闭包里买你的值加入一个集合中
def addTo = []
map.collect(addTo) { it.value + 1 }
assert [2, 3] == addTo

map.sort { -it.value }
assert [b: 2, a: 1] == map

