package groovy



class groovyTest {

  def fn() {
    def lex = xyz.wanghongtao.rebac.engine.formula.FormulaLexer.lex("a and b")
    println (lex)
    Map<String,String> map = new HashMap<>();
    map.put("a", "b")
    def currentTimeMillis = System.currentTimeMillis()
    def date = new Date(currentTimeMillis)
    map.put("currentTime", date.dateTimeString)
    return map
  }

}
