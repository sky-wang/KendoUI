package kendoui.controllers;


import kendoui.dao.ICategoryDao;
import kendoui.exception.MTXException;
import kendoui.model.Category;
import kendoui.service.IPointOfSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Query;
import java.util.List;

@Controller
public class SampleController {

    @Autowired
    private IPointOfSaleService pointOfSaleService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() throws MTXException {
        pointOfSaleService.getCategoryByPosId(1l);
        return "hello";
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

}
