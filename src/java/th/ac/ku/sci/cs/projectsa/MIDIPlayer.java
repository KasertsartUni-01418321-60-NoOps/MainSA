package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

// TODO: CHANGE SOUNDFONT แต่ใช้ของ ./misc/soundfonts/*
public class MIDIPlayer {
	private static java.util.List<String> playlist = new java.util.ArrayList<>();
	private static short currentIndex = 0;
	private static javax.sound.midi.Sequencer sequencer = null;
	private static java.io.InputStream midiDataStream = null;
	public static boolean isStop = false;
	private static boolean listenAtLeastOneRound = false;

	// entire exception handling info: mode=no
	// it's init func lamo
	public static void main() {
		MIDIPlayer.playlist.clear();
		MIDIPlayer.playlist = new java.util.ArrayList<>();
		MIDIPlayer.currentIndex = 0;
		MIDIPlayer.isStop = false;
		try {
			MIDIPlayer.sequencer = javax.sound.midi.MidiSystem.getSequencer();
			MIDIPlayer.sequencer.open();
		} catch (javax.sound.midi.MidiUnavailableException e) {
			try {
				MIDIPlayer.sequencer.close();
			} catch (NullPointerException e1) {
			}
		}
		String[] tmp1 = new String[] { "karaokeMIDI/32980.mid", "karaokeMIDI/63137.mid", "karaokeMIDI/63846.mid",
				"karaokeMIDI/A01997.mid", "karaokeMIDI/A023645.mid", "karaokeMIDI/B008004.mid",
				"karaokeMIDI/B0010106.mid", "karaokeMIDI/84195.mid", "karaokeMIDI/90762.mid", "karaokeMIDI/B006765.mid",
				"karaokeMIDI/B007261.mid", "karaokeMIDI/00179.mid", "karaokeMIDI/01176.mid", "karaokeMIDI/01723.mid",
				"karaokeMIDI/03767.mid", "karaokeMIDI/03879.mid", "karaokeMIDI/04612.mid", "karaokeMIDI/10514.mid",
				"karaokeMIDI/10982.mid", "karaokeMIDI/11383.mid", "karaokeMIDI/11868.mid", "karaokeMIDI/12033.mid",
				"karaokeMIDI/13306.mid", "karaokeMIDI/13901.mid", "karaokeMIDI/13910.mid", "karaokeMIDI/14523.mid",
				"karaokeMIDI/20223.mid", "karaokeMIDI/20797.mid", "karaokeMIDI/22057.mid", "karaokeMIDI/22163.mid",
				"karaokeMIDI/22510.mid", "karaokeMIDI/22906.mid", "karaokeMIDI/23038.mid", "karaokeMIDI/30004.mid",
				"karaokeMIDI/30165.mid", "karaokeMIDI/32055.mid", "karaokeMIDI/33665.mid", "karaokeMIDI/34262.mid",
				"karaokeMIDI/40374.mid", "karaokeMIDI/40407.mid", "karaokeMIDI/43095.mid", "karaokeMIDI/44552.mid",
				"karaokeMIDI/50420.mid", "karaokeMIDI/51055.mid", "karaokeMIDI/51468.mid", "karaokeMIDI/51675.mid",
				"karaokeMIDI/51695.mid", "karaokeMIDI/51831.mid", "karaokeMIDI/52594.mid", "karaokeMIDI/52948.mid",
				"karaokeMIDI/54895.mid", "karaokeMIDI/61177.mid", "karaokeMIDI/72473.mid", "karaokeMIDI/72979.mid",
				"karaokeMIDI/73949.mid", "karaokeMIDI/80836.mid", "karaokeMIDI/81510.mid", "karaokeMIDI/81626.mid",
				"karaokeMIDI/93283.mid", "karaokeMIDI/93287.mid", "karaokeMIDI/A0527.mid", "karaokeMIDI/A0617.mid",
				"karaokeMIDI/A03276.mid", "karaokeMIDI/A03370.mid", "karaokeMIDI/A04489.mid", "karaokeMIDI/A07088.mid",
				"karaokeMIDI/A08608.mid", "karaokeMIDI/A09535.mid", "karaokeMIDI/A011742.mid",
				"karaokeMIDI/A012515.mid", "karaokeMIDI/A013710.mid", "karaokeMIDI/A015337.mid",
				"karaokeMIDI/A015361.mid", "karaokeMIDI/A018669.mid", "karaokeMIDI/A019482.mid",
				"karaokeMIDI/A021990.mid", "karaokeMIDI/A022146.mid", "karaokeMIDI/A024429.mid",
				"karaokeMIDI/A024597.mid", "karaokeMIDI/A024815.mid", "karaokeMIDI/A024898.mid",
				"karaokeMIDI/A025061.mid", "karaokeMIDI/A025194.mid", "karaokeMIDI/A025447.mid",
				"karaokeMIDI/B00358.mid", "karaokeMIDI/B001597.mid", "karaokeMIDI/B003620.mid",
				"karaokeMIDI/B004651.mid", "karaokeMIDI/B006001.mid", "karaokeMIDI/B006860.mid",
				"karaokeMIDI/B007231.mid", "karaokeMIDI/B007607.mid", "karaokeMIDI/B009741.mid",
				"karaokeMIDI/B0010676.mid", "karaokeMIDI/B0011025.mid", "karaokeMIDI/B0011456.mid",
				"karaokeMIDI/B0013641.mid", "karaokeMIDI/B0014595.mid", "karaokeMIDI/B0015300.mid",
				"karaokeMIDI/B0016575.mid", "karaokeMIDI/00218.mid", "karaokeMIDI/00683.mid", "karaokeMIDI/00891.mid",
				"karaokeMIDI/02096.mid", "karaokeMIDI/02663.mid", "karaokeMIDI/03751.mid", "karaokeMIDI/03816.mid",
				"karaokeMIDI/04002.mid", "karaokeMIDI/10401.mid", "karaokeMIDI/11261.mid", "karaokeMIDI/11422.mid",
				"karaokeMIDI/12989.mid", "karaokeMIDI/14668.mid", "karaokeMIDI/20194.mid", "karaokeMIDI/20646.mid",
				"karaokeMIDI/21094.mid", "karaokeMIDI/21334.mid", "karaokeMIDI/23314.mid", "karaokeMIDI/31251.mid",
				"karaokeMIDI/31404.mid", "karaokeMIDI/31505.mid", "karaokeMIDI/32687.mid", "karaokeMIDI/32872.mid",
				"karaokeMIDI/34249.mid", "karaokeMIDI/34268.mid", "karaokeMIDI/42615.mid", "karaokeMIDI/43218.mid",
				"karaokeMIDI/44790.mid", "karaokeMIDI/50399.mid", "karaokeMIDI/51658.mid", "karaokeMIDI/52882.mid",
				"karaokeMIDI/53157.mid", "karaokeMIDI/53980.mid", "karaokeMIDI/54352.mid", "karaokeMIDI/60934.mid",
				"karaokeMIDI/63105.mid", "karaokeMIDI/64465.mid", "karaokeMIDI/64973.mid", "karaokeMIDI/70136.mid",
				"karaokeMIDI/71684.mid", "karaokeMIDI/71726.mid", "karaokeMIDI/72255.mid", "karaokeMIDI/72272.mid",
				"karaokeMIDI/72567.mid", "karaokeMIDI/74023.mid", "karaokeMIDI/74205.mid", "karaokeMIDI/81084.mid",
				"karaokeMIDI/81301.mid", "karaokeMIDI/81533.mid", "karaokeMIDI/81851.mid", "karaokeMIDI/82079.mid",
				"karaokeMIDI/84308.mid", "karaokeMIDI/90029.mid", "karaokeMIDI/90208.mid", "karaokeMIDI/91137.mid",
				"karaokeMIDI/91526.mid", "karaokeMIDI/92358.mid", "karaokeMIDI/92529.mid", "karaokeMIDI/92576.mid",
				"karaokeMIDI/93327.mid", "karaokeMIDI/A0371.mid", "karaokeMIDI/A0422.mid", "karaokeMIDI/A0503.mid",
				"karaokeMIDI/A01624.mid", "karaokeMIDI/A02873.mid", "karaokeMIDI/A03801.mid", "karaokeMIDI/A04216.mid",
				"karaokeMIDI/A05831.mid", "karaokeMIDI/A05968.mid", "karaokeMIDI/A07528.mid", "karaokeMIDI/A08976.mid",
				"karaokeMIDI/A09101.mid", "karaokeMIDI/A09124.mid", "karaokeMIDI/A09191.mid", "karaokeMIDI/A09346.mid",
				"karaokeMIDI/A09565.mid", "karaokeMIDI/A09736.mid", "karaokeMIDI/A010110.mid",
				"karaokeMIDI/A010351.mid", "karaokeMIDI/A011767.mid", "karaokeMIDI/A012148.mid",
				"karaokeMIDI/A012218.mid", "karaokeMIDI/A012577.mid", "karaokeMIDI/A013705.mid",
				"karaokeMIDI/A014061.mid", "karaokeMIDI/A017261.mid", "karaokeMIDI/A017681.mid",
				"karaokeMIDI/A018096.mid", "karaokeMIDI/A018207.mid", "karaokeMIDI/A018220.mid",
				"karaokeMIDI/A020506.mid", "karaokeMIDI/A020944.mid", "karaokeMIDI/A021234.mid",
				"karaokeMIDI/A021958.mid", "karaokeMIDI/A022600.mid", "karaokeMIDI/A022681.mid",
				"karaokeMIDI/A025410.mid", "karaokeMIDI/A025640.mid", "karaokeMIDI/B001643.mid",
				"karaokeMIDI/B002029.mid", "karaokeMIDI/B003268.mid", "karaokeMIDI/B003660.mid",
				"karaokeMIDI/B004943.mid", "karaokeMIDI/B0010103.mid", "karaokeMIDI/B0010526.mid",
				"karaokeMIDI/B0010926.mid", "karaokeMIDI/B0011576.mid", "karaokeMIDI/B0014657.mid",
				"karaokeMIDI/B0015181.mid", "karaokeMIDI/B0015735.mid", "karaokeMIDI/02887.mid",
				"karaokeMIDI/13146.mid", "karaokeMIDI/21714.mid", "karaokeMIDI/24312.mid", "karaokeMIDI/31525.mid",
				"karaokeMIDI/31802.mid", "karaokeMIDI/34981.mid", "karaokeMIDI/40260.mid", "karaokeMIDI/41969.mid",
				"karaokeMIDI/42285.mid", "karaokeMIDI/44245.mid", "karaokeMIDI/50900.mid", "karaokeMIDI/51830.mid",
				"karaokeMIDI/52480.mid", "karaokeMIDI/53302.mid", "karaokeMIDI/53440.mid", "karaokeMIDI/53691.mid",
				"karaokeMIDI/70098.mid", "karaokeMIDI/72771.mid", "karaokeMIDI/74443.mid", "karaokeMIDI/74816.mid",
				"karaokeMIDI/84351.mid", "karaokeMIDI/90837.mid", "karaokeMIDI/92065.mid", "karaokeMIDI/A0117.mid",
				"karaokeMIDI/A0578.mid", "karaokeMIDI/A0780.mid", "karaokeMIDI/A01575.mid", "karaokeMIDI/A04463.mid",
				"karaokeMIDI/A05029.mid", "karaokeMIDI/A08815.mid", "karaokeMIDI/A09501.mid", "karaokeMIDI/A09715.mid",
				"karaokeMIDI/A010873.mid", "karaokeMIDI/A011040.mid", "karaokeMIDI/A017615.mid",
				"karaokeMIDI/A020698.mid", "karaokeMIDI/A022159.mid", "karaokeMIDI/A022716.mid",
				"karaokeMIDI/B0011329.mid" };
		for (boolean tmp2 : new boolean[] { false, true }) {
			MIDIPlayer.playlist.add("rickroll.mid");
			MIDIPlayer.playlist.add("mhwgo.mid");
			MIDIPlayer.playlist.add("thainame_restorehappinessforthailand.mid");
			MIDIPlayer.playlist.add("thainame_love.mid");
		}
		MIDIPlayer.playlist.addAll(java.util.Arrays.asList(tmp1));

		java.util.Collections.shuffle(MIDIPlayer.playlist);
		MIDIPlayer.sequencer.addMetaEventListener(meta -> {
			if (meta.getType() == 0x2F) {
				if (MIDIPlayer.isStop) {
				} else {
					MIDIPlayer.currentIndex++;
					MIDIPlayer.play(); // Play the next song
				}

			}
		});
	}

