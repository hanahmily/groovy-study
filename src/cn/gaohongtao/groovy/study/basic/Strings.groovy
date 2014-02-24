package cn.gaohongtao.groovy.study.basic

/**
 * 能力框架
 *
 * Date: 14-2-24
 * Time: 上午10:22
 * @auth gaohongtao
 */
//基本操作
me = 'Gao'
identity = 'God'
line = "$me is $identity";
assert 'Gao is God' == line;

//字符串中方法调用，这种类似基本的脚本语言
def now = new Date(0);
assert "现在是 70" == "现在是 $now.year"

//多行展示
assert """
INSERT INTO `mail_send`
(`NOTICE_ID`,`RECIEVER`,`SUBJECT`,`CONTENT`,`PRIORITY`,`STATE`,`INSERT_TIME`,`UPDATE_TIME`)
VALUES (28,'hmily_163@163.com','邮箱激活邮件','激活test',1100,1,${now.toGMTString()},${now.toGMTString()});
""" == """
INSERT INTO `mail_send`
(`NOTICE_ID`,`RECIEVER`,`SUBJECT`,`CONTENT`,`PRIORITY`,`STATE`,`INSERT_TIME`,`UPDATE_TIME`)
VALUES (28,'hmily_163@163.com','邮箱激活邮件','激活test',1100,1,1 Jan 1970 00:00:00 GMT,1 Jan 1970 00:00:00 GMT);
"""

//GString 中$表示的是value， 其他部分是strings，这与stringbuffer的部分相似
assert line.strings[0] == ''
assert line.strings[1] == ' is '
assert line.values[0] == 'Gao'
assert line.valueCount == 2

//查找字符串 替代indexOf
assert line.contains('Gao')

//代替substring
assert 'is God' == line[4..line.length() - 1]

//追加字符串，拼接文本使用
line <<= ' !!!' //此操作后line就变成了StringBuilder
assert line instanceof java.lang.StringBuilder
assert 'Gao is God !!!' == line.toString()
line << '??'
assert 'Gao is God !!!??' == line.toString()

//替换字符串
