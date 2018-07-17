package com.moochain.common;

import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Registration extends Frame implements ActionListener {

    private WListener WinList;
    private TextField tfTitle;
    private TextField tfYear;
    private TextField tfWidth;
    private TextField tfHeight;
    private TextField tfDept;
    private TextField tfName;
    private TextArea tfIntro;
    private Choice chStyle;
    private Choice chType;
    private List<String> img;
    private int picSelected;
    private Button btnImage;
    private Choice ImageList;
    private Button btnRemove;

    public Registration(){
        setTitle("Artworks Registration");
        setVisible(true);

        GridLayout Layout = new GridLayout(19,1);
        setLayout(Layout);

        Font BigFont = new Font(null, 0, 30);
        Font MedFont = new Font(null, 0, 25);
        Font SmallFont = new Font(null, 0, 20);

        picSelected=0;

        Label lblInfo = new Label("Basic Information",Label.CENTER);
        lblInfo.setFont(BigFont);
        add(lblInfo);

        Label lblTitle = new Label("Title",Label.LEFT);
        lblTitle.setFont(MedFont);
        add(lblTitle);
        tfTitle = new TextField("");
        tfTitle.setFont(SmallFont);
        add(tfTitle);

        Label lblYear = new Label("Year",Label.LEFT);
        lblYear.setFont(MedFont);
        add(lblYear);
        tfYear = new TextField("");
        tfYear.setFont(SmallFont);
        add(tfYear);

        Label lblSize = new Label("Size",Label.LEFT);
        lblSize.setFont(MedFont);
        add(lblSize);

        Panel PanelSize = new Panel(new GridLayout(1,6));
        tfWidth = new TextField("");
        tfWidth.setFont(SmallFont);
        PanelSize.add(tfWidth);
        Label lblX1 = new Label("X",Label.CENTER);
        lblX1.setFont(MedFont);
        PanelSize.add(lblX1);
        tfHeight = new TextField("");
        tfHeight.setFont(SmallFont);
        PanelSize.add(tfHeight);
        Label lblX2 = new Label("X",Label.CENTER);
        lblX2.setFont(MedFont);
        PanelSize.add(lblX2);
        tfDept = new TextField("");
        tfDept.setFont(SmallFont);
        PanelSize.add(tfDept);
        Label lblcm = new Label("cm",Label.CENTER);
        lblcm.setFont(MedFont);
        PanelSize.add(lblcm);
        add(PanelSize);

        Label lblName = new Label("Artist Name",Label.LEFT);
        lblName.setFont(MedFont);
        add(lblName);
        tfName = new TextField("");
        tfName.setFont(SmallFont);
        add(tfName);

        Label lblStyle = new Label("Style",Label.LEFT);
        lblStyle.setFont(MedFont);
        add(lblStyle);
        chStyle = new Choice();
        chStyle.setFont(SmallFont);
        chStyle.add("Landscape");
        chStyle.add("Icon");
        chStyle.add("Animal");
        chStyle.add("Still");
        chStyle.add("Abstract");
        chStyle.add("Cartoon");
        chStyle.add("Others");
        add(chStyle);

        Label lblType = new Label("Type",Label.LEFT);
        lblType.setFont(MedFont);
        add(lblType);
        chType = new Choice();
        chType.setFont(SmallFont);
        chType.add("Ink Wash Painting");
        chType.add("Oil Painting");
        chType.add("Sketch");
        chType.add("Watercolor & Gouache");
        chType.add("Engraving");
        chType.add("Calligraphy & Seal Carving");
        chType.add("Photography");
        chType.add("Others");
        add(chType);

        Label lblIntro = new Label("Artwork Introduction",Label.LEFT);
        lblIntro.setFont(MedFont);
        add(lblIntro);
        tfIntro = new TextArea("",3,1,TextArea.SCROLLBARS_NONE);
        tfIntro.setFont(SmallFont);
        add(tfIntro);

        img = new ArrayList<>();
        btnImage = new Button("Select Image (0 / 9)");
        btnImage.setFont(MedFont);
        add(btnImage);
        btnImage.addActionListener(new ImageActionListener());

        ImageList = new Choice();
        ImageList.setFont(SmallFont);
        add(ImageList);

        btnRemove = new Button("Remove");
        btnRemove.setFont(MedFont);
        add(btnRemove);
        btnRemove.addActionListener(new RemoveImageActionListener());
        btnRemove.setEnabled(false);

        Button btnNext = new Button("Next");
        btnNext.setFont(MedFont);
        add(btnNext);
        btnNext.addActionListener(this);

        WinList = new WListener();
        addWindowListener(WinList);
        setSize(600, 900);
    }
    @Override public void actionPerformed(ActionEvent evt) {
        boolean err=false;
        if(tfTitle.getText().length()>30) {
            err = true;
            new Message("Error: Title is too long!");
        }
        if(tfIntro.getText().length()>1000) {
            err = true;
            new Message("Error: Intro is too long!");
        }
        if(picSelected==0){
            err = true;
            new Message("Error: Pictures are missing!");
        }
        if(tfName.getText().length()>30) {
            err = true;
            new Message("Error: Name is too long!");
        }
        if(!isNumber(tfYear.getText())) {
            err = true;
            new Message("Error: Year has to be a number!");
        }
        if(!isNumber(tfWidth.getText()) || !isNumber(tfHeight.getText()) || !isNumber(tfDept.getText())) {
            err = true;
            new Message("Error: Size has to be a number!");
        }
        if("".equals(tfTitle.getText()) || "".equals(tfYear.getText()) || "".equals(tfWidth.getText()) ||
           "".equals(tfHeight.getText()) || "".equals(tfName.getText()) || "".equals(tfIntro.getText())){
            err = true;
            new Message("Error: some fields are not filled!");
        }
        if(!err){

            String url = "http://10.1.25.34:7070/api/art/reg";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", tfTitle.getText());
            jsonObject.put("years", tfYear.getText());
            jsonObject.put("author", tfName.getText());
            jsonObject.put("size", tfWidth.getText()+"x"+tfHeight.getText());
            jsonObject.put("material", chStyle.getSelectedItem());
            jsonObject.put("type", chType.getSelectedItem());
            jsonObject.put("description", tfIntro.getText());
            jsonObject.put("cover",img.get(0));
            if(picSelected > 1){
                img.remove(0);
                jsonObject.put("antiFakes",img);
            }
            jsonObject.put("ownerNum", "U1524470188904835382");
            String result = post(url, jsonObject.toJSONString());
            System.out.println(result);
            new Message("Registration Complete!");
        }
    }

    class ImageActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            if(picSelected==9) {
                new Message("You can't choose more than 9 pictures!");
                return;
            }
            FileChooser fc = new FileChooser();
            File f =fc.getFile();
            if(f==null)
                return;
            ImageIcon tmpIcon = new ImageIcon(f.getPath());
            if(tmpIcon.getIconWidth()<720 || tmpIcon.getIconHeight()<560){
                new Message("Error: picture size too small");
                f=null;
                return;
            }
            img.add(fileToBase64(f));
                picSelected++;
            btnImage.setLabel("Select Image ("+picSelected+" / 9)");
            ImageList.add(f.toString());
            if(picSelected>0)
                btnRemove.setEnabled(true);
            f=null;
        }
    }
    class RemoveImageActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            int i=ImageList.getSelectedIndex();
            ImageList.remove(i);
            img.remove(i);
            picSelected--;
            btnImage.setLabel("Select Image ("+picSelected+" / 9)");
            if(picSelected==0)
                btnRemove.setEnabled(false);
        }
    }


    public static void main(String[] args) {
        Registration app = new Registration();
    }

    public static String post(String url,String json){
        HttpRequest request = HttpRequest.post(url).body(json).contentType("application/json").charset("UTF-8");
        HttpResponse response = request.send();

        return response.bodyText();
    }

    public static boolean isNumber(String source) {
        for (int i = 0; i < source.length(); i++)
            if (source.charAt(i) != '1' && source.charAt(i) != '2' && source.charAt(i) != '3' &&
                source.charAt(i) != '4' && source.charAt(i) != '5' && source.charAt(i) != '6' &&
                source.charAt(i) != '7' && source.charAt(i) != '8' && source.charAt(i) != '9' &&
                source.charAt(i) != '0' && source.charAt(i) != ',' && source.charAt(i) != '.')
            return false;
        return true;
    }

    public String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(base64);
        return base64;
    }


}
