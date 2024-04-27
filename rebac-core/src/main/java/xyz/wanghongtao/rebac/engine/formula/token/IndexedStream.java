package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.exception.CustomException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class IndexedStream<TElement, TElementType> {
    protected BiFunction<TElement, TElementType, Boolean> constraintChecker;
    private int index;
    private final List<TElement> input;

    public IndexedStream(Collection<TElement> input, BiFunction<TElement, TElementType, Boolean> constraintChecker) {
        this.input = new ArrayList<>(input);
        this.constraintChecker = constraintChecker;
    }

    public List<TElement> getInput() {
      return input;
    }

    public Optional<TElement> peek()  {
        if (index + 1 >= input.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(input.get(index + 1));
    }

    public boolean peekTypeIs(TElementType expectedType) {
        Optional<TElement> peekVal = peek();
        return (peekVal.isPresent() && constraintChecker.apply(peekVal.get(), expectedType));
    }

    public TElement current() {
        if (end()) {
            throw new  CustomException("");
        }
        return input.get(index);
    }

    public boolean currentTypeIs(TElementType expectedType) {
        TElement val = current();
        return constraintChecker.apply(val, expectedType);
    }

    public TElement consume()  {
        TElement r = current();
        index++;
        return r;
    }

    public TElement consume(TElementType expectedType) {
        TElement elem = consume();
        if (constraintChecker.apply(elem, expectedType)) {
            return elem;
        }
      return elem;
    }

    public boolean isLastToken() {
        return index >= (input.size() - 1);
    }

    public boolean end() {
        return index >= input.size();
    }



    public String toString() {
        return String.format("(CharStream {Index=%d, Input=%s})", index, input);
    }
}
