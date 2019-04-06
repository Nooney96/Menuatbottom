package rossnoonan.menuatbottom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaypalMoneyPoolFragment extends Fragment  {

    WebView webPool;
    public PaypalMoneyPoolFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_paypal_moneypool, container, false);

        View v = inflater.inflate(R.layout.fragment_paypal_moneypool, container, false);
        webPool=(WebView)v.findViewById(R.id.webView1);

        //setting up java script
        WebSettings webSettings =webPool.getSettings();
        webSettings.setJavaScriptEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);

        // Forces links and redirects to open in the WebView instead of in a browser
        webPool.setWebViewClient(new WebViewClient());
        webPool.loadUrl("https://www.paypal.com/signin?returnUri=https%3A%2F%2Fwww.paypal.com%2Fpools&state=%2F");
        return v;
    }
    }





