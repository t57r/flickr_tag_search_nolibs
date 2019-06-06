package com.flickrsearch.util.network;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextDebouncer {
    private static final long DEBOUNCE_TIMEOUT = 1000L;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private Runnable workRunnable;

    public interface DebounceListener {
        void onCancel();
        void onRun(String text);
    }

    public void debounce(EditText editText, final DebounceListener debounceListener) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                final String text = s.toString();
                debounce(text, debounceListener);
            }
        });
    }

    private void debounce(final String text, final DebounceListener debounceListener) {
        if (workRunnable != null) {
            debounceListener.onCancel();
            uiHandler.removeCallbacks(workRunnable);
        }
        workRunnable = new Runnable() {
            @Override
            public void run() {
                debounceListener.onRun(text);
            }
        };
        uiHandler.postDelayed(workRunnable, DEBOUNCE_TIMEOUT);
    }

}
