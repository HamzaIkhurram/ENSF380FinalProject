package com.example.demo.utils;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private static final String VOICENAME = "kevin16";
    static Voice voice;
    private static TextToSpeech instance;

    public TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME);
        if (voice != null) {
            voice.allocate();
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICENAME);
        }
    }

    public static synchronized TextToSpeech getInstance() {
        if (instance == null) {
            instance = new TextToSpeech();
        }
        return instance;
    }

    public synchronized void speak(String text) {
        if (voice != null) {
            voice.speak(text);
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICENAME);
        }
    }

    public synchronized void deallocate() {
        if (voice != null) {
            voice.deallocate();
            voice = null; // Set to null to prevent further use
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICENAME);
        }
    }
}
