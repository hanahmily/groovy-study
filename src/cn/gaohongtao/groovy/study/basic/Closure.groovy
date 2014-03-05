package cn.gaohongtao.groovy.study.basic

/**
 * 闭包
 *
 * Date: 14-2-26
 * Time: 下午3:10
 * @auth gaohongtao
 */

//从方法中获取闭包
class Father {
    private boolean isCalled = false;

    boolean isCalled() {
        return isCalled;
    }

    void callMe() {
        isCalled = true;
    }
}

Father f = new Father();
def callMe = f.&callMe;

callMe.call();

assert f.isCalled()

//参数简单声明    闭包作为最后一个参数可以简单生命

def validate(name, worker) {
    return worker(name + ' ');
}

assert "gao do it" == validate('gao') { return it + "do it" }

//缺省值
def addr = { x, y = 5 -> return x + y }
assert 12 == addr(7)
//参数数量
assert 2 == { x, y -> }.getParameterTypes().size()

//curry
def multiply = { x, y -> return x * y }  // closure
def triple = multiply.curry(3)           // triple = { y -> return 3 * y }
def quadruple = multiply.curry(4)
// quadruple = { y -> return 4 * y }
assert 12 == triple.call(4)                   // explicit call
assert 20 == quadruple(5)                     // implicit call

//isCase使用
assert [2] == [1, 2, 3].grep { it % 2 == 0 }

switch (10) {
    case { it + 5 == 15 }: assert true; break;
    default: asssert false;
}

//闭包范围
class Mother {
    private filed = 1
    groovy.lang.Closure birth(param) {
        //最后的表达式值作作为返回值
        return {this.filed};
    }
}
assert 1 == new Mother().birth()();