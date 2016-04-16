package krishna.mydemo.Register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import krishna.mydemo.Helpers.PrefUtils;
import krishna.mydemo.Main.MainActivity;
import krishna.mydemo.R;

public class RegisterAcitivity extends AppCompatActivity implements View.OnClickListener,RegisterView {

    private ImageView imgProfile;
    private EditText etEmail,etName,etPassword,etCnfPassword,etAge;
    private RadioButton rdMale,rdFeMale;
    private TextView txtRegister;
    private boolean isProfilePicSelected=false;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog dialog;
    private RegisterPresenter presenter;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, CANCEL_REQUEST = 2;
    private File imageFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivity);
        getSupportActionBar().setTitle(R.string.reg);
        presenter = new RegisterPresenter(RegisterAcitivity.this,this);
        init();
    }



    private void init() {
        dialog = new ProgressDialog(RegisterAcitivity.this);
        dialog.setMessage("Registering your account...");
        dialog.setCancelable(false);

        imgProfile = (ImageView)findViewById(R.id.imgProfile);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        rdMale = (RadioButton)findViewById(R.id.rdMale);
        rdFeMale = (RadioButton)findViewById(R.id.rdFeMale);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etCnfPassword = (EditText)findViewById(R.id.etCnfPassword);
        etAge = (EditText)findViewById(R.id.etAge);
        txtRegister.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgProfile:
                selectImage();
                break;
            case R.id.txtRegister:
                if(PrefUtils.isInternetConnected(RegisterAcitivity.this)){
                    if(!etEmail.getText().toString().matches(emailPattern) || etEmail.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();

                    }else  if(etName.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();

                    }else  if(etPassword.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();

                    }else  if(etCnfPassword.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter confirm password.", Toast.LENGTH_SHORT).show();

                    }else if(!etPassword.getText().toString().equals(etCnfPassword.getText().toString())){
                        Toast.makeText(RegisterAcitivity.this, "Password do not match.", Toast.LENGTH_SHORT).show();

                    }else  if(etAge.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter age.", Toast.LENGTH_SHORT).show();

                    }else if(!isProfilePicSelected){
                        Toast.makeText(RegisterAcitivity.this, "Please select Profile picture.", Toast.LENGTH_SHORT).show();
                    }else{

                        if(rdMale.isChecked()) {
                            presenter.doLogin(imageFile, etEmail.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etAge.getText().toString(), "male");
                        }else{
                            presenter.doLogin(imageFile, etEmail.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etAge.getText().toString(), "female");
                        }
                    }

                }else{
                    Toast.makeText(RegisterAcitivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterAcitivity.this);
        builder.setTitle("Add Profile Picture!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                }
            }
        });
        builder.show();
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if(dialog.isShowing())dialog.dismiss();
    }

    @Override
    public void doLogin(File f, String email, String password, String firstname, String age, String gender) {

    }

    @Override
    public void onLoginSucess(String msg) {
        Toast.makeText(RegisterAcitivity.this,msg,Toast.LENGTH_SHORT).show();
        PrefUtils.setLogin(RegisterAcitivity.this, true);
        startActivity(new Intent(RegisterAcitivity.this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void onLoginFailure(String msg) {
        Toast.makeText(RegisterAcitivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
        imageFile = new File(getRealPathFromURI(tempUri));

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgProfile.setImageBitmap(thumbnail);
        isProfilePicSelected = true;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            Uri tempUri = getImageUri(getApplicationContext(), bitmap);
            imageFile = new File(getRealPathFromURI(tempUri));


            imgProfile.setImageBitmap(bitmap);
            isProfilePicSelected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //end of main class
}