	// entire exception handling info: mode=no
	public static void play() {
		while (true) {
			MIDIPlayer.isStop = false;
			if (MIDIPlayer.currentIndex >= MIDIPlayer.playlist.size()) {
				MIDIPlayer.currentIndex = 0;
				MIDIPlayer.listenAtLeastOneRound=true;
				java.util.Collections.shuffle(MIDIPlayer.playlist);
			}
			String currentSongName = MIDIPlayer.playlist.get(MIDIPlayer.currentIndex);
			// do not put leading slash for jarfile resource for this line of code lamo
			MIDIPlayer.midiDataStream = MIDIPlayer.class.getClassLoader()
					.getResourceAsStream("resources/" + currentSongName);
			try {
				MIDIPlayer.sequencer.setSequence(
						javax.sound.midi.MidiSystem.getSequence(MIDIPlayer.midiDataStream));
			} catch (java.io.EOFException e) {
				currentIndex++;
				continue;
			} catch (javax.sound.midi.InvalidMidiDataException | java.io.IOException e) {
				MIDIPlayer.sequencer.close();
				return;
			} finally {
				try {
					MIDIPlayer.midiDataStream.close();
				} catch (java.io.IOException e1) {
				}
			}
			break;
		}
		MIDIPlayer.sequencer.start();
		// TODO: excepiton handling
		javafx.application.Platform.runLater(() -> {
			UICtrl_Test.MIDIPlayerCallback();
		});
	}

	// entire exception handling info: mode=no
	public static void stop() {

		MIDIPlayer.isStop = true;
		MIDIPlayer.sequencer.stop();
		MIDIPlayer.sequencer.close();

	}

	// TODO: exception handling
	public static String getCurrentSongName() {
		if (MIDIPlayer.listenAtLeastOneRound) {return "(เพลงตอนนี้อะไรไม่รู้ รู้แต่ว่าคุณฟังมาครบ 1 รอบแล้ว เราว่าคุณน่าจะจำได้มั้งว่าที่ฟังคือเพลงอะไร...)";}
		return playlist.get(currentIndex);
	}
}
