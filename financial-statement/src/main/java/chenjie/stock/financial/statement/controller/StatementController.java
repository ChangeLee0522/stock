package chenjie.stock.financial.statement.controller;

import chenjie.stock.financial.statement.domain.StatementRequest;
import chenjie.stock.financial.statement.domain.StatementResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/statement")
public interface StatementController {

    @RequestMapping(value = "/load/files", method = RequestMethod.POST)
    @ResponseBody
    StatementResponse loadFromFiles(@RequestParam(required = false) String sinceDate,
                                    @RequestBody List<String> files);

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    StatementResponse loadFromDirectory(@RequestParam(required = false) String sinceDate,
                                        @RequestParam String directory);

    @RequestMapping(value = "/query/{type}", method = RequestMethod.GET)
    @ResponseBody
    Float getValue(@PathVariable("type") String type,
                               @RequestParam String code,
                               @RequestParam String item,
                               @RequestParam String date);

    @RequestMapping(value = "/query/{type}", method = RequestMethod.PUT)
    @ResponseBody
    StatementResponse getValue(@PathVariable("type") String type,
                               @RequestBody StatementRequest request);

    @RequestMapping(value = "/queryall/{type}", method = RequestMethod.GET)
    @ResponseBody
    StatementResponse getValue(@PathVariable("type") String type);
}
