package net.avalon.generic.core.exception;

/**
 * Description: 自定义异常类
 * status 封装了 异常状态码 code、和异常信息 message
 *
 * @Author: Weiyin
 * @Create: 2023/2/26 - 16:12
 */
public class AvalonException extends RuntimeException {

    private AvalonStatus status;

    public AvalonException(AvalonStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public AvalonException(AvalonStatus status, String message) {
        super(message);
        this.status = status;
    }

    public AvalonStatus getStatus() {
        return this.status;
    }
}
