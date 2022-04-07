package chenjie.stock.financial.statement.controller;

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
}
