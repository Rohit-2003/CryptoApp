package com.example.cryptoapp;

public class HistoryItem {
    private String inputText;
    private String encryptedText;

    public HistoryItem(String inputText, String encryptedText) {
        this.inputText = inputText;
        this.encryptedText = encryptedText;
    }

    public String getInputText() { return inputText; }
    public String getEncryptedText() { return encryptedText; }
}
