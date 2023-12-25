package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.*;
import com.inventorymanagementsystem.config.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardController implements Initializable {
    private double x;
    private double y;

    @FXML
    private Button billing_btn;

    @FXML
    private AnchorPane billing_pane;

    @FXML
    private Button customer_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane customer_pane;

    @FXML
    private AnchorPane dasboard_pane;

    @FXML
    private TextField prc_invoice_no;


    @FXML
    private Button purchase_btn;

    @FXML
    private AnchorPane purchase_pane;

    @FXML
    private Button sales_btn;

    @FXML
    private AnchorPane sales_pane;

    @FXML
    private Label user;

    @FXML
    private Label inv_num;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    @FXML
    private Button bill_add;

    @FXML
    private Button bill_clear;

    @FXML
    private DatePicker bill_date;

    @FXML
    private TextField bill_item;

    @FXML
    private TextField bill_name;

    @FXML
    private TextField bill_phone;

    @FXML
    private TextField bill_price;

    @FXML
    private Button bill_print;

    @FXML
    private ComboBox<?> bill_quantity;

    @FXML
    private Button bill_save;

    @FXML
    private TextField bill_total_amount;

    @FXML
    private TableView<Billing> billing_table;

    @FXML
    private TextField billing_table_search;

    @FXML
    private Label final_amount;

    private  String invoiceList[]={"BX123456","ZX123456","AX123456"};

    private String quantityList[]={"1","2","3","4","5","6","7","8","9","10"};

    @FXML
    private TableColumn<?, ?> col_bill_item_num;

    @FXML
    private TableColumn<?, ?> col_bill_price;

    @FXML
    private TableColumn<?, ?> col_bill_quantity;

    @FXML
    private TableColumn<?, ?> col_bill_total_amt;

    @FXML
    private Button cust_btn_add;

    @FXML
    private Button cust_btn_delete;

    @FXML
    private Button cust_btn_edit;

    @FXML
    private TableColumn<?, ?> cust_col_id;

    @FXML
    private TableColumn<?, ?> cust_col_name;

    @FXML
    private TableColumn<?, ?> cust_col_phone;

    @FXML
    private TextField cust_field_name;

    @FXML
    private TextField cust_field_phone;

    @FXML
    private TextField customer_search;

    @FXML
    private TableView<Customer> customer_table;

    @FXML
    private TableColumn<?, ?> sales_col_cust_name;

    @FXML
    private TableColumn<?, ?> sales_col_date_of_sales;

    @FXML
    private TableColumn<?, ?> sales_col_id;

    @FXML
    private TableColumn<?, ?> sales_col_inv_num;

    @FXML
    private TableColumn<?, ?> sales_col_quantity;

    @FXML
    private TableColumn<?, ?> sales_col_total_amount;

    @FXML
    private TableColumn<?, ?> sales_col_price;

    @FXML
    private TableColumn<?, ?> sales_col_item_num;

    @FXML
    private TableView<Sales> sales_table;

    @FXML
    private Label sales_total_amount;

    @FXML
    private Button purchase_btn_add;

    @FXML
    private Button purchase_btn_print;

    @FXML
    private Label purchase_total_amount;

    @FXML
    private TableColumn<?, ?> purchase_col_date_of_purchase;

    @FXML
    private TableColumn<?, ?> purchase_col_id;

    @FXML
    private TableColumn<?, ?> purchase_col_invoice;

    @FXML
    private TableColumn<?, ?> purchase_col_invoice_no;

    @FXML
    private TableColumn<?, ?> purchase_col_shop_details;

    @FXML
    private TableColumn<?, ?> purchase_col_total_amount;

    @FXML
    private TableColumn<?, ?> purchase_col_total_items;

    @FXML
    private TableView<Purchase> purchase_table;

    @FXML
    private Label dash_total_items_sold_this_month;

    @FXML
    private Label dash_total_purchase;

    @FXML
    private Label dash_total_sales_items_this_month_name;

    @FXML
    private Label dash_total_sales_this_month;

    @FXML
    private Label dash_total_sales_this_month_name;

    @FXML
    private Label dash_total_sold;

    @FXML
    private Label dash_total_stocks;

    @FXML
    private Button signout_btn;

    @FXML
    private TextField purchase_name;

    @FXML
    private TextField purchase_details;

    @FXML
    private TextField purchase_price;

    @FXML
    private TextField purchase_totalamount;

    @FXML
    private ComboBox<?> purchase_quantity;

    @FXML
    private DatePicker purchase_date;

    List<Product> productsList;

    public void onExit(){
        System.exit(0);
    }

    public void activateAnchorPane(){
        Button[] btnlist = {dashboard_btn,billing_btn,purchase_btn,customer_btn,sales_btn};
        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(true);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            getItemSoldThisMonth();
            getTotalPurchase();
            getTotalSales();
            getTotalPurchaseAmount();
            getTotalStocks();

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , #21965a 0%, #00b2c2 100%)");
            billing_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color:transparent");
            sales_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");
        });
        billing_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(true);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:transparent");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , #21965a 0%, #00b2c2 100%)");
            customer_btn.setStyle("-fx-background-color:transparent");
            sales_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");
        });
        customer_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(true);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:transparent");
            billing_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , #21965a 0%, #00b2c2 100%)");
            sales_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");
        });
        sales_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(true);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:transparent");
            billing_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color:transparent");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , #21965a 0%, #00b2c2 100%)");
            purchase_btn.setStyle("-fx-background-color:transparent");
        });
        purchase_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(true);
            dashboard_btn.setStyle("-fx-background-color:transparent");
            billing_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color:transparent");
            sales_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , #21965a 0%, #00b2c2 100%)");
            });



    }

    public void setUsername(){
        user.setText(User.name.toUpperCase());
    }

    public void activateDashboard(){
        dasboard_pane.setVisible(true);
        billing_pane.setVisible(false);
        customer_pane.setVisible(false);
        sales_pane.setVisible(false);
        purchase_pane.setVisible(false);
    }

    public List<Product> getItemsList(){
        productsList=new ArrayList<>();
        connection= Database.getInstance().connectDB();
        String sql="SELECT * FROM PRODUCTS";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Product product;
            while (resultSet.next()){
                product=new Product(Integer.parseInt(resultSet.getString("id")),resultSet.getString("item_number"),Integer.parseInt(resultSet.getString("quantity")),Double.parseDouble(resultSet.getString("price")));
                productsList.add(product);
            }
        }catch (Exception err){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
        return productsList;
    }  // Ürünleri listeleme
    public void setInvoiceNum(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT MAX(inv_num) AS inv_num FROM sales";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                String result=resultSet.getString("inv_num");
                if (result == null) {
                    Invoice.billingInvoiceNumber = "INV-1";
                    inv_num.setText(Invoice.billingInvoiceNumber);
                } else {
                    int invId = Integer.parseInt(result.substring(4));
                    invId++;
                    Invoice.billingInvoiceNumber = "INV-" + invId;
                    inv_num.setText(Invoice.billingInvoiceNumber);
                }
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    } // Fatura numarasını otomatik ayarlama
    public void setAutoCompleteItemNumber(){
        getItemsList();
        List<String> itemNumberList=productsList.stream().map(Product::getItemNumber).collect(Collectors.toList());
        ObservableList<String> observableItemList=FXCollections.observableArrayList(itemNumberList);
        TextFields.bindAutoCompletion(bill_item,observableItemList);
    } // Ürün numarasını otomatik doldurma
    public void comboBoxQuantity(){
        List<String> list=new ArrayList<>();
        for(String quantity:quantityList){
            list.add(quantity);
        }
        ObservableList comboList= FXCollections.observableArrayList(list);
        bill_quantity.setItems(comboList);
        purchase_quantity.setItems(comboList);
    } // Miktar seçme kutucuğunu ayarlama
    public void checkForPriceandQuantity(){
        if(!bill_price.getText().isBlank() && !bill_quantity.getSelectionModel().isEmpty()){
            bill_total_amount.setText(String.valueOf(Integer.parseInt(bill_price.getText())*Integer.parseInt(bill_quantity.getValue().toString())));
        }else{
            bill_total_amount.setText("0");
        }
    } // Toplam fiyatı otomatik doldurma ( Bill )
    public void checkPurchaseForPriceandQuantity(){
        if(!purchase_price.getText().isBlank()&& !purchase_quantity.getSelectionModel().isEmpty()){
            purchase_totalamount.setText(String.valueOf(Integer.parseInt(purchase_price.getText())*Integer.parseInt(purchase_quantity.getValue().toString())));
        }else{
            purchase_totalamount.setText("0");
        }
    } // Toplam fiyatı otomatik doldurma ( Purchase )
    public void getPriceOfTheItem(){
        try {
            Product product = productsList.stream().filter(prod -> prod.getItemNumber().equals(bill_item.getText())).findAny().get();
            bill_price.setText(String.valueOf((int) product.getPrice()));
        }catch (Exception err){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    } // Products tablosunda bulunan ürünün fiyatını otomatik doldurma

    public void onInputTextChanged(){
        bill_price.setOnKeyReleased(event-> checkForPriceandQuantity());
        bill_price.setOnKeyPressed(event-> checkForPriceandQuantity());
        bill_price.setOnKeyTyped(event-> checkForPriceandQuantity());
        bill_quantity.setOnAction(actionEvent -> checkForPriceandQuantity());
        bill_item.setOnKeyPressed(actionEvent ->{
            if(actionEvent.getCode().equals(KeyCode.ENTER)) {
                getPriceOfTheItem();
            }
        });
    } // Fiyatta değişiklik olduğunu kontrol etme ( Bill )

    public void onPurchaseInputTextChanged(){
        purchase_price.setOnKeyReleased(event-> checkPurchaseForPriceandQuantity());
        purchase_price.setOnKeyPressed(event-> checkPurchaseForPriceandQuantity());
        purchase_price.setOnKeyTyped(event-> checkPurchaseForPriceandQuantity());
        purchase_quantity.setOnAction(actionEvent -> checkPurchaseForPriceandQuantity());
    } // Fiyatta değişiklik olduğunu kontrol etme ( Purchase )
    public void addBillingData(){
        if(bill_item.getText().isBlank()||bill_quantity.getSelectionModel().isEmpty()||bill_price.getText().isBlank()||bill_total_amount.getText().isBlank()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen ürün numarası, miktar ve fiyat gibi zorunlu verileri doldurun.");
            alert.showAndWait();
            return;
        }

        if(productsList.stream().anyMatch(product -> product.getItemNumber().equals(bill_item.getText()))) {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO BILLING(item_number,quantity,price,total_amount)VALUES(?,?,?,?)";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bill_item.getText());
                preparedStatement.setInt(2, Integer.parseInt(bill_quantity.getValue().toString()));
                preparedStatement.setInt(3, Integer.parseInt(bill_price.getText()));
                preparedStatement.setInt(4, Integer.parseInt(bill_total_amount.getText()));
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    showBillingData();
                    billClearData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata");
                    alert.setHeaderText(null);
                    alert.setContentText("Lütfen ürün numarası, miktar ve fiyat gibi zorunlu verileri doldurun.\n");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("'"+bill_item.getText() + "' numaralı bir ürün bulunamadı.");
            alert.showAndWait();
            bill_item.clear();
        }
    } // Fatura ekleme

    public ObservableList<Billing> listBilligData(){
        ObservableList<Billing> billingList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM BILLING";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);


              Billing billingData;
              while (resultSet.next()){
              billingData=new Billing(resultSet.getString("item_number"),Integer.parseInt(resultSet.getString("quantity")),Double.parseDouble(resultSet.getString("price")),Double.parseDouble(resultSet.getString("total_amount")));
              billingList.addAll(billingData);
             }


        }catch (Exception err){
            err.printStackTrace();
        }
        return billingList;
    } // Faturaları listeleme

    public void calculateFinalAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) AS final_amount FROM billing";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                final_amount.setText(resultSet.getString("final_amount"));
            }

        }catch (Exception err){
            err.printStackTrace();
        }

    } // Tüm faturalardaki fiyatı toplama

    public void showBillingData(){
        ObservableList<Billing> billingList=listBilligData();
        col_bill_item_num.setCellValueFactory(new PropertyValueFactory<>("item_number"));
        col_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bill_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_bill_total_amt.setCellValueFactory(new PropertyValueFactory<>("total_amount"));

        billing_table.setItems(billingList);
        LocalDate date=LocalDate.now();
        bill_date.setValue(date);
        if(!billingList.isEmpty()){
         calculateFinalAmount();
        }else{
            final_amount.setText("0.00");
        }

    } // Faturayı ekrana yansıtma

    public void billClearCustomerData(){
        bill_name.setText("");
        bill_phone.setText("");
    } // Fatura sahibi bilgileri temizleme

    public void billClearData(){
        bill_item.clear();
        bill_quantity.setValue(null);
        bill_price.setText("");
        bill_total_amount.setText("");
    } // Fatura bilgileri temizleme

    public void selectBillingTableData(){
        int num=billing_table.getSelectionModel().getSelectedIndex();
        Billing billingData=billing_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }
        bill_item.setText(billingData.getItem_number());
        bill_price.setText(String.valueOf((int)billingData.getPrice()));
        bill_total_amount.setText(String.valueOf((int)billingData.getTotal_amount()));
    }
    public void updateSelectedBillingData() {
        connection = Database.getInstance().connectDB();
        String sql = "UPDATE billing SET quantity=?, price=?, total_amo unt=? WHERE item_number=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            // ComboBox'tan seçilen değeri kontrol et
            if (bill_quantity.getValue() != null) {
                int quantity = Integer.parseInt(bill_quantity.getValue().toString());
                double price = Double.parseDouble(bill_price.getText());
                double totalAmount = Double.parseDouble(bill_total_amount.getText());

                preparedStatement.setInt(1, quantity);
                preparedStatement.setDouble(2, price);
                preparedStatement.setDouble(3, totalAmount);
                preparedStatement.setString(4, bill_item.getText());

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    showBillingData();
                    billClearData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hata");
                    alert.setHeaderText(null);
                    alert.setContentText("Lütfen ürün numarası, miktar ve fiyat gibi zorunlu verileri doldurun.\n");
                    alert.showAndWait();
                }
            } else {
                // ComboBox'tan seçilen değer null ise kullanıcıyı bilgilendir
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen miktar seçiniz.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Sayısal dönüşüm hatası kontrolü
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen miktar, fiyat ve toplam tutar için geçerli değerler girin.");
            alert.showAndWait();
        } catch (Exception err) {
            err.printStackTrace();
        }
    } // Fatura bilgileri düzenleme

    public void deleteBillingData(){
        connection = Database.getInstance().connectDB();
        String sql;
        try {
            if(billing_table.getSelectionModel().isEmpty()){
                sql = "DELETE FROM BILLING";
                preparedStatement = connection.prepareStatement(sql);
            }else{
                sql="DELETE FROM BILLING WHERE item_number=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,billing_table.getSelectionModel().getSelectedItem().getItem_number());
            }
           int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showBillingData();
                billClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Veri yok!");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    } // Fatura silme
    public boolean saveCustomerDetails(){
        if(bill_phone.getText().isBlank() || bill_name.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen müşteri adını ve telefon numarasını doldurun.");
            alert.showAndWait();
            return false;
        }
        connection = Database.getInstance().connectDB();
        String sql="SELECT * FROM CUSTOMERS WHERE phone_number=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bill_phone.getText());
            resultSet= preparedStatement.executeQuery();
            if (!resultSet.next()) {
                String customerSql="INSERT INTO CUSTOMERS(name,phone_number) VALUES(?,?)";
                preparedStatement = connection.prepareStatement(customerSql);
                preparedStatement.setString(1,bill_name.getText());
                preparedStatement.setString(2,bill_phone.getText());
                int result= preparedStatement.executeUpdate();
                if(result>0){
                    showCustomerData();
                    return true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mesaj");
                    alert.setHeaderText(null);
                    alert.setContentText("Müşteri verileri kaydedilmedi. Lütfen müşteri adını ve telefon numarasını doğru girin.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }
    public void saveInvoiceDetails(){
        // GET CUSTOMER ID FOR MAPPING INVOICE RECORDS
        connection=Database.getInstance().connectDB();
        String sql="SELECT id FROM CUSTOMERS WHERE phone_number=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,bill_phone.getText());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                  String custId=resultSet.getString("id");
                  String getBillingDetails="SELECT * FROM BILLING";
                  preparedStatement=connection.prepareStatement(getBillingDetails);
                  resultSet=preparedStatement.executeQuery();
                  int count=0;
                  while (resultSet.next()){
                      String salesDetailsSQL="INSERT INTO sales(inv_num,item_number,cust_id,price,quantity,total_amount,date) VALUES(?,?,?,?,?,?,?)";
                      preparedStatement=connection.prepareStatement(salesDetailsSQL);
                      preparedStatement.setString(1,inv_num.getText());
                      preparedStatement.setString(2,resultSet.getString("item_number"));
                      preparedStatement.setInt(3,Integer.parseInt(custId));
                      preparedStatement.setDouble(4, resultSet.getDouble("price")); //preparedStatement.setBigDecimal(4, new BigDecimal(resultSet.getString("price")));
                      preparedStatement.setInt(5, resultSet.getInt("quantity")); //preparedStatement.setString(5,resultSet.getString("quantity"));
                      preparedStatement.setDouble(6, resultSet.getDouble("total_amount")); //preparedStatement.setBigDecimal(6, new BigDecimal(resultSet.getString("total_amount")));
                      preparedStatement.setDate(7, java.sql.Date.valueOf(bill_date.getValue()));   //preparedStatement.setString(7,bill_date.getValue().toString());
                      preparedStatement.executeUpdate();
                      count++;
                  }
                  if(count>0){
                      billClearCustomerData();
                      deleteBillingData();
                      showSalesData();
                      setInvoiceNum();
                      showDashboardData();
                      Alert alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Mesaj");
                      alert.setHeaderText(null);
                      alert.setContentText("Veriler satışlar tablosuna başarıyla kaydedildi. ");
                      alert.showAndWait();
                  }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen Ad ve Telefon Numarası gibi müşteri bilgilerini doğru şekilde doldurun.");
                alert.showAndWait();
            }
        }catch (Exception err){
            err.printStackTrace();
        }


    }

    public void billSave(){
        // Save Customer Details
        if(!saveCustomerDetails()) {
            return;
        }
        //Save Invoice Details in Sales Table and Reference Customer
        saveInvoiceDetails();

    }

    public void printBill(){
     connection=Database.getInstance().connectDB();
     String sql="SELECT * FROM sales s INNER JOIN customers c ON s.cust_id=c.id and s.inv_num=(SELECT MAX(inv_num) as inv_num FROM sales)";
     try{
         JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/Invoice.jrxml"));
         JRDesignQuery updateQuery=new JRDesignQuery();
         updateQuery.setText(sql);
         jasperDesign.setQuery(updateQuery);
         JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
         JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
         JasperViewer.viewReport(jasperPrint ,false);
     }catch (Exception err){
      err.printStackTrace();
     }
    }
    public void searchForBills(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("bills.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void customerClearData(){
        cust_field_name.setText("");
        cust_field_phone.setText("");
    }
    public ObservableList<Customer> listCustomerData(){
        ObservableList<Customer> customersList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM Customers";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);


            Customer customer;
            while (resultSet.next()){
                customer=new Customer(Integer.parseInt(resultSet.getString("id")),resultSet.getString("name"),resultSet.getString("phone_number"));
                customersList.addAll(customer);
            }


        }catch (Exception err){
            err.printStackTrace();
        }
        return customersList;
    }
    public void showCustomerData(){
        ObservableList<Customer> customerList=listCustomerData().sorted(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        });

        cust_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cust_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cust_col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_table.setItems(customerList);
    }
    public boolean checkForCustomerAvailability(){

        if (cust_field_name.getText().isEmpty() || cust_field_phone.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Müşteri ismi ve telefon numarası boş bırakılamaz.");
            alert.showAndWait();
            return false;
        }


        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM CUSTOMERS WHERE phone_number=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_phone.getText());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Müşteri zaten müşteri tablosunda mevcut.");
                alert.showAndWait();
                return false;
            }else {
              return true;
            }
        }catch (Exception err){
            err.printStackTrace();
        }

        return false;
    }
    public void addCustomerData(){
        if(!checkForCustomerAvailability()){
            return;
        }
        connection=Database.getInstance().connectDB();
        String sql="INSERT INTO CUSTOMERS(name,phone_number)VALUES(?,?)";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_name.getText());
            preparedStatement.setString(2,cust_field_phone.getText());
            int result=preparedStatement.executeUpdate();
            if(result>0){
                showCustomerData();
                customerClearData();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen isim ve telefon numarası gibi zorunlu verileri doldurun.");
                alert.showAndWait();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void selectCustomerTableData(){
        int num=customer_table.getSelectionModel().getSelectedIndex();
        Customer customerData=customer_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        cust_field_name.setText(customerData.getName());
        cust_field_phone.setText(customerData.getPhoneNumber());
    }

    public void updateCustomerData(){
        if(cust_field_phone.getText().isBlank() || cust_field_name.getText().isBlank() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen isim, telefon numarası gibi zorunlu verileri doldurun.");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();
        String sql = "UPDATE CUSTOMERS SET name=? WHERE phone_number=?";
        String sql2 = "UPDATE CUSTOMERS SET phone_number=? WHERE name=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_name.getText());
            preparedStatement.setString(2, cust_field_phone.getText());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showCustomerData();
                customerClearData();
                showSalesData();
            } else {
                try {
                    preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setString(1, cust_field_phone.getText());
                    preparedStatement.setString(2,cust_field_name.getText());
                    int result2 = preparedStatement.executeUpdate();
                    if (result2 > 0) {
                        showCustomerData();
                        customerClearData();
                        showSalesData();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Hata");
                        alert.setHeaderText(null);
                        alert.setContentText("Lütfen isim, telefon numarası gibi zorunlu verileri doldurun.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void deleteCustomerData(){
        if(customer_table.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen silinecek müşteriyi seçin.");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql="DELETE FROM CUSTOMERS WHERE phone_number=?";
        String sql2 = "DELETE FROM sales WHERE cust_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setInt(1,customer_table.getSelectionModel().getSelectedItem().getId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showSalesData();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer_table.getSelectionModel().getSelectedItem().getPhoneNumber());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showCustomerData();
                customerClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Müşteri tablosunda veri yok.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void printCustomersDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM customers";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/customers.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void getTotalSalesAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_sale_amount FROM sales";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_sale_amount");
                if (result == null) {
                    sales_total_amount.setText("0.00");
                }else{
                    sales_total_amount.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }
    public ObservableList<Sales> listSalesData(){
        ObservableList<Sales> salesList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM SALES s INNER JOIN CUSTOMERS c ON s.cust_id=c.id";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Sales sale;
            while (resultSet.next()){
                sale=new Sales(Integer.parseInt(resultSet.getString("id")),resultSet.getString("inv_num"),Integer.parseInt(resultSet.getString("cust_id")),resultSet.getString("name"),Double.parseDouble(resultSet.getString("price")),Integer.parseInt(resultSet.getString("quantity")),Double.parseDouble(resultSet.getString("total_amount")),resultSet.getString("date"),resultSet.getString("item_number"));
                salesList.addAll(sale);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return salesList;
    }
    public void showSalesData(){
        ObservableList<Sales> salesList=listSalesData();
        sales_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sales_col_inv_num.setCellValueFactory(new PropertyValueFactory<>("inv_num"));
        sales_col_cust_name.setCellValueFactory(new PropertyValueFactory<>("custName"));
        sales_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        sales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sales_col_total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        sales_col_date_of_sales.setCellValueFactory(new PropertyValueFactory<>("date"));
        sales_col_item_num.setCellValueFactory(new PropertyValueFactory<>("item_num"));
        sales_table.setItems(salesList);

        getTotalSalesAmount();
    }
    public void printSalesDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM sales s INNER JOIN customers c ON s.cust_id=c.id";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/sales_report.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void getTotalPurchaseAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_purchase_amount FROM purchase";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_purchase_amount");
                if (result == null) {
                    purchase_total_amount.setText("0.00");
                }else{
                    purchase_total_amount.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }
    public void printPurchaseDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM purchase";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/purchase_report.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public ObservableList<Purchase> listPurchaseData(){
        ObservableList<Purchase> purchaseList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM Purchase";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Purchase purchase;
            while (resultSet.next()){
                purchase=new Purchase(Integer.parseInt(resultSet.getString("id")),resultSet.getString("invoice"),resultSet.getString("invoice_no"),resultSet.getString("shop_and_address"),Integer.parseInt(resultSet.getString("total_items")),Double.parseDouble(resultSet.getString("total_amount")),resultSet.getString("date_of_purchase"));
                purchaseList.addAll(purchase);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return purchaseList;
    }
    public void showPurchaseData(){
        ObservableList<Purchase> purchaseList=listPurchaseData();
        purchase_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchase_col_invoice.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        purchase_col_invoice_no.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));
        purchase_col_shop_details.setCellValueFactory(new PropertyValueFactory<>("shopDetails"));
        purchase_col_total_items.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        purchase_col_total_amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        purchase_col_date_of_purchase.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));
        purchase_table.setItems(purchaseList);
        getTotalPurchaseAmount();
        LocalDate date=LocalDate.now();
        purchase_date.setValue(date);
    }

    public void getTotalPurchase(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_items) as total_purchase FROM PURCHASE";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_purchase");
                if (result == null) {
                    dash_total_purchase.setText("0");
                }else{
                    dash_total_purchase.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    public void getTotalSales(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(quantity) as total_sale FROM sales";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_sale");
                if (result == null) {
                    dash_total_sold.setText("0");
                }else{
                    dash_total_sold.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    public void getTotalStocks(){
        int totalPurchase=Integer.parseInt(dash_total_purchase.getText());
        int total_sold= Integer.parseInt(dash_total_sold.getText());
        int totalStockLeft=totalPurchase-total_sold;
        dash_total_stocks.setText(String.valueOf(totalStockLeft));
    }

    public String translateMonth() {
        LocalDate date=LocalDate.now();
        String monthName = date.getMonth().name();

        switch (monthName) {
            case "JANUARY":
                monthName = "OCAK";
                break;
            case "FEBRUARY":
                monthName = "ŞUBAT";
                break;
            case "MARCH":
                monthName = "MART";
                break;
            case "APRİL":
                monthName = "NİSAN";
                break;
            case "MAY":
                monthName = "MAYIS";
                break;
            case "JUNE":
                monthName = "HAZİRAN";
                break;
            case "JULY":
                monthName = "TEMMUZ";
                break;
            case "AUGUST":
                monthName = "AĞUSTOS";
                break;
            case "SEPTEMBER":
                monthName = "EYLÜL";
                break;
            case "OCTOBER":
                monthName = "EKİM";
                break;
            case "NOVEMBER":
                monthName = "KASIM";
                break;
            case "DECEMBER":
                monthName = "ARALIK";
                break;
        }
        return monthName;
    }

    public void getSalesDetailsOfThisMonth(){
        LocalDate date=LocalDate.now();



        String month=Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        String concatYear = year + "-" + month;
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_sales_this_month FROM SALES WHERE TO_CHAR(date, 'YYYY-MM')=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,concatYear);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String result=resultSet.getString("total_sales_this_month");
                if (result == null) {
                    dash_total_sales_this_month.setText("0.00");
                }else{
                    dash_total_sales_this_month.setText(result);
                }
                dash_total_sales_this_month_name.setText(translateMonth());
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void getItemSoldThisMonth(){
        LocalDate date=LocalDate.now();
        String monthName=date.getMonth().toString();
        String month= Integer.toString(date.getMonthValue());
        String year = Integer.toString(date.getYear());
        String concatDate = year + "-" + month;
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(quantity) as total_items_sold_this_month FROM SALES WHERE TO_CHAR(date, 'YYYY-MM')=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,concatDate);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String result=resultSet.getString("total_items_sold_this_month");
                if (result == null) {
                    dash_total_items_sold_this_month.setText("0");
                }else{
                    dash_total_items_sold_this_month.setText(result);
                }
                dash_total_sales_items_this_month_name.setText(translateMonth());
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void addPurchaseData(){
        if(purchase_name.getText().isBlank()||purchase_quantity.getSelectionModel().isEmpty()||purchase_price.getText().isBlank()||purchase_details.getText().isBlank()|prc_invoice_no.getText().isBlank()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen ürün numarası, miktar ve fiyat gibi zorunlu verileri doldurun.");
            alert.showAndWait();
            return;
        }
        String purchaseName = prc_invoice_no.getText();
        int purchaseQuantity = Integer.parseInt(purchase_quantity.getValue().toString());
        int purchasePrice = Integer.parseInt(purchase_price.getText());




        connection=Database.getInstance().connectDB();
        String sql="INSERT INTO PURCHASE(invoice,shop_and_address,total_items,total_amount,date_of_purchase,invoice_no)VALUES(?,?,?,?,?,?)";
        try {
            purchase_total_amount.setText(String.valueOf(Integer.parseInt(purchase_price.getText())*Integer.parseInt(purchase_quantity.getValue().toString())));
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, purchase_name.getText());
            preparedStatement.setString(2, purchase_details.getText());
            preparedStatement.setInt(3,purchaseQuantity);
            preparedStatement.setDouble(4, Double.parseDouble(purchase_total_amount.getText()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(purchase_date.getValue()));
            preparedStatement.setString(6, purchaseName);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showPurchaseData();
                purchaseClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Lütfen ürün numarası, miktar ve fiyat gibi zorunlu verileri doldurun.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        connection=Database.getInstance().connectDB();


        String sql2="INSERT INTO PRODUCTS(item_number,quantity,price)VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, purchaseName);
            preparedStatement.setInt(2,purchaseQuantity);
            preparedStatement.setInt(3,purchasePrice);
            preparedStatement.executeUpdate();

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void deletePurchaseData(){
        connection = Database.getInstance().connectDB();
        String sql;
        try {
            if(!purchase_table.getSelectionModel().isEmpty()){
                sql="DELETE FROM PURCHASE WHERE id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,purchase_table.getSelectionModel().getSelectedItem().getId());
            }
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showPurchaseData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Veri yok!");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void purchaseClearData(){
        prc_invoice_no.clear();
        purchase_name.clear();
        purchase_details.clear();
        purchase_quantity.setValue(null);
        purchase_price.clear();
        purchase_totalamount.clear();
        LocalDate date=LocalDate.now();
        purchase_date.setValue(date);
    }

    public void showDashboardData(){
     getTotalPurchase();
     getTotalSales();
     getTotalStocks();
     getSalesDetailsOfThisMonth();
     getItemSoldThisMonth();
    }
    public void signOut(){
        signout_btn.getScene().getWindow().hide();
        try{
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();

        setUsername();
        activateDashboard();

//      DASHBOARD PANE
        showDashboardData();

//      BILLING PANE
        setAutoCompleteItemNumber();
        comboBoxQuantity();
        setInvoiceNum();
        showBillingData();

//      CUSTOMER PANE
        showCustomerData();

//      SALES PANE
        showSalesData();

//      Purchase Pane
        showPurchaseData();
    }
}
