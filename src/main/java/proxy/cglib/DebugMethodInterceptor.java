package proxy.cglib;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DebugMethodInterceptor implements MethodInterceptor {
    /**
     *
     * @param obj   //被代理的对象
     * @param method    //被拦截的方法
     * @param args      //方法入参
     * @param proxy     //用于调用原始方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object interceptor(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before method:" + method.getName());
        Object result = proxy.invokeSuper(method, args);
        System.out.println("after method:" + method.getName());
        return result;
    }
}
