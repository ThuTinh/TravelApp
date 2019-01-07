package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Home implements Serializable {
  private String diaDiem;
  private String noiDung;
  private String phone;
  private String Email;
  private String Face;
  private  String nameHome;
  private String gia;
  private  String tienIch;
  private  String chiTietPhongO;
  private  String link;
  public List<Comment_class> listFeedBack= new ArrayList<>();
  public List<String> arrHinh = new ArrayList<String>();
  public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTienIch() {
        return tienIch;
    }

    public void setTienIch(String tienIch) {
        this.tienIch = tienIch;
    }

    public String getChiTietPhongO() {
        return chiTietPhongO;
    }

    public void setChiTietPhongO(String chiTietPhongO) {
        this.chiTietPhongO = chiTietPhongO;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }



  public String getEmailNguoiTao()  {
    return emailNguoiTao;
  }

  public void setEmailNguoiTao(String emailNguoiTao) {
    this.emailNguoiTao = emailNguoiTao;
  }

  private String emailNguoiTao;

  private String key = "";

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }



  public Home() {
  }

  public String getDiaDiem() {
    return diaDiem;
  }

  public void setDiaDiem(String diaDiem) {
    this.diaDiem = diaDiem;
  }

  public String getNoiDung() {
    return noiDung;
  }

  public void setNoiDung(String noiDung) {
    this.noiDung = noiDung;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getFace() {
    return Face;
  }

  public void setFace(String face) {
    Face = face;
  }



  public List<Comment_class> getListFeedBack() {
    return listFeedBack;
  }

  public void setListFeedBack(List<Comment_class> listFeedBack) {
    this.listFeedBack = listFeedBack;
  }

  public List<String> getArrHinh() {
    return arrHinh;
  }

  public void setArrHinh(List<String> arrHinh) {
    this.arrHinh = arrHinh;
  }

    public Home(String diaDiem, String noiDung, String phone, String email, String face, String nameHome, String gia, String tienIch, String chiTietPhongO, String link, String emailNguoiTao, String key, List<Comment_class> listFeedBack, List<String> arrHinh) {
        this.diaDiem = diaDiem;
        this.noiDung = noiDung;
        this.phone = phone;
        Email = email;
        Face = face;
        this.nameHome = nameHome;
        this.gia = gia;
        this.tienIch = tienIch;
        this.chiTietPhongO = chiTietPhongO;
        this.link = link;
        this.emailNguoiTao = emailNguoiTao;
        this.key = key;
        this.listFeedBack = listFeedBack;
        this.arrHinh = arrHinh;
    }
    public String getNameHome() {
    return nameHome;
  }

  public void setNameHome(String nameHome) {
    this.nameHome = nameHome;
  }


}

