package com.example.zcy.rsademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.zcy.rsademo.utils.Aes;
import com.example.zcy.rsademo.utils.Rsa;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.aes_encrypt_btn)
    Button aesEncryptBtn;
    @BindView(R.id.aes_decrypt_btn)
    Button aesDecryptBtn;
    @BindView(R.id.ras_encrypt_btn)
    Button rasEncryptBtn;
    @BindView(R.id.ras_decrypt_btn)
    Button rasDecryptBtn;
    @BindView(R.id.reset)
    Button reset;

    private String aesKey, aesKeyRsaEncrypt, aesKeyRsaDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.aes_encrypt_btn)
    public void onAesEncryptBtnClicked() {
        aesKey = Aes.generateKeyString();
        tv.setText(Aes.encrypt(tv.getText().toString(), aesKey));
    }

    @OnClick(R.id.aes_decrypt_btn)
    public void onAesDecryptBtnClicked() {
        tv.setText(Aes.decrypt(tv.getText().toString(), aesKey));
    }

    @OnClick(R.id.ras_encrypt_btn)
    public void onRasEncryptBtnClicked() {
        try {
            aesKey = Aes.generateKeyString();
            tv.setText(Aes.encrypt(tv.getText().toString(), aesKey));
            InputStream is = getAssets().open("pub.key");
            aesKeyRsaEncrypt = Rsa.encryptByPublicKey(aesKey, Rsa.loadPublicKey(is));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ras_decrypt_btn)
    public void onRasDecryptBtnClicked() {
        try {
            aesKeyRsaDecrypt = Rsa.decryptByPrivateKey(aesKeyRsaEncrypt, Rsa.loadPrivateKey(getAssets().open("pri.key")));
            tv.setText(Aes.decrypt(tv.getText().toString(), aesKeyRsaDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.reset)
    public void onViewClicked() {
        tv.setText("Hello World!");
        aesKey=aesKeyRsaEncrypt=aesKeyRsaDecrypt="";
    }
}
