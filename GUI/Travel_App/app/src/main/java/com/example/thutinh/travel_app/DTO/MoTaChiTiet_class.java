package com.example.thutinh.travel_app.DTO;

import android.net.Uri;
import android.widget.Adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoTaChiTiet_class implements Serializable{

    private String tenDiaDanh;
    private String viTri;
    private String moTa;
    public List<String> dsYeuThich = new ArrayList<String>();
    public long soLuotThich = dsYeuThich.size();
    private String key = "";
    public List<Comment_class> listCmt = new ArrayList<Comment_class>();
    public   List<String> arrHinh = new ArrayList<String>();

    public ArrayList<String> getListEdit() {
        return listEdit;
    }

    public void setListEdit(ArrayList<String> listEdit) {
        this.listEdit = listEdit;
    }

   public ArrayList<String> listEdit = new ArrayList<>();

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public MoTaChiTiet_class() {

    }

    public List<Comment_class> getListCmt() {
        return listCmt;
    }

    public void setListCmt(ArrayList<Comment_class> listCmt) {
        this.listCmt = listCmt;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public MoTaChiTiet_class(String tenDiaDanh, String moTa, List<String> arrHinh, List<String>dsYeuThich, List<Comment_class> listCmt, String vitri,ArrayList<String> listEdit) {
        this.tenDiaDanh = tenDiaDanh;
        this.moTa = moTa;
        this.arrHinh = arrHinh;
        this.dsYeuThich = dsYeuThich;
        this.listCmt = listCmt;
        this.viTri = vitri;
        this.listEdit = listEdit;
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

    public List<String> getDsYeuThich() {
        return dsYeuThich;
    }

    public long getSoLuotThich() {
        return soLuotThich;
    }

    public void setDsYeuThich(List<String> dsYeuThich) {
        this.dsYeuThich = dsYeuThich;
    }

    public void setSoLuotThich(long soLuotThich) {
        this.soLuotThich = soLuotThich;
    }
}
