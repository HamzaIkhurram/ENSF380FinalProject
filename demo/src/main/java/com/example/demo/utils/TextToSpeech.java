package com.example.demo.utils;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private static final String VOICENAME = "kevin16";
    private static Voice voice;

    public TextToSpeech() {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME);
        if (voice != null) {
            voice.allocate();
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICENAME);
        }
    }

    public  void speak(String text) {
        if (voice != null) {
            voice.speak(text);
        }
    }

    public  void deallocate() {
        if (voice != null) {
            voice.deallocate();
        }
    }

}
