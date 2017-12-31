package com.lashes.controller;

import com.lashes.entities.Sales;
import com.lashes.models.Status;
import com.lashes.services.ReportServices;
import com.lashes.services.SalesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BillController {

    @Autowired
    private SalesServices salesServices;

    @Autowired
    private ReportServices reportServices;

    @RequestMapping(value = "/createBill",method = RequestMethod.POST)
    public ResponseEntity<Status> createBill(@RequestBody List<Sales> sales, Model model){
        salesServices.createBill(sales);
       return new ResponseEntity<Status>(HttpStatus.OK);
    }


    @RequestMapping("/listItems")
    public @ResponseBody Sales getUsersForGrid(@RequestParam Map<String, String> params) {
        params.get("parametername");
        return null;
        // ...
    }





}
