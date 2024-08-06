package com.example.demo.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TextToSpeechTest {

    private TextToSpeech textToSpeech;

    @Before
    public void setUp() {
        textToSpeech = new TextToSpeech();
    }

    @Test
    public void testSpeak() {
        String text = "Hello, this is a test.";
        textToSpeech.speak(text);
        // No assertion needed as we just want to ensure the method runs without exceptions
    }

    @Test
    public void testVoiceNotNull() {
        assertNotNull(TextToSpeech.voice);
    }

    @After
    public void tearDown() {
        textToSpeech.deallocate();
    }
}
