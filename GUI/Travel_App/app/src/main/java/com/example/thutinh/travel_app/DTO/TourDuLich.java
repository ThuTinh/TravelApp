package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TourDuLich implements Serializable{
    String phone;
    String email;
    String faceBook;
    String thoiGian;
    String thoiGianTu;
    String thoiGianDen;
    String diaDiemKhoiHanh;
    String MaTour;
    String dsDiaDiem;
    String soLuongCon;
    String gia;
    String key;
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Comment_class> getListFeedBack() {
        return listFeedBack;
    }

    public void setListFeedBack(List<Comment_class> listFeedBack) {
        this.listFeedBack = listFeedBack;
    }

   public  List<Comment_class> listFeedBack = new ArrayList<>();

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    String nguoiTao;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


   public   List<String>arrHinh = new ArrayList<>();
    public TourDuLich(String phone, String email, String faceBook, String thoiGian, String thoiGianTu, String thoiGianDen, String diaDiemKhoiHanh, String maTour, String dsDiaDiem, String soLuongCon, String gia, List<String> arrHinh,String key, String nguoiTao, List<Comment_class> list, String link) {
        this.phone = phone;
        this.email = email;
        this.faceBook = faceBook;
        this.thoiGian = thoiGian;
        this.thoiGianTu = thoiGianTu;
        this.thoiGianDen = thoiGianDen;
        this.diaDiemKhoiHanh = diaDiemKhoiHanh;
        MaTour = maTour;
        this.dsDiaDiem = dsDiaDiem;
        this.soLuongCon = soLuongCon;
        this.gia = gia;
        this.arrHinh = arrHinh;

        this.key = key;
        this.nguoiTao = nguoiTao;
        this.listFeedBack = list;
        this.link = link;
    }

    public TourDuLich() {
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

    public String getFaceBook() {
        return faceBook;
    }

    public void setFaceBook(String faceBook) {
        this.faceBook = faceBook;
    }

    public String getthoiGian() {
        return thoiGian;
    }

    public void setthoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getThoiGianTu() {
        return thoiGianTu;
    }

    public void setThoiGianTu(String thoiGianTu) {
        this.thoiGianTu = thoiGianTu;
    }

    public String getThoiGianDen() {
        return thoiGianDen;
    }

    public void setThoiGianDen(String thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
    }

    public String getDiaDiemKhoiHanh() {
        return diaDiemKhoiHanh;
    }

    public void setDiaDiemKhoiHanh(String diaDiemKhoiHanh) {
        this.diaDiemKhoiHanh = diaDiemKhoiHanh;
    }

    public String getMaTour() {
        return MaTour;
    }

    public void setMaTour(String maTour) {
        MaTour = maTour;
    }

    public String getDsDiaDiem() {
        return dsDiaDiem;
    }

    public void setDsDiaDiem(String dsDiaDiem) {
        this.dsDiaDiem = dsDiaDiem;
    }

    public String getSoLuongCon() {
        return soLuongCon;
    }

    public void setSoLuongCon(String soLuongCon) {
        this.soLuongCon = soLuongCon;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public List<String> getArrHinh() {
        return arrHinh;
    }

    public void setArrHinh(List<String> arrHinh) {
        this.arrHinh = arrHinh;
    }
}
