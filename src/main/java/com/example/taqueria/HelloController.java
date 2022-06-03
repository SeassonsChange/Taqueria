package com.example.taqueria;

import com.example.taqueria.Base.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private Button btnBorrar;


    CategoriaDAO cat=new CategoriaDAO(Conexion.getConnection());
    AlimentoDAO alimentosDAO=new AlimentoDAO(Conexion.getConnection());
    ClienteDAO clienteDAO=new ClienteDAO(Conexion.getConnection());
    String img[]={"tacos de pastor","tacos chorizo","tacos lengua","tortas de pastor","torta cubana","torta choriqueso","torta carne asada","torta milanesa","papa con pastor y queso","papa con chorizo y queso","papa con arrachera y queso","quesadilla sencilla","quesadilla con pastor","quesadilla de costilla","quesadilla con chorizo",
            "Coca Cola 600ml","Mundet 600ml","Agua Horchata 1lt","Agua jamaica 1lt"};
    String tipo=".jpeg";
    int cont=0,id_cliente,ireg=1,icol=1;
    @FXML
    Button categoriaButton,TOP,Tacos,TOTAL,Orders,Prueba,Prueba1,Prueba2,Prueba3,Prueba4,Prueba5,BORRAR;
    @FXML
    TableView tblCat,tblOrden,tblAli;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BORRAR.setDisable(true);
        TOP.setDisable(false);
        Tacos.setDisable(true);
        Orders.setDisable(true);
        TOTAL.setDisable(true);
        categoriaButton.setDisable(true);
        ImageView imageView= new ImageView("tacos de pastor.jpeg");
        imageView.setFitHeight(100);
        imageView.setFitWidth(140);
        Prueba.setGraphic(imageView);
        Prueba1.setGraphic(imageView);
        Prueba2.setGraphic(imageView);
        Prueba3.setGraphic(imageView);
        Prueba4.setGraphic(imageView);
        Prueba5.setGraphic(imageView);
        Prueba.setVisible(false);
        Prueba1.setVisible(false);
        Prueba2.setVisible(false);
        Prueba3.setVisible(false);
        Prueba4.setVisible(false);
        Prueba5.setVisible(false);

        tblCat.setItems(cat.getAll());
        tblCat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TOP.setDisable(true);
                Tacos.setDisable(false);
                Categoria catClick= (Categoria) tblCat.getSelectionModel().getSelectedItem();
                int cat=catClick.getId_Categoria();
                tblAli.setItems(alimentosDAO.mostrar(cat));
            }
        });
        tblAli.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BORRAR.setDisable(false);
                Orders.setDisable(false);
                categoriaButton.setDisable(false);
                if(cont==0){
                    clienteDAO.crear();
                }
                cont++;
                id_cliente=clienteDAO.obtenerid();
                Alimento alimentosClick= (Alimento) tblAli.getSelectionModel().getSelectedItem();
                String nameB=alimentosClick.getNombre();
                int id_C=alimentosClick.getId_Categoria();
                Buscar(nameB,id_C);
                int id_alimento =alimentosClick.getId_Alimento();
                tblOrden.setItems(alimentosDAO.Insert(id_alimento,id_cliente));
                BORRAR.setOnAction(actionEvent -> {
                    alimentosDAO.borrar();
                    tblOrden.refresh();

                });

            }
        });
    }

    private void Buscar(String nameB, int id_C) {
        for(int i=0;i<img.length;i++){
            System.out.println(img[i]+" == "+nameB);
            if(img[i].equalsIgnoreCase(nameB)){
                ImageView imageView= new ImageView(img[i]+tipo);
                imageView.setFitHeight(90);
                imageView.setFitWidth(130);
                System.out.println("ENTRO");
                if(id_C==1){
                    Prueba.setGraphic(imageView);
                    Prueba.setVisible(true);
                }else {
                    if(id_C==2){
                        Prueba1.setGraphic(imageView);
                        Prueba1.setVisible(true);
                    }else {
                        if(id_C==3){
                            Prueba2.setGraphic(imageView);
                            Prueba2.setVisible(true);
                        }else {
                            if(id_C==4){
                                Prueba3.setGraphic(imageView);
                                Prueba3.setVisible(true);
                            }else {
                                if(id_C==5){
                                    Prueba4.setGraphic(imageView);
                                    Prueba4.setVisible(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Categoria(ActionEvent actionEvent) {
        id_cliente=clienteDAO.obtenerid();
        int paga=alimentosDAO.pagar(id_cliente);
        System.out.println("PAGAR : "+paga);
        TOTAL.setText("TOTAL A PAGAR : "+paga);
    }
}