package proxy.dynamicProxy;

public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message: " + message);
        return message;
    }

    @Override
    public String get(String message) {
        System.out.println("get message: " + message);
        return message;
    }
}
