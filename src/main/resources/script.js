/**
 * Created by I on 2017/9/15.
 */

var fun1 = function (name) {
    print('hi ~~~~~~~');
    return "greetings from javascript";
}

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};

var MyJavaClass = Java.type('com.houde.script.ScriptTest');
var result = MyJavaClass.fun1('John Doe');
print(result);