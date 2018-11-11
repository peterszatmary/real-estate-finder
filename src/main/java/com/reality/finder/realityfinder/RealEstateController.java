package com.reality.finder.realityfinder;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
public class RealEstateController {

    private final RealEstateServiceApi realityService;

    @RequestMapping(path = "/live", method = RequestMethod.GET)
    private @ResponseBody
    ResponseEntity live() {
        return ResponseEntity.ok("Yes we are live !");
    }

    /**
     * example http://localhost:8070/search/13/16/5
     * @param from page number from (13)
     * @param to is page number to (16)
     * @param postFix html file target postfix (5)
     * @return filtered links.
     */
    @RequestMapping(path = "/search/{from}/{to}/{postFix}", method = RequestMethod.GET)
    private @ResponseBody
    ResponseEntity search(@PathVariable("from")  Integer from, @PathVariable("to") Integer to, @PathVariable("postFix")  Integer postFix) {
        System.setProperty("http.agent", "Chrome");
        return ResponseEntity.ok(realityService.search(postFix, from, to));
    }
}
