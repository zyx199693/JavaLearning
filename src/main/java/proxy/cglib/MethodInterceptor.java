package proxy.cglib;

import net.sf.cglib.proxy.MethodProxy;

import javax.security.auth.callback.Callback;
import java.lang.reflect.Method;

public interface MethodInterceptor extends Callback {
    public Object interceptor(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
}
