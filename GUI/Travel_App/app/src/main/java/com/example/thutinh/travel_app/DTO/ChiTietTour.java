package com.example.thutinh.travel_app.DTO;

import java.io.Serializable;

public class ChiTietTour  implements Serializable{
    String ngay;
    String noiDung;

    public ChiTietTour(String ngay, String noiDung) {
        this.ngay = ngay;
        this.noiDung = noiDung;
    }

    public ChiTietTour() {
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
