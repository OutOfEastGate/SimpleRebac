package xyz.wanghongtao.rebac.util;

import java.util.Random;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-25
 */
public class IdUtil {

  public static final char[] DEFAULT_ALPHABET =
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  public static final char[] DEFAULT_ALPHABET_EN =
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  /**
   * 生成 12 位的 包含数字、大小写字符、的随机ID
   * 推荐使用
   */
  public static String generateId() {
    return NanoIdUtils.randomNanoId(new Random(), DEFAULT_ALPHABET, 12);
  }

  /**
   * 生成 size 位的 包含数字、大小写字符、的随机ID
   * 注意- 如果size值越小ID重复几率会越高，请谨慎使用
   */
  public static String generateId(int size) {
    return NanoIdUtils.randomNanoId(new Random(), DEFAULT_ALPHABET, size);
  }

  /**
   * 生成 12 位的 包含大小写字符、的随机ID
   * 推荐使用
   *
   * @return 12位字符串
   */
  public static String generateEnId() {
    return NanoIdUtils.randomNanoId(new Random(), DEFAULT_ALPHABET_EN, 12);
  }

  /**
   * 生成 size 位的 包含大小写字符、的随机ID
   * 注意- 如果size值越小ID重复几率会越高，请谨慎使用
   */
  public static String generateEnId(int size) {
    return NanoIdUtils.randomNanoId(new Random(), DEFAULT_ALPHABET_EN, size);
  }

  /**
   * 根据alphabet作为基数数据，生成size位的随机ID
   * 注意- 如果alphabet 数量种类越少、size值越小 随机ID重复几率会越高，请谨慎使用
   */
  public static String generateId(int size, char[] alphabet) {
    return NanoIdUtils.randomNanoId(new Random(), alphabet, size);
  }

  public static Long generateLongId() {
    return System.currentTimeMillis();
  }
}
