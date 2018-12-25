package com.example.thutinh.travel_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class ThongTinDichVuChiTiet extends AppCompatActivity {
FlipperLayout fliper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_dich_vu_chi_tiet);
        AnhXa();
        SetLayout();

    }

    private void SetLayout() {
        String url[] = new String[]{
                "https://image.tienphong.vn/w665/Uploaded/2018/pcgycivo/2018_05_24/1_dhtp.jpg",
                "https://datvietmedia.com/wp-content/uploads/2018/08/4-1.jpg",
                "https://ttol.vietnamnetjsc.vn/images/2018/05/25/13/40/net-cuoi-be-gai-4-1527053440029267502181.jpg"

        };
        for (int i = 0;i<3; i++)
        {

            FlipperView view = new FlipperView(getBaseContext());
            view.setImageUrl(url[i]).setDescription("img thứ "+i);
            fliper.addFlipperView(view);
        }
    }


    void AnhXa()
    {
        fliper = (FlipperLayout)findViewById(R.id.flipAnhDichVu);
    }
}
