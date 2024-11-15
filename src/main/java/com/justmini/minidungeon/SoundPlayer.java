package com.justmini.minidungeon;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private static Map<String, Clip> soundCache = new HashMap<>();

    public static void playSound(String soundFilePath) {
        try {
            Clip clip = soundCache.get(soundFilePath);
            if (clip == null) {
                // 리소스에서 사운드 파일 로드
                URL soundUrl = SoundPlayer.class.getResource(soundFilePath);
                if (soundUrl == null) {
                    System.err.println("no sound files at: " + soundFilePath);
                    return;
                }

                // 오디오 입력 스트림 생성
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);

                // clip 생성 및 열기
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // 사운드 캐시에 저장
                soundCache.put(soundFilePath, clip);
            }

            // 클립 재생인데 일단 isRunning 체크하는거 빼서 겹칠 수 있게 해봄.
            // battle effect 사운드가 자주 씹히는데 이래도 해결이 안되네
            //if (clip.isRunning()) {
            //    clip.stop();
            //}
            clip.setFramePosition(5);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}