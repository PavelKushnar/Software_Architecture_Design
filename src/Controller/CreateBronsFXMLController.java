package Controller;

import buisness_logic.Bron;
import facade.Facade;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateBronsFXMLController implements Initializable, ControllerWithIdentificator {

    //если место 0 то его можно заьронировать
    //если место 1 то оно уже забронировано кемто
    // если 2, то его пытаются забронировать в данный момент

    private int[][] graphint = new int[7][17];

    String login;
    String idseans;
    Facade facade;

    Image imagenull = new Image("/resources/mestonull.png");
    Image imagebron = new Image("/resources/mestoBron.png");
    Image imagebuy = new Image("/resources/mestoBuyed.png");
    @FXML
    private ImageView[][] graphim = new ImageView[7][17];

    @FXML
    private ImageView imM71;

    @FXML
    private ImageView imM72;

    @FXML
    private ImageView imM73;

    @FXML
    private ImageView imM74;

    @FXML
    private ImageView imM75;

    @FXML
    private ImageView imM76;

    @FXML
    private ImageView imM77;

    @FXML
    private ImageView imM78;

    @FXML
    private ImageView imM79;

    @FXML
    private ImageView imM710;

    @FXML
    private ImageView imM711;

    @FXML
    private ImageView imM712;

    @FXML
    private ImageView imM713;

    @FXML
    private ImageView imM714;

    @FXML
    private ImageView imM715;

    @FXML
    private ImageView imM716;

    @FXML
    private ImageView imM717;

    @FXML
    private ImageView imM61;

    @FXML
    private ImageView imM62;

    @FXML
    private ImageView imM63;

    @FXML
    private ImageView imM64;

    @FXML
    private ImageView imM65;

    @FXML
    private ImageView imM66;

    @FXML
    private ImageView imM67;

    @FXML
    private ImageView imM68;

    @FXML
    private ImageView imM69;

    @FXML
    private ImageView imM610;

    @FXML
    private ImageView imM611;

    @FXML
    private ImageView imM612;

    @FXML
    private ImageView imM51;

    @FXML
    private ImageView imM52;

    @FXML
    private ImageView imM53;

    @FXML
    private ImageView imM54;

    @FXML
    private ImageView imM55;

    @FXML
    private ImageView imM56;

    @FXML
    private ImageView imM57;

    @FXML
    private ImageView imM58;

    @FXML
    private ImageView imM59;

    @FXML
    private ImageView imM510;

    @FXML
    private ImageView imM511;

    @FXML
    private ImageView imM512;

    @FXML
    private ImageView imM513;

    @FXML
    private ImageView imM41;

    @FXML
    private ImageView imM42;

    @FXML
    private ImageView imM43;

    @FXML
    private ImageView imM44;

    @FXML
    private ImageView imM45;

    @FXML
    private ImageView imM46;

    @FXML
    private ImageView imM47;

    @FXML
    private ImageView imM48;

    @FXML
    private ImageView imM49;

    @FXML
    private ImageView imM410;

    @FXML
    private ImageView imM411;

    @FXML
    private ImageView imM412;

    @FXML
    private ImageView imM31;

    @FXML
    private ImageView imM32;

    @FXML
    private ImageView imM33;

    @FXML
    private ImageView imM34;

    @FXML
    private ImageView imM35;

    @FXML
    private ImageView imM36;

    @FXML
    private ImageView imM37;

    @FXML
    private ImageView imM38;

    @FXML
    private ImageView imM39;

    @FXML
    private ImageView imM310;

    @FXML
    private ImageView imM311;

    @FXML
    private ImageView imM312;

    @FXML
    private ImageView imM313;

    @FXML
    private ImageView imM21;

    @FXML
    private ImageView imM22;

    @FXML
    private ImageView imM23;

    @FXML
    private ImageView imM24;

    @FXML
    private ImageView imM25;

    @FXML
    private ImageView imM26;

    @FXML
    private ImageView imM27;

    @FXML
    private ImageView imM28;

    @FXML
    private ImageView imM29;

    @FXML
    private ImageView imM210;

    @FXML
    private ImageView imM211;

    @FXML
    private ImageView imM212;

    @FXML
    private ImageView imM213;

    @FXML
    private ImageView imM11;

    @FXML
    private ImageView imM12;

    @FXML
    private ImageView imM13;

    @FXML
    private ImageView imM14;

    @FXML
    private ImageView imM15;

    @FXML
    private ImageView imM16;

    @FXML
    private ImageView imM17;

    @FXML
    private ImageView imM18;

    @FXML
    private ImageView imM19;

    @FXML
    private ImageView imM110;

    @FXML
    private ImageView imM111;

    @FXML
    private ImageView imM112;

    @FXML
    private ImageView imM113;

    @FXML
    private Button btnBron;

    @FXML
    private Label lblAnswer;
        @FXML
    private Label lblAnswer1;

    @FXML
    private Label lblCost;

    int findnum(int y, int yryad, int kol) {
        //yryad = 81;
        int yryadold = yryad;
        int num = 108;
        int i_26 = 0;
        int i_25 = 0;
        for (int i = 1; i < kol; i++) {
            if (i % 2 == 0) {
                i_25++;
            } else {
                i_26++;
            }

            if (y > yryadold && y < yryad + (25 * i_25) + (26 * i_26)) {
                num = i - 1;
            }
            yryadold = yryad + (25 * i_25) + (26 * i_26);
            ////System.out.println("yryad "+ yryad);
        }
        return num;
    }

    @FXML
    void onClick(MouseEvent event) {
        lblAnswer.setText("");
        String text = ((ImageView) event.getSource()).getId();
        //System.out.println("Image : " + text);
        int y = (int) event.getSceneX();
        int x = (int) event.getSceneY();
        //System.out.println("x: " + x + " y: " + y);
        int ryad = 108;
        int num = 108;
        int yryad;
        if (x >= 25) {
            if (x <= 25 + 23) {
                //tyt ryad 7
                ryad = 6;
                yryad = 81;
                num = findnum(y, yryad, 18);
            } else if (x >= 55) {
                if (x <= 55 + 23) {
                    //tyt ryad 6
                    ryad = 5;
                    yryad = 145;
                    num = findnum(y, yryad, 13);
                } else if (x >= 85) {
                    if (x <= 85 + 23) {
                        //tyt ryad 5
                        ryad = 4;
                        yryad = 132;
                        num = findnum(y, yryad, 14);
                    } else if (x >= 113) {
                        if (x <= 113 + 23) {
                            //tyt ryad 4
                            ryad = 3;
                            yryad = 145;
                            num = findnum(y, yryad, 13);
                        } else if (x >= 146) {
                            if (x <= 146 + 23) {
                                //tyt ryad 3
                                ryad = 2;
                                yryad = 132;
                                num = findnum(y, yryad, 14);
                            } else if (x >= 195) {
                                if (x <= 195 + 23) {
                                    //tyt ryad 2
                                    ryad = 1;
                                    yryad = 134;
                                    num = findnum(y, yryad, 14);
                                } else if (x >= 227) {
                                    if (x <= 227 + 23) {
                                        //tyt ryad 1
                                        ryad = 0;
                                        yryad = 134;
                                        num = findnum(y, yryad, 14);
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("ryad " + ryad + "num " + num);
        //System.out.println("graphim " + graphim[ryad][num].getId());
        if (ryad == 108 || num == 108) {

        } else if (graphint[ryad][num] == 0) {
            graphim[ryad][num].setImage(imagebron);
            graphint[ryad][num] = 2;
            lblCost.setText(facade.PlusCostMesto(ryad, num));
        } else if (graphint[ryad][num] == 2) {
            graphim[ryad][num].setImage(imagenull);
            graphint[ryad][num] = 0;
            lblCost.setText(facade.MinusCostMesto(ryad, num));
        }
    }

    @FXML
    void onClickBuyButton(ActionEvent event) {
        ArrayList<String> rez = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            for (int k = 0; k < 17; k++) {
                if (graphint[i][k] == 2) {
                    rez.add(String.valueOf(i + 1) + String.valueOf(k + 1));
                }
            }
        }

        lblAnswer.setText(facade.CreateBrons(login, idseans, rez));
        this.init(login, idseans, idseans);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void zapmassiv() {
        graphim[0][0] = imM11;
        graphint[0][0] = 0;
        graphim[0][1] = imM12;
        graphint[0][1] = 0;
        graphim[0][2] = imM13;
        graphint[0][2] = 0;
        graphim[0][3] = imM14;
        graphint[0][3] = 0;
        graphim[0][4] = imM15;
        graphint[0][4] = 0;
        graphim[0][5] = imM16;
        graphint[0][5] = 0;
        graphim[0][6] = imM17;
        graphint[0][6] = 0;
        graphim[0][7] = imM18;
        graphint[0][7] = 0;
        graphim[0][8] = imM19;
        graphint[0][8] = 0;
        graphim[0][9] = imM110;
        graphint[0][9] = 0;
        graphim[0][10] = imM111;
        graphint[0][10] = 0;
        graphim[0][11] = imM112;
        graphint[0][11] = 0;
        graphim[0][12] = imM113;
        graphint[0][12] = 0;
        graphim[1][0] = imM21;
        graphint[1][0] = 0;
        graphim[1][1] = imM22;
        graphint[1][1] = 0;
        graphim[1][2] = imM23;
        graphint[1][2] = 0;
        graphim[1][3] = imM24;
        graphint[1][3] = 0;
        graphim[1][4] = imM25;
        graphint[1][4] = 0;
        graphim[1][5] = imM26;
        graphint[1][5] = 0;
        graphim[1][6] = imM27;
        graphint[1][6] = 0;
        graphim[1][7] = imM28;
        graphint[1][7] = 0;
        graphim[1][8] = imM29;
        graphint[1][8] = 0;
        graphim[1][9] = imM210;
        graphint[1][9] = 0;
        graphim[1][10] = imM211;
        graphint[1][10] = 0;
        graphim[1][11] = imM212;
        graphint[1][11] = 0;
        graphim[1][12] = imM213;
        graphint[1][12] = 0;
        graphim[2][0] = imM31;
        graphint[2][0] = 0;
        graphim[2][1] = imM32;
        graphint[2][1] = 0;
        graphim[2][2] = imM33;
        graphint[2][2] = 0;
        graphim[2][3] = imM34;
        graphint[2][3] = 0;
        graphim[2][4] = imM35;
        graphint[2][4] = 0;
        graphim[2][5] = imM36;
        graphint[2][5] = 0;
        graphim[2][6] = imM37;
        graphint[2][6] = 0;
        graphim[2][7] = imM38;
        graphint[2][7] = 0;
        graphim[2][8] = imM39;
        graphint[2][8] = 0;
        graphim[2][9] = imM310;
        graphint[2][9] = 0;
        graphim[2][10] = imM311;
        graphint[2][10] = 0;
        graphim[2][11] = imM312;
        graphint[2][11] = 0;
        graphim[2][12] = imM313;
        graphint[2][12] = 0;
        graphim[3][0] = imM41;
        graphint[3][0] = 0;
        graphim[3][1] = imM42;
        graphint[3][1] = 0;
        graphim[3][2] = imM43;
        graphint[3][2] = 0;
        graphim[3][3] = imM44;
        graphint[3][3] = 0;
        graphim[3][4] = imM45;
        graphint[3][4] = 0;
        graphim[3][5] = imM46;
        graphint[3][5] = 0;
        graphim[3][6] = imM47;
        graphint[3][6] = 0;
        graphim[3][7] = imM48;
        graphint[3][7] = 0;
        graphim[3][8] = imM49;
        graphint[3][8] = 0;
        graphim[3][9] = imM410;
        graphint[3][9] = 0;
        graphim[3][10] = imM411;
        graphint[3][10] = 0;
        graphim[3][11] = imM412;
        graphint[3][11] = 0;
        graphim[4][0] = imM51;
        graphint[4][0] = 0;
        graphim[4][1] = imM52;
        graphint[4][1] = 0;
        graphim[4][2] = imM53;
        graphint[4][2] = 0;
        graphim[4][3] = imM54;
        graphint[4][3] = 0;
        graphim[4][4] = imM55;
        graphint[4][4] = 0;
        graphim[4][5] = imM56;
        graphint[4][5] = 0;
        graphim[4][6] = imM57;
        graphint[4][6] = 0;
        graphim[4][7] = imM58;
        graphint[4][7] = 0;
        graphim[4][8] = imM59;
        graphint[4][8] = 0;
        graphim[4][9] = imM510;
        graphint[4][9] = 0;
        graphim[4][10] = imM511;
        graphint[4][10] = 0;
        graphim[4][11] = imM512;
        graphint[4][11] = 0;
        graphim[4][12] = imM513;
        graphint[4][12] = 0;
        graphim[5][0] = imM61;
        graphint[5][0] = 0;
        graphim[5][1] = imM62;
        graphint[5][1] = 0;
        graphim[5][2] = imM63;
        graphint[5][2] = 0;
        graphim[5][3] = imM64;
        graphint[5][3] = 0;
        graphim[5][4] = imM65;
        graphint[5][4] = 0;
        graphim[5][5] = imM66;
        graphint[5][5] = 0;
        graphim[5][6] = imM67;
        graphint[5][6] = 0;
        graphim[5][7] = imM68;
        graphint[5][7] = 0;
        graphim[5][8] = imM69;
        graphint[5][8] = 0;
        graphim[5][9] = imM610;
        graphint[5][9] = 0;
        graphim[5][10] = imM611;
        graphint[5][10] = 0;
        graphim[5][11] = imM612;
        graphint[5][11] = 0;
        graphim[6][0] = imM71;
        graphint[6][0] = 0;
        graphim[6][1] = imM72;
        graphint[6][1] = 0;
        graphim[6][2] = imM73;
        graphint[6][2] = 0;
        graphim[6][3] = imM74;
        graphint[6][3] = 0;
        graphim[6][4] = imM75;
        graphint[6][4] = 0;
        graphim[6][5] = imM76;
        graphint[6][5] = 0;
        graphim[6][6] = imM77;
        graphint[6][6] = 0;
        graphim[6][7] = imM78;
        graphint[6][7] = 0;
        graphim[6][8] = imM79;
        graphint[6][8] = 0;
        graphim[6][9] = imM710;
        graphint[6][9] = 0;
        graphim[6][10] = imM711;
        graphint[6][10] = 0;
        graphim[6][11] = imM712;
        graphint[6][11] = 0;
        graphim[6][12] = imM713;
        graphint[6][12] = 0;
        graphim[6][13] = imM714;
        graphint[6][13] = 0;
        graphim[6][14] = imM715;
        graphint[6][14] = 0;
        graphim[6][15] = imM716;
        graphint[6][15] = 0;
        graphim[6][16] = imM717;
        graphint[6][16] = 0;
    }

    @Override
    public void init(String login, String identificator2, String seansid) {
        facade = Facade.getInstance();
        this.login = login;
        this.idseans = seansid;
        ArrayList<Bron> brs = facade.getBronsSe(seansid);
        facade.setCostsum(0);
        zapmassiv();
        if (brs.isEmpty()) {

        } else {
            for (Bron br : brs) {

                graphint[br.getMesto().getRyad() - 1][br.getMesto().getNumber() - 1] = 1;
                graphim[br.getMesto().getRyad() - 1][br.getMesto().getNumber() - 1].setImage(imagebuy);
            }
        }
    }

}
