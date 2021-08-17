package co.datacap.mobiletokendemo;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import co.datacap.mobiletoken.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DatacapTokenizerActivity.DATACAP_TOKENIZER_REQUEST)
        {
            if (resultCode == DatacapTokenizerActivity.RESULT_SUCCESS)
            {
                DatacapToken token = (DatacapToken)data.getSerializableExtra("token");
                new AlertDialog.Builder(this)
                        .setTitle("Token Response")
                        .setMessage("Token: " + token.token + "\nLast 4: " + token.last4 + "\nExp Month: " + token.expirationMonth + "\nExp Year: " + token.expirationYear + "\nBrand: " + token.brand)
                        .setNeutralButton("OK", null).show();
            }
            else if (resultCode == DatacapTokenizerActivity.RESULT_ERROR)
            {
                DatacapTokenizationError error = (DatacapTokenizationError)data.getSerializableExtra("error");
                new AlertDialog.Builder(this)
                        .setTitle("Tokenization Error")
                        .setMessage("Error Code: " + error.errorCode.toString() + "\nError Message: " + error.errorMessage)
                        .setNeutralButton("OK", null).show();
            }
            else if (resultCode == DatacapTokenizerActivity.RESULT_CANCELED)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Tokenization Cancelled")
                        .setMessage("Tokenization Cancelled")
                        .setNeutralButton("OK", null).show();
            }
        }
    }

    public void getTokenPressed(View v) {
        Intent tokenIntent = new Intent(this, DatacapTokenizerActivity.class);
        tokenIntent.putExtra("publicKey", "57869507bb3545e6871de996208b2bcf");
        //"environment" should only be supplied when targeting the certification environment. When targeting production do not pass an environment.
        tokenIntent.putExtra("environment", "cert");
        startActivityForResult(tokenIntent, DatacapTokenizerActivity.DATACAP_TOKENIZER_REQUEST);
    }

}
