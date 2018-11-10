package com.example.thutinh.travel_app.DTO;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoTaChiTiet_class implements Serializable{

    private String tenDiaDanh;
    private String moTa;
    public   List<String> arrHinh = new ArrayList<String>();

    public MoTaChiTiet_class() {

    }

    public MoTaChiTiet_class(String tenDiaDanh, String moTa, List<String> arrHinh) {
        this.tenDiaDanh = tenDiaDanh;
        this.moTa = moTa;
        this.arrHinh = arrHinh;
    }

    public String getTenDiaDanh() {
        return tenDiaDanh;
    }

    public void setTenDiaDanh(String tenDiaDanh) {
        this.tenDiaDanh = tenDiaDanh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<String> getArrHinh() {
        return arrHinh;
    }

    public void setArrHinh(List<String> arrHinh) {
        this.arrHinh = arrHinh;
    }
}
