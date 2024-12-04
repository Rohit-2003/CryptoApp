package com.example.cryptoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    private EditText inputText;
    private EditText keyText;
    private TextView outputText;
    private Spinner schemeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        inputText = view.findViewById(R.id.inputText);
        keyText = view.findViewById(R.id.keyText);
        outputText = view.findViewById(R.id.outputText);
        schemeSpinner = view.findViewById(R.id.schemeSpinner);

        // Set up the spinner with encryption schemes
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.encryption_schemes,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schemeSpinner.setAdapter(adapter);

        Button encryptButton = view.findViewById(R.id.encryptButton);
        encryptButton.setOnClickListener(v -> {
            String input = inputText.getText().toString();
            String key = keyText.getText().toString();
            String scheme = schemeSpinner.getSelectedItem().toString();
            outputText.setText("Encrypted: " + performEncryption(input, key, scheme));
        });

        Button decryptButton = view.findViewById(R.id.decryptButton);
        decryptButton.setOnClickListener(v -> {
            String input = inputText.getText().toString();
            String key = keyText.getText().toString();
            String scheme = schemeSpinner.getSelectedItem().toString();
            outputText.setText("Decrypted: " + performDecryption(input, key, scheme));
        });

        return view;
    }

    // Perform encryption based on the selected scheme
    private String performEncryption(String input, String key, String scheme) {
        switch (scheme) {
            case "Caesar Cipher":
                return caesarCipher(input, key, true); // Encrypt using Caesar Cipher
            case "Reverse Cipher":
                return reverseCipher(input); // Encrypt by reversing the string
            case "Base64 Encoding":
                return base64Encode(input); // Encrypt using Base64 Encoding
            default:
                return "Invalid scheme selected!";
        }
    }

    // Perform decryption based on the selected scheme
    private String performDecryption(String input, String key, String scheme) {
        switch (scheme) {
            case "Caesar Cipher":
                return caesarCipher(input, key, false); // Decrypt using Caesar Cipher
            case "Reverse Cipher":
                return reverseCipher(input); // Decrypt by reversing the string
            case "Base64 Encoding":
                return base64Decode(input); // Decrypt using Base64 Decoding
            default:
                return "Invalid scheme selected!";
        }
    }

    // Caesar Cipher implementation
    private String caesarCipher(String input, String key, boolean encrypt) {
        if (key.isEmpty()) return "Key is required!";
        int shift;
        try {
            shift = Integer.parseInt(key); // Convert key to an integer
        } catch (NumberFormatException e) {
            return "Invalid key! Please provide a numeric key.";
        }

        if (!encrypt) shift = -shift; // Reverse shift for decryption

        StringBuilder result = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                result.append((char) ((character - base + shift + 26) % 26 + base));
            } else {
                result.append(character); // Non-alphabet characters remain unchanged
            }
        }
        return result.toString();
    }

    // Reverse Cipher implementation
    private String reverseCipher(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    // Base64 Encoding implementation
    private String base64Encode(String input) {
        return android.util.Base64.encodeToString(input.getBytes(), android.util.Base64.DEFAULT).trim();
    }

    // Base64 Decoding implementation
    private String base64Decode(String input) {
        try {
            byte[] decodedBytes = android.util.Base64.decode(input, android.util.Base64.DEFAULT);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            return "Invalid Base64 input!";
        }
    }
}
