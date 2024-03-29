package xyz.wanghongtao.rebac.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.object.form.graph.AddGraphParam;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.service.GraphService;

/**
 * @author wanghongtao
 * @data 2024/3/27 22:41
 */
@AllArgsConstructor
@RequestMapping("/graph")
@RestController
public class GraphController {
  private final GraphService graphService;
  @GetMapping("/get/{id}")
  public Result<GraphDo> getGraph(@PathVariable("id") String id) {
    return Result.success(graphService.getGraph(id));
  }

  @PostMapping("/save")
  public Result<Boolean> saveGraph(@RequestBody AddGraphParam addGraphParam) {
    graphService.saveGraph(addGraphParam.getGraphDo());
    return Result.success(true);
  }
}
