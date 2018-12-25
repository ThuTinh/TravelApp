package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DanhSachAnUong implements Serializable {
    private  String tennhaCungCap;
    private  String diaDiem;
    private  String phone;
    private  String email;
    private  String face;
    private String noiDung;
    private  String nguoiTao;
    private String hinh="";
    private  List<ThongTinMonAn> listMonAn = new ArrayList<>();
    public   List<Comment_class> listCmt = new ArrayList<>();

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }


    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public DanhSachAnUong(String tennhaCungCap, String noiDung, String nguoitao, String diaDiem, String phone, String email, String face,String hinh, List<ThongTinMonAn> listMonAn, List<Comment_class>listCmt) {
        this.tennhaCungCap = tennhaCungCap;
        this.diaDiem = diaDiem;
        this.phone = phone;
        this.email = email;
        this.face = face;
       this.hinh = hinh;
        this.listMonAn = listMonAn;
        this.listCmt = listCmt;
        this.noiDung = noiDung;
        this.nguoiTao = nguoitao;

    }

    public List<Comment_class> getListCmt() {
        return listCmt;
    }

    public void setListCmt(List<Comment_class> listCmt) {
        this.listCmt = listCmt;
    }


    public DanhSachAnUong() {
    }

    public String getTennhaCungCap() {
        return tennhaCungCap;
    }

    public void setTennhaCungCap(String tennhaCungCap) {
        this.tennhaCungCap = tennhaCungCap;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }


    public List<ThongTinMonAn> getListMonAn() {
        return listMonAn;
    }

    public void setListMonAn(List<ThongTinMonAn> listMonAn) {
        this.listMonAn = listMonAn;
    }
}
