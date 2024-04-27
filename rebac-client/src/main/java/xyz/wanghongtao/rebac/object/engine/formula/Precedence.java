package xyz.wanghongtao.rebac.object.engine.formula;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Precedence {
    PREC_NONE(0),
    PREC_ASSIGNMENT(1),    // =
    PREC_OR(2),    // or ||
    PREC_AND(3),   // and &&
    PREC_EQUALITY(4),  // == !=
    PREC_COMPARISON(5),    // < > <= >=
    PREC_TERM(6),  // + -
    PREC_FACTOR(7),    // * /
    PREC_UNARY(8),     // ! -
    PREC_CALL(9),

    ;

    private int code;

    public static Optional<Precedence> getByCode(int code) {
        for (Precedence item : Precedence.values()) {
            if (item.code == code) {
                return Optional.ofNullable(item);
            }
        }
        return Optional.empty();
    }

    public Precedence addPrecedence(int plusNum) {
        int targetCode = this.getCode() + plusNum;
        return getByCode(targetCode).orElse(this);
    }
}
