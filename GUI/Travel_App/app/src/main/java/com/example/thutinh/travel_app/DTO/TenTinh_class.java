package com.example.thutinh.travel_app.DTO;

public class TenTinh_class {
     private int hinh;
     private String tenTinh;

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }

    public TenTinh_class( String tenTinh ,int hinh) {
        this.hinh = hinh;
        this.tenTinh = tenTinh;
    }

}
