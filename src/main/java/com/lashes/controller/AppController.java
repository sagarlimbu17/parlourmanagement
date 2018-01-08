package com.lashes.controller;


import com.lashes.ExcelReport.ExcelReportListView;
import com.lashes.config.SecurityConfig;
import com.lashes.entities.*;
import com.lashes.entities.Finance;
import com.lashes.models.CreateUser;
import com.lashes.models.EditUser;
import com.lashes.models.SalesReportMapping;
import com.lashes.services.*;
import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryServices inventoryServices;

    @Autowired
    private SalesServices salesServices;

    @Autowired
    private ReportServices reportServices;


    @RequestMapping("/addCategory")
    public String addCategory(){
        return "category/category";
    }

    @RequestMapping(value = "/addCategory",method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("itemcategory")Category category, Model model){
        categoryService.addCategory(category);
        model.addAttribute("addCategorySuccess",true);
        return "category/category";
    }

    //items handlers

    @RequestMapping("/itemsmgmt")
    public String itemsmgmt(){
        return "items/items";
    }

    //change password handlers

    @RequestMapping("/password")
    public String password(Model model){
        model.addAttribute("username",getPrincipal());
        return "password";
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam("username") String username,
                                 @RequestParam("oldpassword") String password,
                                 @RequestParam("password") String newPassword,
                                 Model model){
        SecurityConfig securityConfig = new SecurityConfig();

        String pass =passwordService.password(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(password, pass)){
            //encoding the user input new password for verification with old password in database
            String encryptedPass = securityConfig.passwordEncoder().encode(newPassword);
            passwordService.changePassword(username,encryptedPass);
        }
        else {
            model.addAttribute("passwordChangeFlag",false);
            return "password";
        }
        model.addAttribute("passwordChangeFlag",true);
        return "password";

    }

    public String getPrincipal(){
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else {
            username = principal.toString();
        }
        return username;
    }


    @RequestMapping("/registerProduct")
    public String addItems(Model model){
        List<Category> categories = categoryService.getAllCategories("product");
        model.addAttribute("categories", categories);
        return "items/registerProduct";
    }
    //registering new products

    @RequestMapping(value = "/admin/registerProduct", method = RequestMethod.POST)
    public String registerProduct(@ModelAttribute("product")Product product, Model model){
        product.setAddedBy(getPrincipal());
        productService.addProduct(product);
        List<Category> categories = categoryService.getAllCategories("product");
        model.addAttribute("categories", categories);
        model.addAttribute("productAdded",true);
        return "items/registerProduct";
    }


    @RequestMapping("/admin/addProduct")
    public String addProduct(Model model){
        List<Category> categories = categoryService.getAllCategories("product");
        model.addAttribute("categories",categories);
        return "items/addProduct";
    }


    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
    public String addProducts(Model model, @ModelAttribute("product") Product product){
        product.setAddedBy(getPrincipal());

        productService.addProduct(product);
        List<Category> categories = categoryService.getAllCategories("product");
        model.addAttribute("categories",categories);
        model.addAttribute("productAdded",true);
        return "items/addProduct";
    }

    @RequestMapping("/getProductDetailsByCategory")
    public @ResponseBody List<RgProduct> getProductDetails(@RequestParam String category){
        System.out.println("product details by category");
        return productService.getProductDetailsByCategory(category);
    }

    @RequestMapping("/getProductByProductName")
    public @ResponseBody ResponseEntity<RgProduct> getProductDetailsByProductName(@RequestParam String productName){
        List<RgProduct> products=productService.getProductByProductName(productName);
        ResponseEntity<RgProduct> res = new ResponseEntity(products, HttpStatus.OK);
        return res;
    }

    @RequestMapping(value = "/registerProduct", method = RequestMethod.POST)
    public String registerProduct(@ModelAttribute("rgProduct") RgProduct rgProduct,Model model){
        productService.registerProduct(rgProduct);
        model.addAttribute("productRegistered",true);
        return "items/registerProduct";
    }


    @RequestMapping("/admin/billing")
    public String createBills(Model model){
        List<Category> categories = categoryService.getAllCategories("product");
        model.addAttribute("categories",categories);
        return  "billing/createnewbill";
    }



    @RequestMapping("/inventory")
    public String showPage(Model model,@RequestParam int page){
        model.addAttribute("totalPages",inventoryServices.getLastPageNumber());
        model.addAttribute("currentPage",page);
        model.addAttribute("inventoryList",inventoryServices.getAllItems(page));
        return "inventory/inventory";
    }


