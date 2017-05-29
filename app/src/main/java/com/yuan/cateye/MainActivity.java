package com.yuan.cateye;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private ImageView iv_show;
    private int viewWidth,viewHeight;//SurfaceView的宽高

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv_show = (ImageView) findViewById(R.id.iv_show_camera_activity);
        //SurfaceView
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_camera2_activity);
        mSurfaceHolder = mSurfaceView.getHolder();
        //mSurfaceView不需要自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //mSurfaceView添加回调
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback2() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                //初始化相机
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                //释放Camera资源
                if (mCamera != null){
                    mCamera.stopPreview();
                    mCamera.release();
                }
            }

            @Override
            public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            }
        });
        mSurfaceView.setOnClickListener(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (mSurfaceView != null){
            viewHeight = mSurfaceView.getHeight();
            viewWidth = mSurfaceView.getWidth();
        }
    }


    private void initCamera() {
        mCamera = Camera.open();//默认开启后置摄像头
        mCamera.setDisplayOrientation(90);//摄像头旋转为90度
        if (mCamera != null){
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                //设置预览照片大小
                parameters.setPreviewFpsRange(viewWidth,viewHeight);
                //预览相机照片帧数;
                parameters.setPreviewFpsRange(4,10);
                //设置格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //设置质量
                parameters.set("jpeg-quality", 90);
                //设置大小
                parameters.setPictureSize(viewWidth,viewHeight);
                //通过SurfaceView显示预览
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //开始预览
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

}
