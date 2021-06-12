package sample;

import com.sun.javafx.geom.PickRay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class Controller3Window {
    private TableView.TableViewSelectionModel<Customer> customerTableViewSelectionModel;
    private TableView.TableViewSelectionModel<Order_info> order_infoTableViewSelectionModel;
    private TableView.TableViewSelectionModel<Product> productTableViewSelectionModel;
    private TableView.TableViewSelectionModel<Order_details> order_detailsTableViewSelectionModel;
    private ObservableList<Customer> listOfCustomers = FXCollections.observableArrayList();
    private ObservableList<Order_info> listOfOrdersInfo = FXCollections.observableArrayList();
    private ObservableList<Product> listOfProducts = FXCollections.observableArrayList();
    private ObservableList<Order_details> listOfOrdersDetails = FXCollections.observableArrayList();


    //customer
    @FXML
    private TableView<Customer> customer;
    @FXML
    private TableColumn<Customer, Integer> id_c;
    @FXML
    private TableColumn<Customer, String> first_name;
    @FXML
    private TableColumn<Customer, String> last_name;
    @FXML
    private TableColumn<Customer, Integer> discount_card;
    @FXML
    private TableColumn<Customer, Integer> mobile_phone;
    @FXML
    private TableColumn<Customer, String> email;

    //order_info
    @FXML
    private TableView<Order_info> order_info;
    @FXML
    private TableColumn<Order_info, Integer> id_oi;
    @FXML
    private TableColumn<Order_info, Integer> customer_id;
    @FXML
    private TableColumn<Order_info, Float> price_o;
    @FXML
    private TableColumn<Order_info, Payment> payment;

    //product
    @FXML
    private TableView<Product> product;
    @FXML
    private TableColumn<Product, Integer> id_p;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Float> price_p;
    @FXML
    private TableColumn<Product, Size> size;

    //order_details
    @FXML
    private TableView<Order_details> order_details;
    @FXML
    private TableColumn<Order_details, Integer> id_od;
    @FXML
    private TableColumn<Order_details, Integer> order_id;
    @FXML
    private TableColumn<Order_details, Integer> product_id;


    @FXML
    private Button insert;
    @FXML
    private Button delete;
    @FXML
    private Button update;

    @FXML
    private Label dbName;

    private static DatabaseAction databaseAction;

    public void setDatabaseAction(DatabaseAction databaseAction) {
        this.databaseAction = databaseAction;
    }

    @FXML
    void addCustomer(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("INSERT INTO customer VALUES");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField idC = new TextField();
        idC.setPromptText("id");
        TextField firstName = new TextField();
        firstName.setPromptText("first_name");
        TextField lastName = new TextField();
        lastName.setPromptText("last_name");
        TextField discountCard = new TextField();
        discountCard.setPromptText("discount_card");
        TextField mobilePhone = new TextField();
        mobilePhone.setPromptText("mobile_phone");
        TextField Email = new TextField();
        Email.setPromptText("email");
        grid.add(idC, 1, 0);
        grid.add(firstName, 2, 0);
        grid.add(lastName, 3, 0);
        grid.add(discountCard, 1, 1);
        grid.add(mobilePhone, 2, 1);
        grid.add(Email, 3, 1);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                databaseAction.insertCustomer(idC.getText(), firstName.getText(), lastName.getText(), discountCard.getText(), mobilePhone.getText(), Email.getText());
                id_c.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
                first_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("first_name"));
                last_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("last_name"));
                discount_card.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("discount_card"));
                mobile_phone.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("mobile_phone"));
                email.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
                listOfCustomers.add(new Customer(Integer.parseInt(idC.getText()), firstName.getText(), lastName.getText(), Integer.parseInt(discountCard.getText()), Integer.parseInt(mobilePhone.getText()), Email.getText()));
                customer.setItems(listOfCustomers);
                dialog.close();
            } catch (NumberFormatException | SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    void addOrderInfo(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("INSERT INTO order_info VALUES");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField IdO = new TextField();
        IdO.setPromptText("id");
        ObservableList<Integer> cus = FXCollections.observableArrayList();
        for (int j = 0; j < listOfCustomers.size(); j++)
            cus.add(listOfCustomers.get(j).getId());
        ChoiceBox<Integer> customerId = new ChoiceBox<Integer>(cus);
        ObservableList<String> p = FXCollections.observableArrayList("credit_card", "cash");
        ChoiceBox<String> pay = new ChoiceBox<String>(p);
        grid.add(IdO, 1, 0);
        grid.add(customerId, 2, 0);
        grid.add(pay, 1, 1);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                Payment pa = null;
                if (pay.getSelectionModel().getSelectedItem() == "credit_card")
                    pa = Payment.CREDIT_CARD;
                else if (pay.getSelectionModel().getSelectedItem() == "cash")
                    pa = Payment.CREDIT_CARD;
                    databaseAction.insertOrderInfo(IdO.getText(), customerId.getSelectionModel().getSelectedItem().toString(), "0", pay.getSelectionModel().getSelectedItem());
                id_oi.setCellValueFactory(new PropertyValueFactory<Order_info, Integer>("id"));
                customer_id.setCellValueFactory(new PropertyValueFactory<Order_info, Integer>("customer_id"));
                price_o.setCellValueFactory(new PropertyValueFactory<Order_info, Float>("price"));
                payment.setCellValueFactory(new PropertyValueFactory<Order_info, Payment>("payment"));
                int index = Integer.parseInt(IdO.getText());
                listOfOrdersInfo.add(new Order_info(index, customerId.getSelectionModel().getSelectedItem(), 0, pa));
                order_info.setItems(listOfOrdersInfo);
                dialog.close();
            } catch (NumberFormatException | SQLException exception) {
                    System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    void addProduct(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("INSERT INTO product VALUES");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField ID = new TextField();
        ID.setPromptText("id");
        TextField Name = new TextField();
        Name.setPromptText("name");
        TextField Price = new TextField();
        Price.setPromptText("price");
        ObservableList<Size> s = FXCollections.observableArrayList(Size.XS, Size.S, Size.M, Size.L, Size.XL);
        ChoiceBox<Size> sizeC = new ChoiceBox<Size>(s);
        grid.add(ID, 1, 0);
        grid.add(Name, 2, 0);
        grid.add(Price, 1, 1);
        grid.add(sizeC, 2, 1);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                String si = sizeC.getSelectionModel().getSelectedItem().toString();
                databaseAction.insertProduct(ID.getText(), Name.getText(), Price.getText(), si);
                id_p.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
                name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
                price_p.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
                size.setCellValueFactory(new PropertyValueFactory<Product, Size>("size"));
                int index = Integer.parseInt(ID.getText());
                listOfProducts.add(new Product(index, Name.getText(), Float.parseFloat(Price.getText()), sizeC.getSelectionModel().getSelectedItem()));
                product.setItems(listOfProducts);
                dialog.close();
            } catch (NumberFormatException | SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    void addOrder_details(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("INSERT INTO customer VALUES");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField ID = new TextField();
        ID.setPromptText("id");
        ObservableList<Integer> or = FXCollections.observableArrayList();
        for (int j = 0; j < listOfOrdersInfo.size(); j++)
            or.add(listOfOrdersInfo.get(j).getId());
        ChoiceBox<Integer> orderId = new ChoiceBox<Integer>(or);
        ObservableList<Integer> pr = FXCollections.observableArrayList();
        for (int j = 0; j < listOfProducts.size(); j++)
            pr.add(listOfProducts.get(j).getId());
        ChoiceBox<Integer> productId = new ChoiceBox<Integer>(pr);
        grid.add(ID, 1, 0);
        grid.add(orderId, 2, 0);
        grid.add(productId, 1, 1);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                databaseAction.insertOrderDetails(ID.getText(), orderId.getSelectionModel().getSelectedItem().toString(), productId.getSelectionModel().getSelectedItem().toString());
                id_od.setCellValueFactory(new PropertyValueFactory<Order_details, Integer>("id"));
                order_id.setCellValueFactory(new PropertyValueFactory<Order_details, Integer>("order_id"));
                product_id.setCellValueFactory(new PropertyValueFactory<Order_details, Integer>("product_id"));
                int index = Integer.parseInt(ID.getText());
                listOfOrdersDetails.add(new Order_details(index, orderId.getSelectionModel().getSelectedItem(), productId.getSelectionModel().getSelectedItem()));
                order_details.setItems(listOfOrdersDetails);
                Order_details temp = listOfOrdersDetails.get(Integer.parseInt(ID.getText()) - 1);
                Order_info temp_o = listOfOrdersInfo.get(temp.getOrder_id() - 1);
                Product temp_p = listOfProducts.get(temp.getProduct_id() - 1);
                temp_o.setPrice(temp_o.getPrice() + temp_p.getPrice());
                listOfOrdersInfo.remove(temp_o.getId() - 1);
                listOfOrdersInfo.add(temp_o.getId() - 1, temp_o);
                order_info.setItems(listOfOrdersInfo);
                dialog.close();
            } catch (NumberFormatException | SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    public void deleteCustomer(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("DELETE FROM customer");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("Id");
        grid.add(id, 1, 0);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                databaseAction.delete("customer", id.getText());
                listOfCustomers.remove(Integer.parseInt(id.getText()) - 1);
                customer.setItems(listOfCustomers);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
            dialog.close();
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    public void deleteOrderInfo(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("DELETE FROM order_info");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("Id");
        grid.add(id, 1, 0);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                databaseAction.delete("order_info", id.getText());
                listOfOrdersInfo.remove(Integer.parseInt(id.getText()) - 1);
                order_info.setItems(listOfOrdersInfo);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
            dialog.close();
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    public void deleteProduct(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("DELETE FROM product");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("Id");
        grid.add(id, 1, 0);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                databaseAction.delete("product", id.getText());
                listOfProducts.remove(Integer.parseInt(id.getText()) - 1);
                product.setItems(listOfProducts);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
            dialog.close();
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    public void deleteOrderDetails(javafx.event.ActionEvent event) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("DELETE FROM info_details");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("Id");
        grid.add(id, 1, 0);
        Button ok = new Button();
        grid.add(ok, 1, 3);
        ok.setText("OK");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        ok.setOnAction(actionEvent -> {
            try {
                Order_details temp = listOfOrdersDetails.get(Integer.parseInt(id.getText()) - 1);
                Order_info temp_o = listOfOrdersInfo.get(temp.getOrder_id() - 1);
                Product temp_p = listOfProducts.get(temp.getProduct_id() - 1);
                temp_o.setPrice(temp_o.getPrice() - temp_p.getPrice());
                listOfOrdersInfo.remove(temp_o.getId() - 1);
                listOfOrdersInfo.add(temp_o.getId() - 1, temp_o);
                order_info.setItems(listOfOrdersInfo);
                databaseAction.delete("info_details", id.getText());
                listOfOrdersDetails.remove(Integer.parseInt(id.getText()) - 1);
                order_details.setItems(listOfOrdersDetails);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                dialog.setTitle("Please make sure the input is correct");
            }
            dialog.close();
        });
        dialog.getDialogPane().setContent(grid);
        dialog.show();
    }

    @FXML
    private void updateCustomer(javafx.event.ActionEvent event) {
        if (!listOfCustomers.isEmpty()) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("UPDATE customer");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            ObservableList<Integer> id_index = FXCollections.observableArrayList();
            for (int j = 0; j < listOfCustomers.size(); j++)
                id_index.add(listOfCustomers.get(j).getId());
            ChoiceBox<Integer> ID = new ChoiceBox<Integer>(id_index);
            ObservableList<String> column = FXCollections.observableArrayList("first_name", "last_name", "discount_card", "mobile_phone", "email");
            ChoiceBox<String> Column = new ChoiceBox<String>(column);
            TextField newValue = new TextField();
            newValue.setPromptText("new value");
            grid.add(ID, 1, 0);
            grid.add(Column, 2, 0);
            grid.add(newValue, 1, 1);
            Button ok = new Button();
            grid.add(ok, 1, 3);
            ok.setText("OK");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            ok.setOnAction(actionEvent -> {
                try {
                    Customer change = listOfCustomers.get(ID.getSelectionModel().getSelectedItem() - 1);
                    if (Column.getSelectionModel().getSelectedItem() == "first_name") {
                        databaseAction.update("customer", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        change.setFirst_name(newValue.getText());
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "last_name") {
                        databaseAction.update("customer", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        change.setLast_name(newValue.getText());
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "discount_card") {
                        databaseAction.update("customer", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setDiscount_card(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "mobile_phone") {
                        databaseAction.update("customer", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setMobile_phone(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "email") {
                        databaseAction.update("customer", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        change.setEmail(newValue.getText());
                    }
                    listOfCustomers.remove(ID.getSelectionModel().getSelectedItem() - 1);
                    listOfCustomers.add(ID.getSelectionModel().getSelectedItem() - 1, change);
                    dialog.close();
                } catch (SQLException | NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    dialog.setTitle("Please make sure the input is correct");
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.show();
        }
    }

    @FXML
    private void updateOrderInfo(javafx.event.ActionEvent event) {
        if (!listOfOrdersInfo.isEmpty()) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("UPDATE order_info");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            ObservableList<Integer> id_index = FXCollections.observableArrayList();
            for (int j = 0; j < listOfOrdersInfo.size(); j++)
                id_index.add(listOfOrdersInfo.get(j).getId());
            ChoiceBox<Integer> ID = new ChoiceBox<Integer>(id_index);
            ObservableList<String> column = FXCollections.observableArrayList("customer_id", "price", "payment");
            ChoiceBox<String> Column = new ChoiceBox<String>(column);
            TextField newValue = new TextField();
            newValue.setPromptText("new value");
            grid.add(ID, 1, 0);
            grid.add(Column, 2, 0);
            grid.add(newValue, 1, 1);
            Button ok = new Button();
            grid.add(ok, 1, 3);
            ok.setText("OK");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            ok.setOnAction(actionEvent -> {
                try {
                    Order_info change = listOfOrdersInfo.get(ID.getSelectionModel().getSelectedItem() - 1);
                    if (Column.getSelectionModel().getSelectedItem() == "customer_id") {
                        databaseAction.update("order_info", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setCustomer_id(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "price") {
                        databaseAction.update("order_info", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setPrice(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "payment") {
                        databaseAction.update("order_info", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        if (newValue.getText() == "credit_card")
                            change.setPayment(Payment.CREDIT_CARD);
                        else
                            change.setPayment(Payment.CASH);
                    }
                    listOfOrdersInfo.remove(ID.getSelectionModel().getSelectedItem() - 1);
                    listOfOrdersInfo.add(ID.getSelectionModel().getSelectedItem() - 1, change);
                    dialog.close();
                } catch (SQLException | NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    dialog.setTitle("Please make sure the input is correct");
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.show();
        }
    }

    @FXML
    private void updateProduct(javafx.event.ActionEvent event) {
        if (!listOfProducts.isEmpty()) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("UPDATE product");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            ObservableList<Integer> id_index = FXCollections.observableArrayList();
            for (int j = 0; j < listOfProducts.size(); j++)
                id_index.add(listOfProducts.get(j).getId());
            ChoiceBox<Integer> ID = new ChoiceBox<Integer>(id_index);
            ObservableList<String> column = FXCollections.observableArrayList("name", "price", "size");
            ChoiceBox<String> Column = new ChoiceBox<String>(column);
            TextField newValue = new TextField();
            newValue.setPromptText("new value");
            grid.add(ID, 1, 0);
            grid.add(Column, 2, 0);
            grid.add(newValue, 1, 1);
            Button ok = new Button();
            grid.add(ok, 1, 3);
            ok.setText("OK");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            ok.setOnAction(actionEvent -> {
                try {
                    Product change = listOfProducts.get(ID.getSelectionModel().getSelectedItem() - 1);
                    if (Column.getSelectionModel().getSelectedItem() == "name") {
                        databaseAction.update("product", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        change.setName(newValue.getText());
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "price") {
                        databaseAction.update("product", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setPrice(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "size") {
                        databaseAction.update("product", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), false);
                        if (newValue.getText() == "XS")
                            change.setSize(Size.XS);
                        else if (newValue.getText() == "S")
                            change.setSize(Size.S);
                        else if (newValue.getText() == "M")
                            change.setSize(Size.M);
                        else if (newValue.getText() == "L")
                            change.setSize(Size.L);
                        else if (newValue.getText() == "XL")
                            change.setSize(Size.XL);
                    }
                    listOfProducts.remove(ID.getSelectionModel().getSelectedItem() - 1);
                    listOfProducts.add(ID.getSelectionModel().getSelectedItem() - 1, change);
                    dialog.close();
                } catch (SQLException | NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    dialog.setTitle("Please make sure the input is correct");
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.show();
        }
    }

    @FXML
    private void updateOrderDetails(javafx.event.ActionEvent event  ) {
        if (!listOfOrdersDetails.isEmpty()) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("UPDATE order_detail");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            ObservableList<Integer> id_index = FXCollections.observableArrayList();
            for (int j = 0; j < listOfOrdersDetails.size(); j++)
                id_index.add(listOfOrdersDetails.get(j).getId());
            ChoiceBox<Integer> ID = new ChoiceBox<Integer>(id_index);
            ObservableList<String> column = FXCollections.observableArrayList("order_id", "product_id");
            ChoiceBox<String> Column = new ChoiceBox<String>(column);
            TextField newValue = new TextField();
            newValue.setPromptText("new value");
            grid.add(ID, 1, 0);
            grid.add(Column, 2, 0);
            grid.add(newValue, 1, 1);
            Button ok = new Button();
            grid.add(ok, 1, 3);
            ok.setText("OK");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            ok.setOnAction(actionEvent -> {
                try {
                    Order_details change = listOfOrdersDetails.get(ID.getSelectionModel().getSelectedItem() - 1);
                    if (Column.getSelectionModel().getSelectedItem() == "order_id") {
                        databaseAction.update("order_details", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setOrder_id(Integer.parseInt(newValue.getText()));
                    }
                    else if (Column.getSelectionModel().getSelectedItem() == "product_id") {
                        databaseAction.update("order_details", ID.getSelectionModel().getSelectedItem(), Column.getSelectionModel().getSelectedItem().toString(), newValue.getText(), true);
                        change.setProduct_id(Integer.parseInt(newValue.getText()));
                    }
                    listOfOrdersDetails.remove(ID.getSelectionModel().getSelectedItem() - 1);
                    listOfOrdersDetails.add(ID.getSelectionModel().getSelectedItem() - 1, change);
                    dialog.close();
                } catch (SQLException | NumberFormatException exception) {
                    System.out.println(exception.getMessage());
                    dialog.setTitle("Please make sure the input is correct");
                }
            });
            dialog.getDialogPane().setContent(grid);
            dialog.show();
        }

    }

    public void switchToScene2(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondWindow.fxml"));
        Parent home_page_parent = loader.load();
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }


    public void displayBDName(String DBName) {
        dbName.setText(DBName);
    }


    @FXML
    void initialize() {

    }
}
