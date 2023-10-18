
// TODO: remove later 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

// TODO: MAnage caught exception
public class MIDIPlayer {
	private static List<String> playlist = new ArrayList<>();
	private static short currentIndex = 0;
	private static Sequencer sequencer;
	public static boolean isStop = false;

	public static void main() throws Throwable {
		try {
			MIDIPlayer.sequencer = MidiSystem.getSequencer();
			MIDIPlayer.sequencer.open();
			MIDIPlayer.playlist.add("rickroll.mid");
			MIDIPlayer.playlist.add("mhwgo.mid");
			Collections.shuffle(MIDIPlayer.playlist);
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}

	public static void play() throws Throwable {
		try {
			if (MIDIPlayer.isStop) {
				isStop = false;
			} else {
				if (MIDIPlayer.currentIndex >= MIDIPlayer.playlist.size()) {
					MIDIPlayer.currentIndex = 0;
					Collections.shuffle(MIDIPlayer.playlist);
				}
				String currentSongName = MIDIPlayer.playlist.get(MIDIPlayer.currentIndex);
				MIDIPlayer.sequencer.setSequence(
						javax.sound.midi.MidiSystem.getSequence(
								MIDIPlayer.class.getClassLoader().getResourceAsStream("res/" + currentSongName)));
				MIDIPlayer.sequencer.start();
				MIDIPlayer.sequencer.addMetaEventListener(meta -> {
					if (meta.getType() == 0x2F) {
						MIDIPlayer.currentIndex++;
						try {
							MIDIPlayer.play(); // Play the next song
						} catch (Throwable e1) {
							try {
								MyExceptionHandling.handleFatalException(e1);
							} catch (Throwable e2) {
							}
						}
					}
				});
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
		}
	}
}
