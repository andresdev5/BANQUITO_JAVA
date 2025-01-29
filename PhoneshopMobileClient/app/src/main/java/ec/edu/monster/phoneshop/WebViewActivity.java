package ec.edu.monster.phoneshop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        webView = findViewById(R.id.myWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                url = input.getText().toString();
                String host = "10.0.2.2";
                String port = "8081";

                if (url == null || url.isEmpty()) {
                    url = "10.0.2.2:8081";
                }

                if (url.contains(":")) {
                    String[] parts = url.split(":");
                    host = parts[0];
                    port = parts[1];
                } else {
                    host = url;
                }

                webView.loadUrl("http://" + host + ":" + port);
            }
        });

        builder.show();
    }
}