package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.BinaryOrExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:04
 */
public class BinaryOrHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    BinaryOrExpression binaryOrExpression = (BinaryOrExpression) expression;
    Expression left = binaryOrExpression.getLeft();
    Expression right = binaryOrExpression.getRight();
    AtomicReference<Boolean> leftResult = new AtomicReference<>();
    AtomicReference<Boolean> rightResult = new AtomicReference<>();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    Thread.startVirtualThread(() -> {
      CheckPermissionContext clone = checkPermissionContext.clone();
      leftResult.set(recursionExpression(clone, permissionRuntime, left));
      countDownLatch.countDown();
    });
    Thread.startVirtualThread(() -> {
      CheckPermissionContext clone = checkPermissionContext.clone();
      rightResult.set(recursionExpression(clone, permissionRuntime, right));
      countDownLatch.countDown();
    });
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return leftResult.get() || rightResult.get();

  }
}
