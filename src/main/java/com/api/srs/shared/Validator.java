package com.api.srs.shared;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Validator {

  public static String validateStringNullOrEmpty(String valor) {
    return valor == null || valor.isBlank() ? null : valor.trim();
  }

  public static Integer validateIntegerNullOrZero(Integer valor) {
    return valor == null || valor.equals(0) ? null : valor;
  }

  public static BigInteger validateBigIntegerNullOrZero(BigInteger valor) {
    return valor == null || valor.equals(BigInteger.ZERO) ? null : valor;
  }

  public static BigDecimal validateBigDecimalNullOrZero(BigDecimal valor) {
    return valor == null || valor.equals(BigDecimal.ZERO) ? null : valor;
  }

  public static Integer validateIntegerNullOrLessEqualZero(Integer valor) {
    return valor == null || valor.compareTo(0) <= 0 ? null : valor;
  }

  public static BigInteger validateBigIntegerNullOrLessEqualZero(BigInteger valor) {
    return valor == null || valor.compareTo(BigInteger.ZERO) <= 0 ? null : valor;
  }

  public static BigDecimal validateBigDecimalNullOrLessEqualZero(BigDecimal valor) {
    return valor == null || valor.compareTo(BigDecimal.ZERO) <= 0 ? null : valor;
  }

  public static boolean emailValidator(String email) {
    if(email == null || email.isEmpty()) return false;

    return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        .matcher(email)
        .matches();
  }

  public static boolean cpfValidator(String cpf) {
    if (cpf == null || cpf.isEmpty()) return false;

    if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
        cpf.equals("22222222222") || cpf.equals("33333333333") ||
        cpf.equals("44444444444") || cpf.equals("55555555555") ||
        cpf.equals("66666666666") || cpf.equals("77777777777") ||
        cpf.equals("88888888888") || cpf.equals("99999999999") ||
        (cpf.length() != 11)) return (false);

    char dig10, dig11;

    int sm, i, r, num, peso;

    try {
      sm = 0;
      peso = 10;

      for (i = 0; i < 9; i++) {
        num = (int) (cpf.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);

      if ((r == 10) || (r == 11)) dig10 = '0';
      else dig10 = (char) (r + 48);

      sm = 0;
      peso = 11;

      for (i = 0; i < 10; i++) {
        num = (int) (cpf.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);

      if ((r == 10) || (r == 11)) dig11 = '0';
      else dig11 = (char) (r + 48);

      return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
    } catch (InputMismatchException err) {
      return (false);
    }
  }
}