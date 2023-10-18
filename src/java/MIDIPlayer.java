
public class MIDIPlayer {
	private static java.util.List<String> playlist = new java.util.ArrayList<>();
	private static short currentIndex = 0;
	private static javax.sound.midi.Sequencer sequencer;
	public static boolean isStop = false;

	public static void main() {

		try {
			MIDIPlayer.sequencer = javax.sound.midi.MidiSystem.getSequencer();
			MIDIPlayer.sequencer.open();
		} catch (javax.sound.midi.MidiUnavailableException e) {
			// just donothing lamo
		}
		MIDIPlayer.playlist.add("mhwgo.mid");
		MIDIPlayer.playlist.add("rickroll.mid");
		// พอดีเบื่อเพลง rickroll เลยเพิ่ม mhwgo เยอะหน่อย
		MIDIPlayer.playlist.add("mhwgo.mid");
		java.util.Collections.shuffle(MIDIPlayer.playlist);

	}

	public static void play() {

		MIDIPlayer.isStop = false;
		if (MIDIPlayer.currentIndex >= MIDIPlayer.playlist.size()) {
			MIDIPlayer.currentIndex = 0;
			java.util.Collections.shuffle(MIDIPlayer.playlist);
		}
		String currentSongName = MIDIPlayer.playlist.get(MIDIPlayer.currentIndex);
		try {
			MIDIPlayer.sequencer.setSequence(
					javax.sound.midi.MidiSystem.getSequence(
							MIDIPlayer.class.getClassLoader().getResourceAsStream("res/" + currentSongName)));
		} catch (javax.sound.midi.InvalidMidiDataException | java.io.IOException e) {
			// just return lamo
			return;
		}
		MIDIPlayer.sequencer.start();
		MIDIPlayer.sequencer.addMetaEventListener(meta -> {
			if (meta.getType() == 0x2F) {
				MIDIPlayer.currentIndex++;
				try {
					if (MIDIPlayer.isStop) {
					} else {
						MIDIPlayer.play(); // Play the next song
					}
				} catch (Throwable e1) {
					try {
						MyExceptionHandling.handleFatalException(e1);
					} catch (Throwable e2) {
					}
				}
			}
		});
	}

	public static void stop() {

		MIDIPlayer.isStop = true;
		MIDIPlayer.sequencer.stop();

	}
}
