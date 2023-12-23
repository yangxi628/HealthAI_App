package com.example.healthai.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthai.R;
import com.paypal.android.sdk.payments.*;

import java.math.BigDecimal;

public class SubscriptionActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription);

        Button subscribeButton = findViewById(R.id.paymentButton);
        ImageView backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionActivity.this, NavigationBar.class);
                startActivity(intent);
            }
        });
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (v.getId() == R.id.contactButton) {
                    switchToActivity(ProfileActivity.class);
                } else if (v.getId() == R.id.profileButton) {
                    switchToActivity(ProfileActivity.class);
                } else if (v.getId() == R.id.paymentButton) {

                    initiatePayPalPayment();
                }
            }
        };

        subscribeButton.setOnClickListener(buttonClickListener);
    }

    private void initiatePayPalPayment() {
        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Use PayPalConfiguration.ENVIRONMENT_PRODUCTION for production
                .clientId("AfKCn76Mtqi0aSpT8_8Q4eRgUtmmSb97Q1anIe2yFYzu42FoWZC11Vz9S-emuRvQF1cLJx6ZpCGtkiXr");

        PayPalPayment payment = new PayPalPayment(new BigDecimal("0.01"), "EUR", "Monthly Subscription", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Payment successful
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    String paymentDetails = confirmation.toJSONObject().toString();
                    // show a toast indicating success
                    Toast.makeText(this, "Payment successful. Premium features unlocked!", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Payment canceled by user
                Toast.makeText(this, "Payment canceled by user", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // Invalid payment configuration
                Toast.makeText(this, "Invalid payment configuration", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}