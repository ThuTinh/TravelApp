package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;

public class ThongTinMonAn implements Serializable {
    private String gia;
    private  String tenMonAn;
    private String hinhMonAn="";

    public ThongTinMonAn(String gia, String tenMonAn, String hinhMonAn) {
        this.gia = gia;
        this.tenMonAn = tenMonAn;
        this.hinhMonAn = hinhMonAn;
    }

    public ThongTinMonAn() {
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getHinhMonAn() {
        return hinhMonAn;
    }

    public void setHinhMonAn(String hinhMonAn) {
        this.hinhMonAn = hinhMonAn;
    }
}
