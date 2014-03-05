package cn.gaohongtao.groovy.study.basic

//禁用同一个类，给一个别名
import cn.gaohongtao.groovy.study.basic.Father as FatherOne
import cn.gaohongtao.groovy.study.basic.Father as FatherTwo
import org.codehaus.groovy.runtime.InvokerHelper
import org.codehaus.groovy.runtime.StringBufferWriter

/**
 * 对象能力
 *
 * Date: 14-2-27
 * Time: 下午2:00
 * @auth gaohongtao
 */
//属性访问
class Pretend {
    public count = 0

    Object get(String name) {
        return 'this test'
    }

    void set(String name, Object value) {
        count++
    }

    def getCount() {
        return count
    }
}

Pretend p = new Pretend()

assert 'this test' == p.noFiled;

p['noThisFile'] = 2

assert 1 == p.count

//使用map做参数，这个比较好。不需要声明参数列表
def add(Map args) {
    args.a + args.b
}

assert 3 == add(a: 1, b: 2)

//动态调用方法
def methodName = 'getCount'
assert 1 == p."${methodName}"()

//安全的引用，引用连上有一个为null就直接返回null
def map = [a: [b: [c: 1]]]
assert null == map?.a?.x?.c

//构造器
class Desk {
    String name, from

    Desk(name, from) {
        this.name = name
        this.from = from
    }
}

desk1 = ['chart', 'beijing'] as Desk
assert 'chart' == desk1.name
Desk desk2 = ['lee', 'beijing']
assert 'lee' == desk2.name

class Chair {
    def name, product
}

new Chair(name: 1, product: 2)
new Chair(product: 2)

/* 在混合脚本与类声明的文件中，不能声明一个与文件名相同的类
class OO{
}
*/

//引用使用别名
FatherOne one = new FatherOne();
FatherTwo two = new FatherTwo();
assert one != two

//直接访问属性
assert desk2.@name == desk2.name

//expendo自动扩充类型
def factory = new Expando();
factory.name = 'test'
assert 'test' == factory.name
factory.do = { item -> "${item} is created" }
assert 'car is created' == factory.do('car')

//list的点操作，应用到元素本身上Gpath调用属性可以用.或者.*,调用方法只能用*.
def list = [new Pretend(count: 100)]
assert list.count == [100]
assert list*.getCount() == [100]

//展开操作符
list = [1, 2, 3]

def sum(a, b, c) {
    a + b + c
}

assert 6 == sum(* list)

range = 1..2
assert [1, 2, 3] == [* range, 3]

map = [a: 1]
assert [a: 1, b: 2] == [*: map, b: 2]

//方法拦截
class Traceable implements GroovyInterceptable {
    Writer writer

    @Override
    Object invokeMethod(String name, Object args) {
        writer.write("\nbefore method $name")
        writer.flush();
        def metaClass = InvokerHelper.getMetaClass(this)
        def result = metaClass.invokeMethod(this, name, args)
        writer.write("\nafter method $name")
        writer.flush();
        return result;
    }
}

class Watever extends Traceable {
    def outer() {
        inner()
    }

    def inner() {
        1
    }
}

StringBuffer log = new StringBuffer()

Watever w = new Watever(writer: new StringBufferWriter(log))
assert w.outer() == 1
assert log.toString() == """
before method outer
before method inner
after method inner
after method outer"""

//直接使用动态代理的方式,这种适合给java的class增加拦截
class Whaterever {
    def outer() {
        inner()
    }

    def inner() {
        1
    }
}

log=new StringBuffer("\n")
def trace = new TracingInterceptor(writer: new StringBufferWriter(log))
def proxy = ProxyMetaClass.getInstance(Whaterever.class)
proxy.interceptor = trace
proxy.use{
    assert 1 == new Whaterever().outer()
}
assert log.toString() == """
before cn.gaohongtao.groovy.study.basic.Whaterever.ctor()
after  cn.gaohongtao.groovy.study.basic.Whaterever.ctor()
before cn.gaohongtao.groovy.study.basic.Whaterever.outer()
  before cn.gaohongtao.groovy.study.basic.Whaterever.inner()
  after  cn.gaohongtao.groovy.study.basic.Whaterever.inner()
after  cn.gaohongtao.groovy.study.basic.Whaterever.outer()
"""