/*    service registration page starts here*/

    @RequestMapping("/registerService")
    public String registerService(Model model){
        model.addAttribute("serviceCategory",categoryService.getAllCategories("service"));
        return "Services/registerService";
    }

    @RequestMapping("/serviceBilling")
    public String serviceBilling(){
        return "billing/servicebilling";
    }

    @RequestMapping(value = "/serviceBilling", method = RequestMethod.POST)
    public String serviceBillingPost(@ModelAttribute("sales")Sales sales,Model model){
        salesServices.createServiceBill(sales);
        model.addAttribute("serviceBillingCompleted",true);
        return "billing/servicebilling";
    }

    @RequestMapping("/report")
    public String report(@ModelAttribute("salesReportMapping") SalesReportMapping salesReportMapping, Model model){
        model.addAttribute("salesReportMapping",salesReportMapping);
        return "reports/reports";
    }

    @RequestMapping(value="/report" , method=RequestMethod.POST,params = "action=getReport")
    public String getReport(@ModelAttribute("salesReportMapping") @Valid SalesReportMapping salesReportMapping, BindingResult bindingResult,
                                          Model model) throws ParseException {

        if(bindingResult.hasErrors()){
            model.addAttribute("salesReportMapping", salesReportMapping);
            return "reports/reports";
        }
        List<Sales>  salesList= new ArrayList<Sales>();

            SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd");
             Date fDate = smd.parse(salesReportMapping.getFromDate());
             Date tDate = smd.parse(salesReportMapping.getToDate());
             if(salesReportMapping.salesType.equalsIgnoreCase("both")){
                 salesList = reportServices.totalSalesReport(fDate,tDate);
                 model.addAttribute("totalSales",reportServices.totalSalesSum(fDate,tDate));
             }
             else {
                 if(salesReportMapping.salesType.equalsIgnoreCase("service")){
                     setCategoryAndQuantity(salesList);
                 }
                 salesList=reportServices.getSalesReportList(fDate,tDate,salesReportMapping.getSalesType());
                 model.addAttribute("totalSales",reportServices.totalSalesSumBySalesType(fDate,tDate,salesReportMapping.getSalesType()));

             }


        model.addAttribute("salesList",salesList);
        return "reports/reports";

    }

    @RequestMapping(value = "/report",method = RequestMethod.POST, params = "action=downloadExcel")
    public ModelAndView salesReport(@ModelAttribute SalesReportMapping salesReportMapping){
        Date ccDate= new Date();
        Date upDate= new Date();

        try{
        String cDate= salesReportMapping.getFromDate();
        String uDate= salesReportMapping.getToDate();
        SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd");
        ccDate = smd.parse(cDate);
        upDate = smd.parse(uDate);
        }catch (Exception ex){

        }
        List<Sales> salesList;
        if(salesReportMapping.salesType.equalsIgnoreCase("both")){
            salesList = reportServices.totalSalesReport(ccDate,upDate);
        }
        else{
            salesList=reportServices.getSalesReportList(ccDate,upDate,salesReportMapping.getSalesType());
        }

        return  new ModelAndView(new ExcelReportListView(),"salesList",salesList);
    }


    //for service sales

    private void setCategoryAndQuantity(List<Sales> salesList){
        for(Sales sales : salesList){
            sales.setQuantity(0);
            sales.setCategory("none");
            sales.setVendor("none");
        }
    }


    //finance section

    @Autowired
    private FinanceServices financeServices;

    @RequestMapping("/finance")
    public String finanace(Model model){
        return "finance/finance";
    }

    @RequestMapping("/financeanalysis")
    public String financeanalysis(Model model) throws ParseException{
        model.addAttribute("profitLoss",financeServices.profitAmount());
        model.addAttribute("profitPercent",financeServices.profitPercent());
        model.addAttribute("targetStatusInAmount",financeServices.salesTargetStartingNow());
        model.addAttribute("targetToday",financeServices.targetForToday());
        return "finance/financeanalysis";
    }


    @RequestMapping(value = "/saveFinance",method = RequestMethod.POST)
    public String postFinance(@ModelAttribute("finance") Finance finance, Model model){

        finance.setCreatedDate(new Date());
       financeServices.targetActivate(finance);
       model.addAttribute("targetSetStatus",true);
        return "finance/finance";
    }



    //users section starts here

    @Autowired
    private UserServices userServices;

    @RequestMapping("/createNewUser")
    public String newUser(){
        return "users/newuser";
    }

    @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
    public String createNewUser(Model model, @ModelAttribute CreateUser user){

        Set<UserRole> userRoleList = new HashSet<>();

        UserRole userRole = new UserRole();
        userRole.setRole(user.getRole());


        userRoleList.add(userRole);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(user.getPassword());

        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setEnabled(true);
        user1.setPassword(encryptedPassword);

        user1.setUserRole(userRoleList);

        userRole.setUser(user1);

        userServices.createNewUser(user1);

        model.addAttribute("userCreated",true);

        return "users/newuser";
    }

    @RequestMapping("/editUsers")
    public String editUsers(Model model){

        model.addAttribute("userList",userServices.getAllUsers());
        return "users/editusers";
    }


    @RequestMapping("/viewSingleUser" )
    public String viewSingleUser(@RequestParam Long id,Model model){
        User user = userServices.findUser(id);
        model.addAttribute("user",user);
        return "users/viewsingleuser";
    }

    @RequestMapping(value = "/editSingleUser", method = RequestMethod.POST)
    public String editSingleUser(@ModelAttribute EditUser editUser, Model model){

        User user = userServices.findUser(editUser.getId());

        user.setPassword(new BCryptPasswordEncoder().encode(editUser.getPassword()));
        user.setUsername(editUser.getUsername());

        UserRole userRole = userServices.getUserRole(editUser.getId());
        userRole.setRole(editUser.getUserRole());

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        user.setUserRole(userRoles);
        userRole.setUser(user);
        userServices.editUser(user);

        model.addAttribute("userEditSuccess",true);

        return "users/viewsingleuser";
    }

    @RequestMapping(value = "/deleteSingleUser")
    public String deleteUser(Model model, @RequestParam Long id){

        if(userServices.deleteUser(id)){
            model.addAttribute("userDeleted",true);
        }
        model.addAttribute("userList",userServices.getAllUsers());
        return "users/editusers";
    }

    @RequestMapping("/activateUser")
    public String activateUser(Model model){
        model.addAttribute("userList",userServices.getInactiveUser());
        return "users/activateuser";
    }


    @RequestMapping(value = "/activateSingleUser")
    public String activateUser(Model model, @RequestParam Long id){
        userServices.activateUser(id);
        model.addAttribute("userList",userServices.getAllUsers());
        return "users/editusers";
    }

    @RequestMapping("/editProduct")
    public String editProd(Model model){
        model.addAttribute("categoryList",categoryService.getAllCategories("product"));
        return "items/editProduct";
    }

    @RequestMapping(value = "/editProduct",method = RequestMethod.POST)
    public String editProduct(Model model, @RequestParam String category){
        model.addAttribute("categoryList",categoryService.getAllCategories("product"));
        model.addAttribute("productList", productService.getProductDetailsByCategory(category));
        return "items/editProduct";
    }

    @RequestMapping("/editSingleProduct")
    public String editSingleProduct(Model model, @RequestParam Long id){
        model.addAttribute("RgProduct",productService.findRgProduct(id));
        return "items/editsingleproduct";
    }

    @RequestMapping(value = "/editSingleProduct", method = RequestMethod.POST)
    public String editSingleProductPost(Model model, @ModelAttribute RgProduct rgProduct){
        productService.editSingleProduct(rgProduct);
        model.addAttribute("productEdited", true);
        model.addAttribute("productEditComplete",true);
        model.addAttribute("modifiedProduct",productService.findRgProduct(rgProduct.getId()));
        return "items/viewmodifiedproduct";
    }


    //init binder

    @InitBinder
    public void bindingPreparation(WebDataBinder binder){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor customDateEditor = new CustomDateEditor(dateFormat,true);
        binder.registerCustomEditor(Date.class,customDateEditor);
    }






}
