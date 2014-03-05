package cn.gaohongtao.groovy.study.basic

/**
 * 流程控制
 *
 * Date: 14-2-27
 * Time: 上午9:47
 * @auth gaohongtao
 */

//if判断条件
assert true
assert !false

assert ('a' =~ /./)
assert !('a' =~ /b/)

assert [1]
assert ![]

assert [a: 1]
assert ![:]

assert 'a'
assert !''

assert 1
assert !0

assert new Object()
assert !null

/*赋值语句不能作为if的顶级表达式
  if(x = 2){
     assert x
  }
  这样写是不合法的
*/
//但可以通过子表达式进行，但不推荐这样
def x = 0
if ((x = 2)) {
    assert x
}

//循环判断没有这个限制
def store = [];
while (x -= 1) {
    store << x
}
assert [1] == store

//if写法可以不用大括号
if (0) assert false
else if ([]) assert false
else assert true

//方法实现了isCase，就可以使用swtich，grep等方法
switch (10) {
    case 0: assert false; break
    case 0..9: assert false; break
    case [4, 6, 111]: assert false; break
    case String: assert false; break
    case { it % 3 == 0 }: assert false; break
    case ~/../: assert true; break
    default: assert false; break
}

//遍历集合的for的两种替代
list = [2, 3, 4]

log = ''
for (i in 0..<list.size()) {
    log <<= list[i]
}
assert '234' == log.toString()

log = ''
list.each { log <<= it }
assert '234' == log.toString()


